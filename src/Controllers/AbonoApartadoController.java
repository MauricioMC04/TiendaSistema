
package Controllers;

import Models.Abono;
import Models.Cliente;
import DataBase.DatosAbonoApartado;
import DataBase.DatosFactura;
import Models.DetalleFactura;
import Models.Factura;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class AbonoApartadoController implements Initializable {

    DatosFactura datosFactura = new DatosFactura();
    DatosAbonoApartado datosAbono = new DatosAbonoApartado();
    private Factura apartado = null;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Factura> tblFacturas;
    @FXML
    private TableView<DetalleFactura> tblArticulos;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPagado;
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
    private Label lblTotalFactura;
    @FXML
    private Label lblPagadoFactura;
    @FXML
    private Label lblFaltanteFactura;
    @FXML
    private Label lblIdClienteFactura;
    @FXML
    private Label lblIdCliente;
    @FXML
    private Label lblNumeroClienteFactura;
    @FXML
    private Label lblNombreCliente;
    @FXML
    private Label lblNumeroCliente;
    @FXML
    private Label lblNombreClienteFactura;
    @FXML
    private Label lblNoMonto;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnNuevoAbono;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtBusqueda.setCursor(Cursor.TEXT);
        txtMontoAbono.setCursor(Cursor.TEXT);
        tblArticulos.setCursor(Cursor.CROSSHAIR);
        tblFacturas.setCursor(Cursor.CROSSHAIR);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnRealizarAbono.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
        btnImprimir.setVisible(false);
        btnNuevoAbono.setCursor(Cursor.HAND);
        btnNuevoAbono.setVisible(false);
        ParteFactura(false);
        CargarColumnasDetalle();
        CargarColumnasFactura();
        CargarFacturas("Ninguna");
        lblNoMonto.setVisible(false);
    }    
    
    @FXML
    private void BuscarFactura(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarFacturas("Ninguna");
        }else{
            CargarFacturas(txtBusqueda.getText());
        }
    }

    @FXML
    private void RealizarAbono(ActionEvent event) {
        btnRealizarAbono.setDisable(true);
        apartado = tblFacturas.getSelectionModel().getSelectedItem();
        if(apartado != null){
            if(txtMontoAbono.getText().equals("")){
                lblNoMonto.setVisible(true);
            }else{
                try {
                    double monto = Double.parseDouble(txtMontoAbono.getText());
                    if(monto <= Double.parseDouble(lblFaltante.getText())){
                        if(Abono(apartado.getCodigoFactura(), monto)){
                            btnImprimir.setVisible(true);
                            btnNuevoAbono.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al realizar el Abono");
                              btnRealizarAbono.setDisable(false);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Monto del Abono Invalido\nEl monto faltante es de " + 
                            lblFaltante.getText() + "\nRedigite el Monto del Abono");
                          btnRealizarAbono.setDisable(false);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Monto de Abono Invalido\nNo digite letras en el espacio del monto "
                        + "del Abono\nRedigite el Monto del Abono");
                      btnRealizarAbono.setDisable(false);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione la factura sobre la que desea hacer el Abono");
              btnRealizarAbono.setDisable(false);
        }
      
    }

    @FXML
    private void MostrarFactura(MouseEvent event) {
        Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
        if(factura != null){
            ParteFactura(true);
            CargarDatosFactura(factura);
        }else{
            ParteFactura(false);
        }
    }
    
    private void ParteFactura(boolean bandera){
        lblFaltante.setVisible(bandera);
        lblFaltanteFactura.setVisible(bandera);
        lblPagado.setVisible(bandera);
        lblPagadoFactura.setVisible(bandera);
        lblTotal.setVisible(bandera);
        lblTotalFactura.setVisible(bandera);
        tblArticulos.setVisible(bandera);
        lblIdCliente.setVisible(bandera);
        lblIdClienteFactura.setVisible(bandera);
        lblNombreCliente.setVisible(bandera);
        lblNumeroCliente.setVisible(bandera);
        lblNombreClienteFactura.setVisible(bandera);
        lblNumeroClienteFactura.setVisible(bandera);
    }
    
     private void CargarColumnasFactura() {
        TableColumn tblCCodigoFactura = new TableColumn("Codigo");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(81);
        TableColumn tblCMontoTotal = new TableColumn("Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<>("MontoTotal"));
        tblCMontoTotal.setMinWidth(81);
        TableColumn tblCMontoPagado = new TableColumn("Pagado");
        tblCMontoPagado.setCellValueFactory(new PropertyValueFactory<>("MontoPagado"));
        tblCMontoPagado.setMinWidth(81);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tblCFecha.setMinWidth(81);
        TableColumn tblCIdCliente = new TableColumn("Cliente");
        tblCIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tblCIdCliente.setMinWidth(81);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(81);
        tblFacturas.getColumns().addAll(tblCCodigoFactura, tblCMontoTotal, tblCMontoPagado, tblCFecha, tblCIdCliente, tblCIdTipoDePago);
    }
    
    private void CargarColumnasDetalle() {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tblCNombreArticulo.setMinWidth(143);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        tblCPrecioArticulo.setMinWidth(143);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<>("Descuento"));
        tblCDescuento.setMinWidth(143);
        tblArticulos.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
    }
    
    private void CargarFacturas(String busqueda){
        tblFacturas.getItems().clear();
        tblFacturas.setItems(datosFactura.FacturasApartados(busqueda));
        ParteFactura(false);
        txtMontoAbono.setText("");
        rbtnEfectivo.setSelected(true);
    }
    
    private void CargarDatosFactura(Factura factura){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosFactura.DetalleFactura(factura.getCodigoFactura()));
        Cliente cliente = datosFactura.Cliente(factura.getIdCliente());
        lblIdCliente.setText(Integer.toString(cliente.getIdCliente()));
        lblNombreCliente.setText(cliente.getNombre());
        lblNumeroCliente.setText(cliente.getNumero());
        lblTotal.setText(String.valueOf(factura.getMontoTotal()));
        lblPagado.setText(String.valueOf(factura.getMontoPagado()));
        lblFaltante.setText(String.valueOf(factura.getMontoTotal() - factura.getMontoPagado()));
    }
    
    private boolean Abono(int codigoFactura, double monto){
        Calendar c = Calendar.getInstance();   
        Date fecha = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        int tipoDePago = 1;
        if(rbtnTarjeta.isSelected()){
            tipoDePago = 2;
        }
        return datosAbono.RealizarAbono(new Abono(0,codigoFactura,monto,fecha,tipoDePago));
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        datosFactura.ImprimirFactura(apartado.getCodigoFactura());
        btnImprimir.setDisable(false);
    }

    @FXML
    private void NuevoAbono(ActionEvent event) {
        btnNuevoAbono.setDisable(true);
        rbtnEfectivo.setSelected(true);
        lblNoMonto.setVisible(false);
        tblArticulos.getItems().clear();
        ParteFactura(false);
        tblFacturas.getSelectionModel().select(null);
        txtMontoAbono.setText("");
        if(txtBusqueda.getText().equals("")){
            CargarFacturas("Ninguna");
        }else{
            CargarFacturas(txtBusqueda.getText());
        }
        btnImprimir.setVisible(false);
        btnNuevoAbono.setVisible(false);
        btnRealizarAbono.setDisable(false);
        btnNuevoAbono.setDisable(false);
    }
}