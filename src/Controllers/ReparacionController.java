
package Controllers;

import DataBase.DatosReparacion;
import Models.Reparacion;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.swing.JOptionPane;

public class ReparacionController implements Initializable {

    private Reparacion reparacion;
    private final DatosReparacion datosReparacion = new DatosReparacion();
    
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnImprimir.setCursor(Cursor.HAND);
        btnNuevaReparacion.setCursor(Cursor.HAND);
        btnReparacion.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnEnEspera.setCursor(Cursor.HAND);
        rbtnEnProceso.setCursor(Cursor.HAND);
        rbtnFinalizada.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        Reestablecer(false);
    }    

    @FXML
    private void RealizarReparacion(ActionEvent event) {
        btnReparacion.setDisable(true);
        if(ValidarInformacion()){
            Calendar c = Calendar.getInstance();   
            Date fecha = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            int tipoDePago = 1;
            if(rbtnTarjeta.isSelected()){
                tipoDePago = 2;
            }
            int Estado = 1;
            if(rbtnEnProceso.isSelected()){
                Estado = 2;
            }
            if(rbtnFinalizada.isSelected()){
                Estado = 3;
            }
            double MontoTotal = 0;
            double MontoPagado = 0;
            if(!txtMontoTotal.getText().equals("")){
                MontoTotal = Double.parseDouble(txtMontoTotal.getText());
            }
            if(!txtMontoPagado.getText().equals("")){
                MontoPagado = Double.parseDouble(txtMontoPagado.getText());
            }
            reparacion = new Reparacion(0, fecha, 0, txtArticulo.getText(), txtDescripcion.getText(), Estado, MontoTotal 
                , MontoPagado, tipoDePago);
            if(datosReparacion.RealizarReparacion(reparacion, txtNombrePersona.getText(), txtNumeroPersona.getText())){
                btnImprimir.setVisible(true);
                btnNuevaReparacion.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Error al realizar la Reparacion");
                btnReparacion.setDisable(false);
            }
        }else{
            btnReparacion.setDisable(false);
        }
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        datosReparacion.ImprimirReparacion(reparacion);
        btnImprimir.setDisable(false);
    }

    @FXML
    private void NuevaReparacion(ActionEvent event) {
        Reestablecer(false);
        reparacion = null;
        txtArticulo.setText("");
        txtDescripcion.setText("");
        txtMontoPagado.setText("");
        txtMontoTotal.setText("");
        txtNombrePersona.setText("");
        txtNumeroPersona.setText("");
        btnReparacion.setDisable(false);
        rbtnEfectivo.setSelected(true);
        rbtnEnEspera.setSelected(true);
    }
    
    private void Reestablecer(boolean bandera){
        lblNoArticulo.setVisible(bandera);
        lblNoEstado.setVisible(bandera);
        lblNoMontoPagado.setVisible(bandera);
        lblNoMontoTotal.setVisible(bandera);
        lblNoNombrePersona.setVisible(bandera);
        lblNoNumeroPersona.setVisible(bandera);
        lblNoTipoPago.setVisible(bandera);
        btnImprimir.setVisible(bandera);
        btnNuevaReparacion.setVisible(bandera);
    }
    
    private boolean ValidarInformacion(){
        if(txtArticulo.getText().equals("")){
            lblNoArticulo.setVisible(true);
            return false;
        }
        if(txtNombrePersona.getText().equals("")){
            lblNoNombrePersona.setVisible(true);
            return false;
        }
        if(txtNumeroPersona.getText().equals("")){
            lblNoNumeroPersona.setVisible(true);
            return false;
        }
        if(!CompararMontos()){
            JOptionPane.showMessageDialog(null, "Error en los Montos\nEl Monto Pagado no puede ser mayor al Monto Total"
                + "\nNo digite letras los espacios de los montos\nRedigite sus Montos");
            return false;
        }
        return true;
    }
    
    private boolean CompararMontos(){
        double montoTotal;
        double montoPagado;
        try {
            if(txtMontoTotal.getText().equals("")){
                montoTotal = 0;
            }else{
                montoTotal = Double.parseDouble(txtMontoTotal.getText());
            }
            if(txtMontoPagado.getText().equals("")){
                montoPagado = 0;
            }else{
                montoPagado = Double.parseDouble(txtMontoPagado.getText());
            }
        } catch (Exception e) {
            return false;
        }
        return montoPagado <= montoTotal;
    }
}
