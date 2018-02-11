
package Models;

import Controllers.ApartadoController;
import Controllers.VentaController;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosFactura {
    
    private final Conexion conex = Conexion.singleton();
    
    public boolean RealizarVenta(Factura factura, ObservableList<DetalleFactura> listaVenta){
        int mayorFactura = MayorFactura();
        if(GenerarFactura(mayorFactura +1, factura) && GenerarDetalles(mayorFactura +1, listaVenta)){
            factura.setCodigoFactura(mayorFactura + 1);            
            VentaController.factura = factura;
            Imprimir(factura, listaVenta);
            return true;
        }else{
            return false;
        }
    }
    
    private boolean GenerarFactura(int codigoFactura, Factura factura){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO facturas VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1, codigoFactura);
            pst.setInt(2, factura.getTipoDeFactura());
            pst.setDouble(3, factura.getMontoTotal());
            pst.setDouble(4, factura.getMontoPagado());
            pst.setDate(5, factura.getFecha());
            pst.setInt(6, factura.getIdCliente());
            pst.setInt(7, factura.getIdTipoDePago());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Generar Factura\n" + ex.getMessage());
        }    
        conex.close();
        return false;
    }   
    
    private boolean GenerarDetalles(int codigoFactura, ObservableList<DetalleFactura> listaVenta){
        Connection conexion = conex.open();
        try {
            for(int i = 0; i < listaVenta.size(); i++){
                PreparedStatement pst = conexion.prepareStatement("INSERT INTO detallefactura VALUES(?,?,?)");
                pst.setInt(1, codigoFactura);
                pst.setInt(2, listaVenta.get(i).getCodigoArticulo());
                pst.setDouble(3, listaVenta.get(i).getDescuento());
                pst.executeUpdate();
            }
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Generar Detalles\n" + ex.getMessage());
        }   
        conex.close();
        return false;
    }
    
    private int MayorFactura(){
        String sql = "SELECT IFNULL(MAX(CodigoFactura), 0) FROM facturas";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String dato = "";
            while(rs.next()){
                dato = rs.getString(1);
            }
            conex.close();
            return Integer.parseInt(dato);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Factura\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    public boolean RealizarApartado(Factura factura, ObservableList<DetalleFactura> listaVenta, String nombre, String numero){
        int mayorFactura = MayorFactura();
        factura.setIdCliente(ObtenerCliente(nombre, numero));
        if(factura.getIdCliente() != -1){
            if(GenerarFactura(mayorFactura +1, factura) && GenerarDetalles(mayorFactura +1, listaVenta)){
                factura.setCodigoFactura(mayorFactura + 1);
                ApartadoController.factura = factura;
                Imprimir(factura, listaVenta);
                return true;
            }
            return false;
        }
        return false;
    }
    
    public int ObtenerCliente(String nombre, String numero){
        String sql = "SELECT idCliente FROM clientes where Nombre = '" + nombre + "' And Numero = '" + numero + "'";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String dato = "";
            while(rs.next()){
                if(rs.getString(1) != null){
                    dato = rs.getString(1);
                }
            }
            conex.close();
            if(!dato.equals("")){
                return Integer.parseInt(dato);
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
        int idCliente = MayorCliente() + 1;
        Connection conexion = conex.open();
        try {
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
        String sql = "SELECT IFNULL(MAX(idCliente), 0) FROM clientes";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String dato = "";
            while(rs.next()){
                dato = rs.getString(1);
            }
            conex.close();
            return Integer.parseInt(dato);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Factura\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    public ObservableList<Factura> FacturasApartados(String busqueda){
        ObservableList <Factura> modelo = FXCollections.observableArrayList();
        String sql = GenerarSqlFacturas(busqueda);
        String[] datos = new String[5];
        Date fecha;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                fecha = rs.getDate(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.add(new Factura(Integer.parseInt(datos[0]),2,Double.parseDouble(datos[1]),
                    Double.parseDouble(datos[2]),fecha,Integer.parseInt(datos[3]),Integer.parseInt(datos[4])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Facturas \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlFacturas(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select f.CodigoFactura, f.MontoTotal, f.MontoPagado, f.Fecha, f.idCliente, f.idTipoDePago from "
                + "facturas f, tiposdefactura t where f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado' And "
                + "f.MontoTotal != f.MontoPagado";
        }
        return "select f.CodigoFactura, f.MontoTotal, f.MontoPagado, f.Fecha, f.idCliente, f.idTipoDePago from "
            + "facturas f, tiposdefactura t, clientes c where f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = "
            + "'Apartado' And f.MontoTotal != f.MontoPagado And f.idCliente = c.idCliente And (c.Nombre Like '%" + 
            busqueda + "%' Or c.Numero Like '%" + busqueda + "%' Or f.CodigoFactura Like '%" + busqueda + "%' "
            + "Or f.MontoTotal Like '%" + busqueda + "%' Or f.MontoPagado Like '%" + busqueda + "%' Or f.Fecha Like '%"
            + busqueda + "%' Or f.idCliente Like '%" + busqueda + "%' Or f.idTipoDePago Like '%" + busqueda + "%')";
    }
    
    public Cliente Cliente(int idCliente){
        Cliente cliente = new Cliente();
        String sql = "SELECT Nombre, Numero FROM clientes where idCliente = " + idCliente;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String[] datos = new String[2];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
            }
            cliente = new Cliente(idCliente, datos[0], datos[1],0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Cliente\n" + ex);
        }
        conex.close();
        return cliente;
    }
    
    public ObservableList<DetalleFactura> DetalleFactura(int codigoFactura){
        ObservableList <DetalleFactura> modelo = FXCollections.observableArrayList();
        String sql = "select a.CodigoArticulo, a.Nombre, a.Precio, d.Descuento from detallefactura d, articulos a where"
            + " d.CodigoFactura = " + codigoFactura + " And d.CodigoArticulo = a.CodigoArticulo";
        String[] datos = new String[4];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                modelo.add(new DetalleFactura(codigoFactura, Double.parseDouble(datos[3]),Integer.parseInt(datos[0]),
                    datos[1],Double.parseDouble(datos[2])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Detalle de Factura\n" + ex);
        }
        conex.close();
        return modelo;
    }
    
    public ObservableList<Factura> TodasFacturas(String busqueda){
        ObservableList <Factura> modelo = FXCollections.observableArrayList();
        String sql = GenerarSqlTodasFacturas(busqueda);
        String[] datos = new String[5];
        Date fecha;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                fecha = rs.getDate(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.add(new Factura(Integer.parseInt(datos[0]),1,Double.parseDouble(datos[1]),
                    Double.parseDouble(datos[2]),fecha,Integer.parseInt(datos[3]),
                    Integer.parseInt(datos[4])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Todas las Facturas \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlTodasFacturas(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select CodigoFactura, MontoTotal, MontoPagado, Fecha, idCliente, idTipoDePago from "
                + "facturas where TipoDeFactura = 1";
        }
        return "select f.CodigoFactura, f.MontoTotal, f.MontoPagado, f.Fecha, f.idCliente, f.idTipoDePago from "
            + "facturas f, clientes c where f.TipoDeFactura = 1 And f.idCliente = c.idCliente And (c.Nombre Like '%" + 
            busqueda + "%' Or c.Numero Like '%" + busqueda + "%' Or f.CodigoFactura Like '%" + busqueda + "%' Or "
            + "f.TipoDeFactura Like '%" + busqueda + "%' Or f.MontoTotal Like '%" + busqueda + "%' Or f.MontoPagado "
            + "Like '%" + busqueda + "%' Or f.Fecha Like '%" + busqueda + "%' Or f.idCliente Like '%" + busqueda + "%' "
            + "Or f.idTipoDePago Like '%" + busqueda + "%')";
    }
    
    public boolean EliminarDetalleFactura(DetalleFactura detalle){
        return EliminarDetalle(detalle) && ActualizarFactura(detalle);
    }
    
    public boolean EliminarDetalle(DetalleFactura detalle){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from detallefactura where CodigoFactura = " + 
                detalle.getCodigoFactura() + " And CodigoArticulo = " + detalle.getCodigoArticulo() + " And Descuento"
                + " = " + detalle.getDescuento());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Detalle:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean ActualizarFactura(DetalleFactura detalle){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update facturas set MontoTotal = MontoTotal - " + 
                (detalle.getPrecio() - detalle.getDescuento()) + " where CodigoFactura = " + detalle.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Actualizar Factura:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean EditarFactura(int tipoPago, Factura factura){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update facturas set idTipoDePago = " + tipoPago + 
                " where CodigoFactura = " + factura.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Editar Factura:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean EliminarFactura(Factura factura){
        return EliminarDetalles(factura) && Eliminar(factura);
    }
   
    public boolean EliminarDetalles(Factura factura){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from detallefactura where CodigoFactura = " + 
                factura.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Detalles:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public boolean Eliminar(Factura factura){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from facturas where CodigoFactura = " + 
                factura.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Detalles:\n" + ex);
        }
        conex.close();
        return false;
    }
    
    public ObservableList<Factura> TodosApartados(String busqueda){
        ObservableList <Factura> modelo = FXCollections.observableArrayList();
        String sql = GenerarSqlTodosApartados(busqueda);
        String[] datos = new String[5];
        Date fecha;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                fecha = rs.getDate(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.add(new Factura(Integer.parseInt(datos[0]),2,Double.parseDouble(datos[1]),
                    Double.parseDouble(datos[2]),fecha,Integer.parseInt(datos[3]),
                    Integer.parseInt(datos[4])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Todos los Apartados \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlTodosApartados(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select CodigoFactura, MontoTotal, MontoPagado, Fecha, idCliente, idTipoDePago from "
                + "facturas where TipoDeFactura = 2";
        }
        return "select f.CodigoFactura, f.MontoTotal, f.MontoPagado, f.Fecha, f.idCliente, f.idTipoDePago from "
            + "facturas f, clientes c where f.TipoDeFactura = 2 And f.idCliente = c.idCliente And (c.Nombre Like '%" + 
            busqueda + "%' Or c.Numero Like '%" + busqueda + "%' Or f.CodigoFactura Like '%" + busqueda + "%' Or "
            + "f.TipoDeFactura Like '%" + busqueda + "%' Or f.MontoTotal Like '%" + busqueda + "%' Or f.MontoPagado "
            + "Like '%" + busqueda + "%' Or f.Fecha Like '%" + busqueda + "%' Or f.idCliente Like '%" + busqueda + "%' "
            + "Or f.idTipoDePago Like '%" + busqueda + "%')";
    }
    
    public boolean EditarApartado(String nombre, String numero, int tipoPago, Factura apartado){
        Connection conexion = conex.open();
        try {
            int idcliente = ObtenerCliente(nombre, numero);
            PreparedStatement pst = conexion.prepareStatement("update facturas set idCliente = " + idcliente + 
                ", idTipoDePago = " + tipoPago + " where CodigoFactura = " + apartado.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Editar Apartado\n" + ex);
        }
        conex.close();
        return false;
    }
  
    public void Imprimir(Factura factura, ObservableList<DetalleFactura> listaVenta){
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        paper.setSize(612.0,832.0);
        double margin = 10;
        paper.setImageableArea(margin, margin, paper.getWidth()-margin, paper.getHeight()-margin);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.PORTRAIT);
        job.setPrintable(new ObjetoDeImpresion(factura,listaVenta,Cliente(factura.getIdCliente())), pf);  
        try{
            job.print();
        }catch(PrinterException e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private Factura Factura(int codigo){
        String sql = "select MontoTotal, MontoPagado, Fecha, idCliente, idTipoDePago, TipoDeFactura "
            + "from facturas where CodigoFactura = " + codigo;
        String[] datos = new String[5];
        Date fecha = null;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                fecha = rs.getDate(3);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Factura\n" + ex);
        }
        conex.close();
        return (new Factura(codigo,Integer.parseInt(datos[4]),Double.parseDouble(datos[0]),
                    Double.parseDouble(datos[1]),fecha,Integer.parseInt(datos[2]),
                    Integer.parseInt(datos[3])));
    }
    
    public void ImprimirFactura(int codigoFactura){
        Factura factura = Factura(codigoFactura);
        ObservableList<DetalleFactura> listaVenta = DetalleFactura(codigoFactura);
        Imprimir(factura, listaVenta);
    }
}

//PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPrintable(new ObjetoDeImpresion(factura,listaVenta));
//            if(job.printDialog()){
//            try{
//                job.print();
//            }catch(PrinterException e){
//                JOptionPane.showMessageDialog(null, e);
//            }
//        }