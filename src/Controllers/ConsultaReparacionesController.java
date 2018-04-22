
package Controllers;

import DataBase.DatosReparacion;
import Models.Cliente;
import Models.Reparacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class ConsultaReparacionesController implements Initializable {
    
    private final DatosReparacion datosReparaciones = new DatosReparacion();

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Reparacion> tblReparaciones;
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
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblReparaciones.setCursor(Cursor.CROSSHAIR);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnEnEspera.setCursor(Cursor.HAND);
        rbtnEnProceso.setCursor(Cursor.HAND);
        rbtnFinalizada.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnEditar.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
        btnEliminar.setCursor(Cursor.HAND);
        lblNoArticulo.setVisible(false);
        lblNoNombrePersona.setVisible(false);
        lblNoNumeroPersona.setVisible(false);
        ParteReparacion(false);
        CargarColumnasReparacion();
        CargarReparaciones("Ninguna");
    }    

    @FXML
    private void Buscar(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarReparaciones("Ninguna");
        }else{
            CargarReparaciones(txtBusqueda.getText());
        }
        ParteReparacion(false);
        lblNoArticulo.setVisible(false);
        lblNoNombrePersona.setVisible(false);
        lblNoNumeroPersona.setVisible(false);
    }

    @FXML
    private void Transladar(MouseEvent event) {
        Reparacion reparacion = tblReparaciones.getSelectionModel().getSelectedItem();
        if(reparacion != null){
            txtArticulo.setText(reparacion.getArticulo());
            txtDescripcion.setText(reparacion.getDescripcion());
            Cliente cliente = datosReparaciones.Cliente(reparacion.getIdCliente());
            txtNombrePersona.setText(cliente.getNombre());
            txtNumeroPersona.setText(cliente.getNumero());
            switch(reparacion.getEstado()){
                case 1:
                    rbtnEnEspera.setSelected(true);
                break;
                case 2:
                    rbtnEnProceso.setSelected(true);
                break;
                default:
                    rbtnFinalizada.setSelected(true);
                break;
            }
            if(reparacion.getTipoPago() == 1){
                rbtnEfectivo.setSelected(true);
            }else{
                rbtnTarjeta.setSelected(true);
            }
            ParteReparacion(true);
        }else{
            ParteReparacion(false);
        }
    }
    
    private void ParteReparacion(boolean bandera){
        lblArticulo.setVisible(bandera);
        txtArticulo.setVisible(bandera);
        lblDescripcion.setVisible(bandera);
        txtDescripcion.setVisible(bandera);
        lblNombrePersona.setVisible(bandera);
        txtNombrePersona.setVisible(bandera);
        lblNumeroPersona.setVisible(bandera);
        txtNumeroPersona.setVisible(bandera);
        lblEstadoReparacion.setVisible(bandera);
        rbtnEnEspera.setVisible(bandera);
        rbtnEnProceso.setVisible(bandera);
        rbtnFinalizada.setVisible(bandera);
        lblTipoDePago.setVisible(bandera);
        rbtnEfectivo.setVisible(bandera);
        rbtnTarjeta.setVisible(bandera);
        btnEditar.setVisible(bandera);
        btnImprimir.setVisible(bandera);
        btnEliminar.setVisible(bandera);
    }
    private void CargarColumnasReparacion(){
        TableColumn tblCCodigoReparacion = new TableColumn("Codigo");
        tblCCodigoReparacion.setCellValueFactory(new PropertyValueFactory<>("codigoReparacion"));
        tblCCodigoReparacion.setPrefWidth(50);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tblCFecha.setPrefWidth(80);
        TableColumn tblCArticulo = new TableColumn("Articulo");
        tblCArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        tblCArticulo.setPrefWidth(140);
        TableColumn tblCMontoTotal = new TableColumn("Monto Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        tblCMontoTotal.setPrefWidth(80);
        TableColumn tblCMontoPagado = new TableColumn("Monto Pagado");
        tblCMontoPagado.setCellValueFactory(new PropertyValueFactory<>("montoPagado"));
        tblCMontoPagado.setPrefWidth(90);
        tblReparaciones.getColumns().addAll(tblCCodigoReparacion, tblCFecha, tblCArticulo, tblCMontoTotal,
        tblCMontoPagado);
    }

    @FXML
    private void Editar(ActionEvent event) {
        btnEditar.setDisable(true);
        Reparacion reparacion = tblReparaciones.getSelectionModel().getSelectedItem();
        if(reparacion == null){
            JOptionPane.showMessageDialog(null, "Seleccione una reparacion");
            return;
        }
        if(Validar()){
            int tipoPago = 1;
            int estado = 1;
            if(rbtnTarjeta.isSelected()){
                tipoPago = 2;
            }
            if(rbtnEnProceso.isSelected()){
                estado = 2;
            }
            if(rbtnFinalizada.isSelected()){
                estado = 3;
            }
            if(datosReparaciones.EditarReparacion(reparacion.getCodigoReparacion(), txtArticulo.getText(), txtDescripcion.getText(), 
            txtNombrePersona.getText(), txtNumeroPersona.getText(), estado, tipoPago)){
                lblNoArticulo.setVisible(false);
                lblNoNombrePersona.setVisible(false);
                lblNoNumeroPersona.setVisible(false);
                ParteReparacion(false);
                if(txtBusqueda.getText().equals("")){
                    CargarReparaciones("Ninguna");
                }else{
                    CargarReparaciones(txtBusqueda.getText());
                }
            }else{
                JOptionPane.showMessageDialog(null, "Error al editar la Reparacion");
            }
        }
        btnEditar.setDisable(false);
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        Reparacion reparacion = tblReparaciones.getSelectionModel().getSelectedItem();
        if(reparacion != null){
            datosReparaciones.ImprimirReparacion(reparacion);
        }
        btnImprimir.setDisable(false);
    }
    
    private void CargarReparaciones(String busqueda){
        tblReparaciones.getItems().clear();
        tblReparaciones.setItems(datosReparaciones.CargarReparacionesPendientes(2, busqueda));
    }
    
    private boolean Validar(){
        if(txtArticulo.getText().equals("")){
            lblNoArticulo.setVisible(true);
            return false;
        }else{
            lblNoArticulo.setVisible(false);
        }
        if(txtNombrePersona.getText().equals("")){
            lblNoNombrePersona.setVisible(true);
            return false;
        }else{
            lblNoNumeroPersona.setVisible(false);
        }
        if(txtNumeroPersona.getText().equals("")){
            lblNoNumeroPersona.setVisible(true);
            return false;
        }else{
            lblNoNumeroPersona.setVisible(false);
        }
        return true;
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        Reparacion reparacion = tblReparaciones.getSelectionModel().getSelectedItem();
        if(reparacion != null){
            Object[] options = { "Eliminar", "Cancelar" };
            int choice = JOptionPane.showOptionDialog(null,
                "Desea eliminar esta Reparacion\nEsta accion NO podra revertirse", 
                "Eliminar Reparacion Confirmacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);
            if (choice == JOptionPane.YES_OPTION){
                if(datosReparaciones.Eliminar(reparacion)){
                    lblNoArticulo.setVisible(false);
                    lblNoNombrePersona.setVisible(false);
                    lblNoNumeroPersona.setVisible(false);
                    ParteReparacion(false);
                    if(txtBusqueda.getText().equals("")){
                        CargarReparaciones("Ninguna");
                    }else{
                        CargarReparaciones(txtBusqueda.getText());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar la Reparacion");
                }
            }
        }
    }
}
