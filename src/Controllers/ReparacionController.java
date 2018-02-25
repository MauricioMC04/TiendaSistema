/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Josue
 */
public class ReparacionController implements Initializable {

    @FXML
    private Label lblNoNumeroPersona;
    @FXML
    private Label lblNoMontoPagado;
    @FXML
    private Label lblNoEstado;
    @FXML
    private Label lblNoArticulo;
    @FXML
    private Label lblNoNombrePersona;
    @FXML
    private Label lblNoMontoTotal;
    @FXML
    private TextField txtArticulo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtNombrePersona;
    @FXML
    private TextField txtNumeroPersona;
    @FXML
    private TextField txtMontoTotal;
    @FXML
    private TextField txtMontoPagado;
    @FXML
    private RadioButton rbtnTarjeta;
    @FXML
    private ToggleGroup rbtngTiposDePago;
    @FXML
    private RadioButton rbtnFinalizada;
    @FXML
    private ToggleGroup rbtngEstados;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private RadioButton rbtnEnEspera;
    @FXML
    private RadioButton rbtnEnProceso;
    @FXML
    private Label lblNoTipoPago;
    @FXML
    private Button btnReparacion;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnNuevaReparacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RealizarReparacion(ActionEvent event) {
    }

    @FXML
    private void Imprimir(ActionEvent event) {
    }

    @FXML
    private void NuevaReparacion(ActionEvent event) {
    }
    
}
