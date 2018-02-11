
package Models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosAbono {

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
        String sql = "SELECT IFNULL(MAX(idAbono), 0) FROM abonos";
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
        String sql = GenerarSqlAbonos(busqueda);
        String[] datos = new String[4];
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
                modelo.add(new Abono(Integer.parseInt(datos[0]),Integer.parseInt(datos[1]),Double.parseDouble(datos[2]),
                    fecha, Integer.parseInt(datos[3])));
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
}
