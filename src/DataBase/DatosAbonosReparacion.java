
package DataBase;

import Models.Abono;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosAbonosReparacion {
    
    private final Conexion conex = Conexion.singleton();
    
    public boolean RealizarAbono(Abono abono){
        abono.setIdAbono(MayorIdAbonoReparacion() + 1);
        return InsertarAbonoReparacion(abono) && ActualizarReparacion(abono);
    }
    
    private int MayorIdAbonoReparacion(){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(CodigoAbono), 0) FROM abonosreparacion");
            int dato = -1;
            while(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            return dato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Abono Reparacion\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    private boolean InsertarAbonoReparacion(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO abonosreparacion VALUES(?,?,?,?,?)");
            pst.setInt(1, abono.getIdAbono());
            pst.setDate(2, abono.getFecha());
            pst.setDouble(3, abono.getMonto());
            pst.setInt(4, abono.getCodigoFactura());
            pst.setInt(5, abono.getIdTipoDePago());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Generar Abono Reparacion\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
    
    
    
    private boolean ActualizarReparacion(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update reparaciones set MontoPagado = MontoPagado + " +
                abono.getMonto() + " where CodigoReparacion = " + abono.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Actualizar Reparacion\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
    
    public ObservableList<Abono> AbonosReparacion(String busqueda){
        ObservableList <Abono> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlAbonos(busqueda));
            while (rs.next()) {
                modelo.add(new Abono(rs.getInt(1), rs.getInt(4), rs.getDouble(3), rs.getDate(2), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Abonos Reparacion\n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlAbonos(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select CodigoAbono, Fecha, Monto, CodigoReparacion, idTipoDePago from abonosReparacion";
        }
        return "select CodigoAbono, Fecha, Monto, CodigoReparacion, idTipoDePago from abonosReparacion where "
        + "CodigoAbono Like '%" + busqueda + "%' Or CodigoReparacion Like '%" + busqueda + "%' Or Monto Like '%" + 
        busqueda + "%' Or Fecha Like '%" + busqueda + "%' Or idTipoDePago Like '%" + busqueda + "%'";
    }

    public boolean EliminarAbono(Abono abono){
        return DescontarAbono(abono) && EliminarAbonoReparacion(abono);
    }
    
    private boolean DescontarAbono(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update reparaciones set MontoPagado = MontoPagado - " +
                abono.getMonto() + " where CodigoReparacion = " + abono.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Actualizar Reparacion por Eliminacion de Abono\n" + ex);
        }
        conex.close();
        return false;
    }
     
    private boolean EliminarAbonoReparacion(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from abonosreparacion where CodigoAbono = " + 
                abono.getIdAbono());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar Abono:\n" + ex);
        }
        conex.close();
        return false;
    }
}
