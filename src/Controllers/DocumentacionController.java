
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {   
    }    

    @FXML
    private void MouseMano(MouseEvent event) {
        btnAbonos.setCursor(Cursor.HAND);
        btnArticulos.setCursor(Cursor.HAND);
        btnClientes.setCursor(Cursor.HAND);
        btnDetallesFacturas.setCursor(Cursor.HAND);
        btnFacturas.setCursor(Cursor.HAND);
        btnTodo.setCursor(Cursor.HAND);
    }

    @FXML
    private void Facturas(ActionEvent event) {
        btnFacturas.setDisable(true);
        CargarVentana("Facturas");
        btnFacturas.setDisable(false);
    }

    @FXML
    private void DetallesFacturas(ActionEvent event) {
        btnDetallesFacturas.setDisable(true);
        CargarVentana("DetallesFacturas");
        btnDetallesFacturas.setDisable(false);
    }

    @FXML
    private void Abonos(ActionEvent event) {
        btnAbonos.setDisable(true);
        CargarVentana("Abonos");
        btnAbonos.setDisable(false);
    }

    @FXML
    private void Articulos(ActionEvent event) {
        btnArticulos.setDisable(true);
        CargarVentana("Articulos");
        btnArticulos.setDisable(false);
    }

    @FXML
    private void Clientes(ActionEvent event) {
        btnClientes.setDisable(true);
        CargarVentana("Clientes");
        btnClientes.setDisable(false);
    }

    @FXML
    private void Todo(ActionEvent event) {
        btnTodo.setDisable(true);
        CargarVentana("Todo");
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
}
