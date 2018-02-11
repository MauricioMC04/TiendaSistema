
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

public class ConsultasController implements Initializable {

    @FXML
    private Button btnFacturas;
    @FXML
    private Button btnApartados;
    @FXML
    private Button btnAbonos;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnFechas;
    @FXML
    private Button btnArticulos;
    @FXML
    private AnchorPane Espacio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void MouseMano(MouseEvent event) {
        btnAbonos.setCursor(Cursor.HAND);
        btnApartados.setCursor(Cursor.HAND);
        btnArticulos.setCursor(Cursor.HAND);
        btnClientes.setCursor(Cursor.HAND);
        btnFacturas.setCursor(Cursor.HAND);
        btnFechas.setCursor(Cursor.HAND);
    }

    @FXML
    private void Facturas(ActionEvent event) {
        CargarVentana("Facturas");
    }

    @FXML
    private void Apartados(ActionEvent event) {
        CargarVentana("Apartados");
    }

    @FXML
    private void Abonos(ActionEvent event) {
        CargarVentana("Abonos");
    }

    @FXML
    private void Clientes(ActionEvent event) {
        CargarVentana("Clientes");
    }

    @FXML
    private void Fechas(ActionEvent event) {
        CargarVentana("Fechas");
    }

    @FXML
    private void Articulos(ActionEvent event) {
        CargarVentana("Articulos");
    }
    
    private void CargarVentana(String ventana){
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Views/Consulta"+ventana+".fxml"));
            Espacio.getChildren().clear();
            Espacio.getChildren().setAll(pane);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
}
