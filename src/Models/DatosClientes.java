
package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosClientes {
    
    private final Conexion conex = Conexion.singleton();
    
    public ObservableList<Cliente> Clientes(String busqueda){
        ObservableList <Cliente> modelo = FXCollections.observableArrayList();
        String sql = GenerarSqlClientes(busqueda);
        String[] datos = new String[3];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.add(new Cliente(Integer.parseInt(datos[0]),datos[1],datos[2],
                    CantidadApartados(Integer.parseInt(datos[0]))));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Clientes \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlClientes(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select idCliente, Nombre, Numero from clientes where idCliente != 0";
        }
        return "select idCliente, Nombre, Numero from clientes where idCliente != 0 And (idCliente Like '%" + busqueda +
            "%' Or Nombre Like '%" + busqueda + "%' Or Numero Like '%" + busqueda + "%')";
    }
    
    private int CantidadApartados(int idCliente){
        int cantidad = 0;
        String sql = "select count(idCliente) from facturas where idCliente = " + idCliente;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cantidad de Apartados\n" + ex);
        }
        conex.close();
        return cantidad;
    }
}
