
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class PrincipalController implements Initializable {
    
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnVenta;
    @FXML
    private Button btnApartado;
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
    @FXML
    private Button btnAbonoApartado;
    @FXML
    private Button btnReparacion;
    @FXML
    private Button btnAbonoReparacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarVentana("Inicio");
        btnInicio.getStyleClass().add("btn5");
        btnInicio.setCursor(javafx.scene.Cursor.HAND);
        btnAbonoApartado.setCursor(javafx.scene.Cursor.HAND);
        btnApartado.setCursor(javafx.scene.Cursor.HAND);
        btnSalir.setCursor(javafx.scene.Cursor.HAND);
        btnConsultas.setCursor(javafx.scene.Cursor.HAND);
        btnVenta.setCursor(javafx.scene.Cursor.HAND);
        btnDocumentacion.setCursor(javafx.scene.Cursor.HAND);
        btnArticulos.setCursor(javafx.scene.Cursor.HAND);
        btnReparacion.setCursor(javafx.scene.Cursor.HAND);
        btnAbonoReparacion.setCursor(javafx.scene.Cursor.HAND);
    }    

    @FXML
    private void Inicio(ActionEvent event){
        btnInicio.setDisable(true);
        DesSeleccionar();
        CargarVentana("Inicio");
        btnInicio.getStyleClass().add("btn5");
        btnInicio.setDisable(false);
    }
    
    @FXML
    private void Venta(ActionEvent event) {
        btnVenta.setDisable(true);
        DesSeleccionar();
        CargarVentana("Venta");
        btnVenta.getStyleClass().add("btn5");
        btnVenta.setDisable(false);
    }

    @FXML
    private void Apartado(ActionEvent event) {
        btnApartado.setDisable(true);
        DesSeleccionar();
        CargarVentana("Apartado");
        btnApartado.getStyleClass().add("btn5");
        btnApartado.setDisable(false);
    }

    @FXML
    private void Consultas(ActionEvent event) {
        btnConsultas.setDisable(true);
        DesSeleccionar();
        CargarVentana("Consultas");
        btnConsultas.getStyleClass().add("btn5");
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
        DesSeleccionar();
        CargarVentana("Documentacion");
        btnDocumentacion.getStyleClass().add("btn5");
        btnDocumentacion.setDisable(false);
    }

    @FXML
    private void Articulos(ActionEvent event) {
        btnArticulos.setDisable(true);
        DesSeleccionar();
        CargarVentana("Articulos");
        btnArticulos.getStyleClass().add("btn5");
        btnArticulos.setDisable(false);
    }

    @FXML
    private void AbonoApartado(ActionEvent event) {
        btnAbonoApartado.setDisable(true);
        DesSeleccionar();
        CargarVentana("AbonoApartado");
        btnAbonoApartado.getStyleClass().add("btn5");
        btnAbonoApartado.setDisable(false);
    }

    @FXML
    private void Reparacion(ActionEvent event) {
        btnReparacion.setDisable(true);
        DesSeleccionar();
        CargarVentana("Reparacion");
        btnReparacion.getStyleClass().add("btn5");
        btnReparacion.setDisable(false);
    }

    @FXML
    private void AbonoReparacion(ActionEvent event) {
        btnAbonoReparacion.setDisable(true);
        DesSeleccionar();
        CargarVentana("AbonoReparacion");
        btnAbonoReparacion.getStyleClass().add("btn5");
        btnAbonoReparacion.setDisable(false);
    }
    
    private void Seleccionados(ObservableList lista){
        if(lista.size()>2){
            lista.remove(2);
        }
    }
    
    private void DesSeleccionar(){
        Seleccionados(btnInicio.getStyleClass());
        Seleccionados(btnAbonoApartado.getStyleClass());
        Seleccionados(btnAbonoReparacion.getStyleClass());
        Seleccionados(btnApartado.getStyleClass());
        Seleccionados(btnArticulos.getStyleClass());
        Seleccionados(btnConsultas.getStyleClass());
        Seleccionados(btnDocumentacion.getStyleClass());
        Seleccionados(btnReparacion.getStyleClass());
        Seleccionados(btnVenta.getStyleClass());
    }
}
