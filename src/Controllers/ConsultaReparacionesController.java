/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Josue
 */
public class ConsultaReparacionesController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<?> tblReparaciones;
    @FXML
    private Label lblArticulo;
    @FXML
    private Label lblNoArticulo;
    @FXML
    private TextField txtArticulo;
    @FXML
    private Label lblDescripcion;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblNoDescripcion;
    @FXML
    private Label lblNombrePersona;
    @FXML
    private TextField txtNombrePersona;
    @FXML
    private Label lblNoNombrePersona;
    @FXML
    private Label lblNumeroPersona;
    @FXML
    private TextField txtNumeroPersona;
    @FXML
    private Label lblNoNumeroPersona;
    @FXML
    private Label lblEstadoReparacion;
    @FXML
    private RadioButton rbtnEnEspera;
    @FXML
    private ToggleGroup rbtngEstadosReparacion;
    @FXML
    private RadioButton rbtnEnProceso;
    @FXML
    private RadioButton rbtnFinalizada;
    @FXML
    private Label lblTipoDePago;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private ToggleGroup rbtngTiposDePago;
    @FXML
    private RadioButton rbtnTarjeta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Buscar(KeyEvent event) {
    }

    @FXML
    private void Transladar(MouseEvent event) {
    }
    
}
