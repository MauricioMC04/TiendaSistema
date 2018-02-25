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
    @FXML
    private Button btnArticulos1;
    @FXML
    private Button btnArticulos2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAbonos.setCursor(Cursor.HAND);
        btnApartados.setCursor(Cursor.HAND);
        btnArticulos.setCursor(Cursor.HAND);
        btnClientes.setCursor(Cursor.HAND);
        btnFacturas.setCursor(Cursor.HAND);
        btnFechas.setCursor(Cursor.HAND);
        btnFacturas.getStyleClass().add("btn5");
        CargarVentana("Facturas");
    }

    @FXML
    private void Facturas(ActionEvent event) {
        btnFacturas.setDisable(true);
        CargarVentana("Facturas");
        DesSeleccionar();
        btnFacturas.getStyleClass().add("btn5");
        btnFacturas.setDisable(false);
    }
   
    @FXML
    private void Apartados(ActionEvent event) {
        btnApartados.setDisable(true);
        CargarVentana("Apartados");
        DesSeleccionar();
        btnApartados.getStyleClass().add("btn5");
        btnApartados.setDisable(false);
    }

    @FXML
    private void Abonos(ActionEvent event) {
        btnAbonos.setDisable(true);
        CargarVentana("Abonos");
        DesSeleccionar();
        btnAbonos.getStyleClass().add("btn5");
        btnAbonos.setDisable(false);
    }

    @FXML
    private void Clientes(ActionEvent event) {
        btnClientes.setDisable(true);
        CargarVentana("Clientes");
        DesSeleccionar();
        btnClientes.getStyleClass().add("btn5");
        btnClientes.setDisable(false);
    }

    @FXML
    private void Fechas(ActionEvent event) {
        btnFechas.setDisable(true);
        CargarVentana("Fechas");
        DesSeleccionar();
        btnFechas.getStyleClass().add("btn5");
        btnFechas.setDisable(false);
    }

    @FXML
    private void Articulos(ActionEvent event) {
        btnArticulos.setDisable(true);
        CargarVentana("Articulos");
        DesSeleccionar();
        btnArticulos.getStyleClass().add("btn5");
        btnArticulos.setDisable(false);
    }

    private void CargarVentana(String ventana) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Views/Consulta" + ventana + ".fxml"));
            Espacio.getChildren().clear();
            Espacio.getChildren().setAll(pane);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    private void Seleccionados(ObservableList lista) {
        if (lista.size() > 2) {
            lista.remove(2);
        }
    }
    
    private void DesSeleccionar(){
        Seleccionados(btnFacturas.getStyleClass());
        Seleccionados(btnAbonos.getStyleClass());
        Seleccionados(btnApartados.getStyleClass());
        Seleccionados(btnArticulos.getStyleClass());
        Seleccionados(btnClientes.getStyleClass());
        Seleccionados(btnFechas.getStyleClass());
    }
}
