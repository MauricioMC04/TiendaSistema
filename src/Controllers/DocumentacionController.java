
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class DocumentacionController implements Initializable {

    @FXML
    private Button btnFacturas;
    @FXML
    private Button btnDetallesFacturas;
    @FXML
    private Button btnAbonos;
    @FXML
    private Button btnArticulos;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnTodo;
    @FXML
    private AnchorPane Espacio;
    @FXML
    private Button btnReparaciones;
    @FXML
    private Button btnAbonosReparaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAbonos.setCursor(Cursor.HAND);
        btnArticulos.setCursor(Cursor.HAND);
        btnClientes.setCursor(Cursor.HAND);
        btnDetallesFacturas.setCursor(Cursor.HAND);
        btnFacturas.setCursor(Cursor.HAND);
        btnTodo.setCursor(Cursor.HAND);
        btnReparaciones.setCursor(Cursor.HAND);
        btnAbonosReparaciones.setCursor(Cursor.HAND);
        btnFacturas.getStyleClass().add("btn5");
        CargarVentana("Facturas");        
    }    

    @FXML
    private void Facturas(ActionEvent event) {
        btnFacturas.setDisable(true);
        CargarVentana("Facturas");
        QuitarColor();
        btnFacturas.getStyleClass().add("btn5");
        btnFacturas.setDisable(false);
    }

    @FXML
    private void DetallesFacturas(ActionEvent event) {
        btnDetallesFacturas.setDisable(true);
        CargarVentana("DetallesFacturas");
        QuitarColor();
        btnDetallesFacturas.getStyleClass().add("btn5");
        btnDetallesFacturas.setDisable(false);
    }


    @FXML
    private void Articulos(ActionEvent event) {
        btnArticulos.setDisable(true);
        CargarVentana("Articulos");
        QuitarColor();
        btnArticulos.getStyleClass().add("btn5");
        btnArticulos.setDisable(false);
    }

    @FXML
    private void Clientes(ActionEvent event) {
        btnClientes.setDisable(true);
        CargarVentana("Clientes");
        QuitarColor();
        btnClientes.getStyleClass().add("btn5");
        btnClientes.setDisable(false);
    }

    @FXML
    private void Todo(ActionEvent event) {
        btnTodo.setDisable(true);
        CargarVentana("Todo");
        QuitarColor();
        btnTodo.getStyleClass().add("btn5");
        btnTodo.setDisable(false);
    }
    
    private void CargarVentana(String ventana){
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Views/Documentacion"+ventana+".fxml"));
            Espacio.getChildren().clear();
            Espacio.getChildren().setAll(pane);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
    
    private void Seleccionados(ObservableList lista){
        if(lista.size()>2){
            lista.remove(2);
        }
    }

    @FXML
    private void AbonosApartado(ActionEvent event) {
        btnAbonos.setDisable(true);
        CargarVentana("AbonosApartados");
        QuitarColor();
        btnAbonos.getStyleClass().add("btn5");
        btnAbonos.setDisable(false);
    }

    @FXML
    private void Reparaciones(ActionEvent event) {
        btnReparaciones.setDisable(true);
        CargarVentana("Reparaciones");
        QuitarColor();
        btnReparaciones.getStyleClass().add("btn5");
        btnReparaciones.setDisable(false);
    }

    @FXML
    private void AbonosReparacion(ActionEvent event) {
        btnAbonosReparaciones.setDisable(true);
        CargarVentana("AbonosReparaciones");
        QuitarColor();
        btnAbonosReparaciones.getStyleClass().add("btn5");
        btnAbonosReparaciones.setDisable(false);
    }
    
    private void QuitarColor(){
        Seleccionados(btnFacturas.getStyleClass());
        Seleccionados(btnDetallesFacturas.getStyleClass());
        Seleccionados(btnAbonos.getStyleClass());
        Seleccionados(btnArticulos.getStyleClass());
        Seleccionados(btnClientes.getStyleClass());
        Seleccionados(btnTodo.getStyleClass());
        Seleccionados(btnReparaciones.getStyleClass());
        Seleccionados(btnAbonosReparaciones.getStyleClass());
    }
}
