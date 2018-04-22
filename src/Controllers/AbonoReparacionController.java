
package Controllers;

import Models.Cliente;
import DataBase.DatosAbonosReparacion;
import DataBase.DatosReparacion;
import Models.Abono;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class AbonoReparacionController implements Initializable {

    private final DatosAbonosReparacion datosAbonosReparacion = new DatosAbonosReparacion();
    private final DatosReparacion datosReparacion = new DatosReparacion();
    private Reparacion reparacion = null;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Reparacion> tblReparaciones;
    @FXML
    private Label lblArticulo;
    @FXML
    private TextField txtArticulo;
    @FXML
    private Label lblDescripcion;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblTotalFactura;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPagadoFactura;
    @FXML
    private Label lblPagado;
    @FXML
    private Label lblFaltanteFactura;
    @FXML
    private Label lblFaltante;
    @FXML
    private TextField txtMontoAbono;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private ToggleGroup rbtngTipoPago;
    @FXML
    private RadioButton rbtnTarjeta;
    @FXML
    private Button btnRealizarAbono;
    @FXML
    private Label lblIdClienteFactura;
    @FXML
    private Label lblIdCliente;
    @FXML
    private Label lblNombreClienteFactura;
    @FXML
    private Label lblNumeroClienteFactura;
    @FXML
    private Label lblNombreCliente;
    @FXML
    private Label lblNumeroCliente;
    @FXML
    private Label lblNoMonto;
    @FXML
    private Label lblMontoAbono;
    @FXML
    private Label lblTipoDePago;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnNuevoAbono;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRealizarAbono.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        tblReparaciones.setCursor(Cursor.CROSSHAIR);
        btnImprimir.setCursor(Cursor.HAND);
        btnNuevoAbono.setCursor(Cursor.HAND);
        btnImprimir.setVisible(false);
        btnNuevoAbono.setVisible(false);
        ParteReparacion(false);
        CargarColumnas();
        CargarReparaciones("Ninguna");
        lblNoMonto.setVisible(false);
    }    

    @FXML
    private void BuscarReparacion(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarReparaciones("Ninguna");
        }else{
            CargarReparaciones(txtBusqueda.getText());
        }
    }

    @FXML
    private void MostrarReparacion(MouseEvent event) {
        if(tblReparaciones.getSelectionModel().getSelectedItem() != null){
            CargarReparacion(tblReparaciones.getSelectionModel().getSelectedItem());
            ParteReparacion(true);
        }
    }

    @FXML
    private void RealizarAbono(ActionEvent event) {
        btnRealizarAbono.setDisable(true);
        if(ValidarMonto()){
            int tipoPago = 1;
            if(rbtnTarjeta.isSelected()){
                tipoPago = 2;
            }
            double monto = Double.parseDouble(txtMontoAbono.getText());
            reparacion = tblReparaciones.getSelectionModel().getSelectedItem();
            Calendar c = Calendar.getInstance();   
            Date fecha = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            if(datosAbonosReparacion.RealizarAbono(new Abono(0, reparacion.getCodigoReparacion(), monto, fecha, 
            tipoPago))){
                reparacion.setMontoPagado(reparacion.getMontoTotal());
                btnImprimir.setVisible(true);
                btnNuevoAbono.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Error al realizar el Abono");
                btnRealizarAbono.setDisable(false);
            }
        }else{
            btnRealizarAbono.setDisable(false);
        }
    }
    
    private void ParteReparacion(boolean bandera){
        lblArticulo.setVisible(bandera);
        txtArticulo.setVisible(bandera);
        lblDescripcion.setVisible(bandera);
        txtDescripcion.setVisible(bandera);
        lblIdCliente.setVisible(bandera);
        lblIdClienteFactura.setVisible(bandera);
        lblNombreCliente.setVisible(bandera);
        lblNombreClienteFactura.setVisible(bandera);
        lblNumeroCliente.setVisible(bandera);
        lblNumeroClienteFactura.setVisible(bandera);
        lblTotal.setVisible(bandera);
        lblTotalFactura.setVisible(bandera);
        lblPagado.setVisible(bandera);
        lblPagadoFactura.setVisible(bandera);
        lblFaltante.setVisible(bandera);
        lblFaltanteFactura.setVisible(bandera);
        lblMontoAbono.setVisible(bandera);
        txtMontoAbono.setVisible(bandera);
        lblTipoDePago.setVisible(bandera);
        rbtnEfectivo.setVisible(bandera);
        rbtnTarjeta.setVisible(bandera);
        btnRealizarAbono.setVisible(bandera);
    }
    
    private void CargarColumnas() {
        TableColumn tblCCodigoReparacion = new TableColumn("Codigo");
        tblCCodigoReparacion.setCellValueFactory(new PropertyValueFactory<>("codigoReparacion"));
        tblCCodigoReparacion.setPrefWidth(51);
        TableColumn tblCArticulo = new TableColumn("Articulo");
        tblCArticulo.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        tblCArticulo.setPrefWidth(171);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tblCFecha.setPrefWidth(90);
        TableColumn tblCMontoTotal = new TableColumn("Monto Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        tblCMontoTotal.setPrefWidth(90);
        TableColumn tblCMontoPagado = new TableColumn("Monto Pagado");
        tblCMontoPagado.setCellValueFactory(new PropertyValueFactory<>("montoPagado"));
        tblCMontoPagado.setPrefWidth(90);
        tblReparaciones.getColumns().addAll(tblCCodigoReparacion, tblCArticulo, tblCFecha, tblCMontoTotal, 
            tblCMontoPagado);
    }
    
    private void CargarReparaciones(String busqueda){
        tblReparaciones.getItems().clear();
        tblReparaciones.getItems().addAll(datosReparacion.CargarReparacionesPendientes(1, busqueda));
    }
    
    private void CargarReparacion(Reparacion reparacion){
        txtArticulo.setText(reparacion.getArticulo());
        txtDescripcion.setText(reparacion.getDescripcion());
        lblIdCliente.setText(Integer.toString(reparacion.getIdCliente()));
        Cliente cliente = datosReparacion.Cliente(reparacion.getIdCliente());
        lblNombreCliente.setText(cliente.getNombre());
        lblNumeroCliente.setText(cliente.getNumero());
        lblTotal.setText(String.valueOf(reparacion.getMontoTotal()));
        lblPagado.setText(String.valueOf(reparacion.getMontoPagado()));
        lblFaltante.setText(String.valueOf(reparacion.getMontoTotal() - reparacion.getMontoPagado()));
    }
    
    private boolean ValidarMonto(){
        if(txtMontoAbono.getText().equals("")){
            lblNoMonto.setVisible(true);
            return false;
        }
        try {
            double monto = Double.parseDouble(txtMontoAbono.getText());
            if(monto > Double.parseDouble(lblFaltante.getText())){
                JOptionPane.showMessageDialog(null, "Monto Invalido\nEl Monto del Abono no puede ser mayor al monto"
                    + "faltante");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Monto Invalido\nNo digite letras en el espacio del Monto del Abono");
            return false;
        }
        return true;
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        datosReparacion.ImprimirReparacion(reparacion);
        btnImprimir.setDisable(false);
    }

    @FXML
    private void NuevoAbono(ActionEvent event) {
        btnNuevoAbono.setDisable(true);
        txtMontoAbono.setText("");
        rbtnEfectivo.setSelected(true);
        ParteReparacion(false);
        tblReparaciones.getSelectionModel().select(null);
        if(txtBusqueda.getText().equals("")){
            CargarReparaciones("Ninguna");
        }else{
            CargarReparaciones(txtBusqueda.getText());
        }
        lblNoMonto.setVisible(false);
        btnImprimir.setVisible(false);
        btnNuevoAbono.setVisible(false);
        btnRealizarAbono.setDisable(false);
        reparacion = null;
        btnNuevoAbono.setDisable(false);
    }
}
