
package DataBase;

import Models.Cliente;
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
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlClientes(busqueda));
            while (rs.next()) {
                modelo.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), CantidadApartados(rs.getInt(1))));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Clientes \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSqlClientes(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select idCliente, Nombre, Numero from clientes where idCliente != 0 Order by Nombre ASC";
        }
        return "select idCliente, Nombre, Numero from clientes where idCliente != 0 And (idCliente Like '%" + busqueda +
            "%' Or Nombre Like '%" + busqueda + "%' Or Numero Like '%" + busqueda + "%') Order by Nombre ASC";
    }
    
    private int CantidadApartados(int idCliente){
        int cantidad = 0;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select count(idCliente) from facturas where idCliente = " + idCliente);
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