
package Controllers;

import Models.Abono;
import Models.Cliente;
import Models.DatosAbono;
import Models.DatosFactura;
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

public class AbonoController implements Initializable {

    DatosFactura datosFactura = new DatosFactura();
    DatosAbono datosAbono = new DatosAbono();
    
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ParteFactura(false);
        CargarColumnasDetalle(tblArticulos);
        CargarColumnasFactura(tblFacturas);
        CargarFacturas("Ninguna");
        lblNoMonto.setVisible(false);
    }    

    @FXML
    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
        txtMontoAbono.setCursor(Cursor.TEXT);
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
    private void MouseDireccion(MouseEvent event) {
        tblArticulos.setCursor(Cursor.CROSSHAIR);
        tblFacturas.setCursor(Cursor.CROSSHAIR);
    }

    @FXML
    private void MouseMano(MouseEvent event) {
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnRealizarAbono.setCursor(Cursor.HAND);
    }

    @FXML
    private void RealizarAbono(ActionEvent event) {
        btnRealizarAbono.setDisable(true);
        Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
        if(factura != null){
            if(txtMontoAbono.getText().equals("")){
                lblNoMonto.setVisible(true);
            }else{
                try {
                    double monto = Double.parseDouble(txtMontoAbono.getText());
                    if(monto <= Double.parseDouble(lblFaltante.getText())){
                        if(Abono(factura.getCodigoFactura(), monto)){
                            if(monto == Double.parseDouble(lblFaltante.getText())){                                
                                datosFactura.ImprimirFactura(factura.getCodigoFactura());
                            }
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
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al realizar el Abono");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Monto del Abono Invalido\nEl monto faltante es de " + 
                            lblFaltante.getText() + "\nRedigite el Monto del Abono");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Monto de Abono Invalido\nNo digite letras en el espacio del monto "
                        + "del Abono\nRedigite el Monto del Abono");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione la factura sobre la que desea hacer el Abono");
        }
        btnRealizarAbono.setDisable(false);
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
    
     private void CargarColumnasFactura(TableView<Factura> table) {
        TableColumn tblCCodigoFactura = new TableColumn("Codigo");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(81);
        TableColumn tblCMontoTotal = new TableColumn("Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<Factura, String>("MontoTotal"));
        tblCMontoTotal.setMinWidth(81);
        TableColumn tblCMontoPagado = new TableColumn("Pagado");
        tblCMontoPagado.setCellValueFactory(new PropertyValueFactory<Factura, String>("MontoPagado"));
        tblCMontoPagado.setMinWidth(81);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("Fecha"));
        tblCFecha.setMinWidth(81);
        TableColumn tblCIdCliente = new TableColumn("Cliente");
        tblCIdCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("idCliente"));
        tblCIdCliente.setMinWidth(81);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<Factura, String>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(81);
        table.getColumns().addAll(tblCCodigoFactura, tblCMontoTotal, tblCMontoPagado, tblCFecha, tblCIdCliente, tblCIdTipoDePago);
    }
    
    private void CargarColumnasDetalle(TableView<DetalleFactura> table) {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Nombre"));
        tblCNombreArticulo.setMinWidth(143);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Precio"));
        tblCPrecioArticulo.setMinWidth(143);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Descuento"));
        tblCDescuento.setMinWidth(143);
        table.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
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
}
