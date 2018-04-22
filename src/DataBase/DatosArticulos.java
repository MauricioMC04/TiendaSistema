
package DataBase;

import Models.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosArticulos {
    
    private final Conexion conex = Conexion.singleton();
    
    public ObservableList<Articulo> Articulos(String busqueda){
        ObservableList <Articulo> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSql(busqueda));
            while (rs.next()) {
                modelo.add(new Articulo(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Articulos \n" + ex);
        }
        conex.close();
        return modelo;
    }
 
    private String GenerarSql(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select CodigoArticulo, Nombre, Precio from articulos Order by Nombre ASC";
        }
        return "select CodigoArticulo, Nombre, Precio from articulos where CodigoArticulo LIKE '%" + busqueda + "%' Or "
            + "Nombre LIKE '%" + busqueda + "%' Or Precio LIKE '%" + busqueda + "%' Order by Nombre ASC";
    }
    
    public ObservableList<Articulo> ArticulosEliminar(String busqueda){
        ObservableList <Articulo> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlEliminar(busqueda));
            while (rs.next()) {
                modelo.add(new Articulo(rs.getInt(1),rs.getString(2),rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar Articulos Eliminar\n" + ex);
        }
        conex.close();
        return modelo;
    }
    
    private String GenerarSqlEliminar(String busqueda){
        if(busqueda.equals("Ninguna")){
            return "select T1.CodigoArticulo, T1.Nombre, T1.Precio from articulos T1 Left Outer Join detalleFactura T2 "
                + "ON T1.CodigoArticulo = T2.CodigoArticulo where T2.CodigoArticulo is null Order by T1.Nombre ASC";
        }
        return  "select T1.CodigoArticulo, T1.Nombre, T1.Precio from articulos T1 Left Outer Join detalleFactura T2 ON "
            + "T1.CodigoArticulo = T2.CodigoArticulo where T2.CodigoArticulo is null And (T1.CodigoArticulo Like '%" + 
            busqueda + "%' Or T1.Nombre Like '%" + busqueda + "%' Or T1.Precio Like '%" + busqueda + "%') Order by "
            + "T1.Nombre ASC";
    }
    
    public boolean ExisteArticulo(String nombre, double precio){
        int CodigoArticulo = 0;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select CodigoArticulo from articulos where Nombre = '" + nombre + "' And "
                + "Precio = " + precio);
            while (rs.next()) {
                CodigoArticulo = rs.getInt(1);      
            }
            conex.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Existe Articulo\n" + ex);
        }
        return CodigoArticulo != 0;
    }
    
    public boolean AgregarArticulo(String nombre, double precio){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO articulos VALUES(?,?,?)");
            pst.setInt(1, MayorArticulo() + 1);
            pst.setString(2, nombre);
            pst.setDouble(3, precio);
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Agregar Articulo\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
     
    private int MayorArticulo(){
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(CodigoArticulo), 0) FROM articulos");
            int dato = -1;
            while(rs.next()){
                dato = rs.getInt(1);
            }
            conex.close();
            return dato;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Mayor Articulo\n" + ex);
        }
        conex.close();
        return -1;
    }
    
    public boolean EliminarArticulo(int codigoArticulo){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("delete from articulos where CodigoArticulo = " + 
                codigoArticulo);
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Eliminar Articulo\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
    
    public boolean EditarArticulo(int codigoArticulo, String nombre, double precio){
        Connection conexion = conex.open();
        try {
            PreparedStatement pst = conexion.prepareStatement("update articulos set Nombre = '" + nombre + "', Precio"
                + " = " + precio + " where CodigoArticulo = " +codigoArticulo); 
            pst.executeUpdate();
            conex.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Editar Articulo\n" + ex.getMessage());
        }
        conex.close();
        return false;
    }
}