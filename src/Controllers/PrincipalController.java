
package Controllers;

import static java.awt.Color.black;
import static java.awt.Color.blue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Color;

public class PrincipalController implements Initializable {
    
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnVenta;
    @FXML
    private Button btnApartado;
    @FXML
    private Button btnAbono;
    @FXML
    private Button btnConsultas;
    @FXML
    private Button btnSalir;
    @FXML
    private AnchorPane Espacio;
    @FXML
    private Button btnDocumentacion;
    @FXML
    private Button btnArticulos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarVentana("Inicio");
        btnInicio.setCursor(javafx.scene.Cursor.HAND);
        btnAbono.setCursor(javafx.scene.Cursor.HAND);
        btnApartado.setCursor(javafx.scene.Cursor.HAND);
        btnSalir.setCursor(javafx.scene.Cursor.HAND);
        btnConsultas.setCursor(javafx.scene.Cursor.HAND);
        btnVenta.setCursor(javafx.scene.Cursor.HAND);
        btnDocumentacion.setCursor(javafx.scene.Cursor.HAND);
        btnArticulos.setCursor(javafx.scene.Cursor.HAND);
    }    

    @FXML
    private void MouseMano(MouseEvent event) {
        DropShadow shadow = new DropShadow(10, javafx.scene.paint.Color.BLUE);
        btnInicio.setEffect(shadow);
    }

    @FXML
    private void Inicio(ActionEvent event){
        btnInicio.setDisable(true);
        CargarVentana("Inicio");
        btnInicio.setDisable(false);
    }
    
    @FXML
    private void Venta(ActionEvent event) {
        btnVenta.setDisable(true);
        CargarVentana("Venta");
        btnVenta.setDisable(false);
    }

    @FXML
    private void Apartado(ActionEvent event) {
        btnApartado.setDisable(true);
        CargarVentana("Apartado");
        btnApartado.setDisable(false);
    }

    @FXML
    private void Abono(ActionEvent event) {
        btnAbono.setDisable(true);
        CargarVentana("Abono");
        btnAbono.setDisable(false);
    }

    @FXML
    private void Consultas(ActionEvent event) {
        btnConsultas.setDisable(true);
        CargarVentana("Consultas");
        btnConsultas.setDisable(false);
    }

    @FXML
    private void Salir(ActionEvent event) {
        Object[] options = { "Salir", "Cancelar" };
        int choice = JOptionPane.showOptionDialog(null,
            "Desea salir del sistema?", 
            "Salir Confirmacion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        if (choice == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    private void CargarVentana(String ventana){
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Views/"+ventana+".fxml"));
            Espacio.getChildren().clear();
            Espacio.getChildren().setAll(pane);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    @FXML
    private void Documentacion(ActionEvent event) {
        btnDocumentacion.setDisable(true);
        CargarVentana("Documentacion");
        btnDocumentacion.setDisable(false);
    }

    @FXML
    private void Articulos(ActionEvent event) {
        btnArticulos.setDisable(true);
        CargarVentana("Articulos");
        btnArticulos.setDisable(false);
    }
}
