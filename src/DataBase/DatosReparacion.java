
package DataBase;

import Models.Cliente;
import Models.ObjetoDeImpresionReparacion;
import Models.Reparacion;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosReparacion {
    
    private final Conexion conex = Conexion.singleton();
    
    public boolean RealizarReparacion(Reparacion reparacion, String nombrePersona, String numeroPersona){
        reparacion.setCodigoReparacion(MayorReparacion() + 1);
        reparacion.setIdCliente(ObtenerCliente(nombrePersona, numeroPersona));
        return GenerarReparacion(reparacion);
    }
    
    private int MayorReparacion(){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(CodigoReparacion), 0) FROM reparaciones");
            int dato = -1;
            if(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            return dato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Reparacion\n" + ex);
        }
        conex.close();
        return -1;
    }
    
     public int ObtenerCliente(String nombre, String numero){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT idCliente FROM clientes where Nombre = '" + nombre + "' And Numero = "
                + "'" + numero + "'");
            int dato = -1;
            if(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            if(dato != -1){
                return dato;
            }else{
                return GenerarCliente(nombre, numero);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Obtener Cliente\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    private int GenerarCliente(String nombre, String numero){
        Connection conexion = conex.open();
        try {
            int idCliente = MayorCliente() + 1;
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO clientes VALUES(?,?,?)");
            pst.setInt(1, idCliente);
            pst.setString(2, nombre);
            pst.setString(3, numero);
            pst.executeUpdate();
            conex.close();
            return idCliente;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en Generar Cliente: " + ex);
        }
        conex.close();
        return -1;
    }
    
    private int MayorCliente(){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(idCliente), 0) FROM clientes");
            int dato = -1;
            if(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            return dato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Cliente\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    private boolean GenerarReparacion(Reparacion reparacion){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO reparaciones VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, reparacion.getCodigoReparacion());
            pst.setDate(2, reparacion.getFecha());
            pst.setString(3, reparacion.getArticulo());
            pst.setString(4, reparacion.getDescripcion());
            pst.setDouble(5, reparacion.getMontoTotal());
            pst.setDouble(6, reparacion.getMontoPagado());
            pst.setInt(7, reparacion.getEstado());
            pst.setInt(8, reparacion.getTipoPago());
            pst.setInt(9, reparacion.getIdCliente());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Generar Reparacion\n" + ex.getMessage());
        }    
        conex.close();
        return false;
    }  
    
     public void ImprimirReparacion(Reparacion reparacion){
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        paper.setSize(612.0,832.0);
        double margin = 10;
        paper.setImageableArea(margin, margin, paper.getWidth()-margin, paper.getHeight()-margin);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.PORTRAIT);
        job.setPrintable(new ObjetoDeImpresionReparacion(reparacion,Cliente(reparacion.getIdCliente())), pf);  
        try{
            job.print();
        }catch(PrinterException e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
     
    public Cliente Cliente(int idCliente){
        Connection conexion = conex.open();
        Cliente cliente = null;
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT Nombre, Numero FROM clientes where idCliente = " + idCliente);
            while(rs.next()){
                cliente = new Cliente(idCliente, rs.getString(1), rs.getString(2), 0);
            }
            conex.close();
            return cliente;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Cliente\n" + ex);
        }
        conex.close();
        return null;
    }
    
    public ObservableList<Reparacion> CargarReparacionesPendientes(int tipo, String busqueda){
        ObservableList <Reparacion> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlReparacionesPendientes(tipo, busqueda));
            while (rs.next()) {
                modelo.add(new Reparacion(rs.getInt(1), rs.getDate(2), rs.getInt(9), rs.getString(3), rs.getString(4), 
                    rs.getInt(7), rs.getDouble(5), rs.getDouble(6), rs.getInt(8)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Reparaciones Pendientes\n" + ex);
        }
        conex.close();
        return modelo;
    }
    
    private String GenerarSqlReparacionesPendientes(int tipo, String busqueda){
        if(tipo == 1){
            if(busqueda.equals("Ninguna")){
                return "select codigoReparacion, fecha, articulo, descripcion, montoTotal, montoPagado, "
                    + "idEstadoReparacion, idTipoDePago, idCliente from reparaciones where montoTotal != montoPagado";
            }else{
                return "select codigoReparacion, fecha, articulo, descripcion, montoTotal, montoPagado, "
                    + "idEstadoReparacion, idTipoDePago, idCliente from reparaciones where montoTotal != montoPagado "
                    + "And (codigoReparacion Like '%" + busqueda + "%' Or fecha Like '%" + busqueda + "%' Or articulo "
                    + "Like '%" + busqueda + "%' Or descripcion Like '%" + busqueda + "%' Or montoTotal Like '%" 
                    + busqueda + "%' Or montoPagado Like '%" + busqueda + "%' Or idEstadoReparacion Like '%" + busqueda 
                    + "%' Or idTipoDePago Like '%" + busqueda + "%' Or idCliente Like '%" + busqueda + "%')";
            }
        }else{
            if(busqueda.equals("Ninguna")){
                return "select codigoReparacion, fecha, articulo, descripcion, montoTotal, montoPagado, "
                    + "idEstadoReparacion, idTipoDePago, idCliente from reparaciones";
            }else{
                return "select codigoReparacion, fecha, articulo, descripcion, montoTotal, montoPagado, "
                    + "idEstadoReparacion, idTipoDePago, idCliente from reparaciones where codigoReparacion Like '%" 
                    + busqueda + "%' Or fecha Like '%" + busqueda + "%' Or articulo Like '%" + busqueda + "%' Or "
                    + "descripcion Like '%" + busqueda + "%' Or montoTotal Like '%" + busqueda + "%' Or montoPagado "
                    + "Like '%" + busqueda + "%' Or idEstadoReparacion Like '%" + busqueda + "%' Or idTipoDePago Like "
                    + "'%" + busqueda + "%' Or idCliente Like '%" + busqueda + "%'";
            }
        }
    }
    
    public boolean EditarReparacion(int reparacion, String articulo, String descripcion, String nombre, 
    String numero, int estado, int tipoPago){
        int idCliente = ObtenerCliente(nombre, numero);
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update reparaciones set Articulo = '" + articulo + "', "
            + "Descripcion = '" + descripcion + "', idEstadoReparacion = " + estado + ", idTipoDePago = " + tipoPago + 
            ", idCliente = " + idCliente + " where CodigoReparacion = " + reparacion);
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Editar Reparacion:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean Eliminar(Reparacion reparacion){
        return EliminarAbonosReparacion(reparacion) && EliminarReparacion(reparacion);
    }
   
    public boolean EliminarAbonosReparacion(Reparacion reparacion){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from abonosreparacion where CodigoReparacion = " + 
                reparacion.getCodigoReparacion());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Abonos Reparacion:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean EliminarReparacion(Reparacion reparacion){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from reparaciones where CodigoReparacion = " + 
                reparacion.getCodigoReparacion());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Abonos Reparacion:\n" + ex);
        }
        conex.close();
        return false;
    }
}