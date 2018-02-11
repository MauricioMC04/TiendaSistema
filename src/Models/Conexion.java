
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
 
    private static Conexion conect;
    private Connection conexion;

    private Conexion(){
        
    }
    
    public static Conexion singleton(){
        if(conect != null){
            return conect;
        }
        conect = new Conexion();
        return conect;
    }
    
    public Connection open() {
        this.conexion = conexion();
        return conexion;
    }
    
    private Connection conexion() {
        Connection conex = null;
        try {    
            Class.forName("com.mysql.jdbc.Driver");
            conex = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Tienda", "root", "Ghptjj26");
            //conex = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Tienda", "root", "24166556");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "error de conexion " + e);
        }
        if(conex == null){
            JOptionPane.showMessageDialog(null, "Contacte al Desarrollador");
            System.exit(0);
        }
        return conex;
    }
    
    public void close(){
        try {
            this.conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexion\nContacte al Desarrollador");
            System.exit(0);
        } 
    }
    
}
