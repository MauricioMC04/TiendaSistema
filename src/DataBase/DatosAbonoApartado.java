
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

public class DatosAbonoApartado {

    private final Conexion conex = Conexion.singleton();
    
    public boolean RealizarAbono(Abono abono){
        abono.setIdAbono(MayorIdAbono() + 1);
        return InsertarAbono(abono) && ActualizarApartado(abono);
    }
    
    private boolean InsertarAbono(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO abonos VALUES(?,?,?,?,?)");
            pst.setInt(1, abono.getIdAbono());
            pst.setInt(2, abono.getCodigoFactura());
            pst.setDouble(3, abono.getMonto());
            pst.setDate(4, abono.getFecha());
            pst.setInt(5, abono.getIdTipoDePago());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Generar Abono\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
    
    private int MayorIdAbono(){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(idAbono), 0) FROM abonos");
            int dato = -1;
            while(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            return dato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Abono\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    private boolean ActualizarApartado(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update facturas set MontoPagado = MontoPagado + " +
                abono.getMonto() + " where CodigoFactura = " + abono.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Actualizar Apartado\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
    
    public ObservableList<Abono> Abonos(String busqueda){
        ObservableList <Abono> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlAbonos(busqueda));
            while (rs.next()) {
                modelo.add(new Abono(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDate(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Abonos \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlAbonos(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select idAbono, CodigoFactura, Monto, Fecha, idTipoDePago from abonos";
        }
        return "select idAbono, CodigoFactura, Monto, Fecha, idTipoDePago from abonos where idAbono Like '%" + busqueda +
            "%' Or CodigoFactura Like '%" + busqueda + "%' Or Monto Like '%" + busqueda + "%' Or Fecha Like '%" + 
            busqueda + "%' Or idTipoDePago Like '%" + busqueda + "%'";
    }
    
    public boolean EliminarAbono(Abono abono){
        return DescontarAbono(abono) && EliminarAbonoApartado(abono);
    }
    
    private boolean DescontarAbono(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update facturas set MontoPagado = MontoPagado - " +
                abono.getMonto() + " where CodigoFactura = " + abono.getCodigoFactura());
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Actualizar Apartado por Eliminacion de Abono\n" + ex);
        }
        conex.close();
        return false;
    }
     
    private boolean EliminarAbonoApartado(Abono abono){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from abonos where idAbono = " + 
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