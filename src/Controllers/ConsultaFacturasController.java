
package Controllers;

import DataBase.DatosFactura;
import Models.DetalleFactura;
import Models.Factura;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class ConsultaFacturasController implements Initializable {

    DatosFactura datosFactura = new DatosFactura();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Factura> tblFacturas;
    @FXML
    private Label lblTipoPago;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private ToggleGroup rbtngTipoPago;
    @FXML
    private RadioButton rbtnTarjeta;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<DetalleFactura> tblDetalleFactura;
    @FXML
    private Button btnQuitarArticulo;
    @FXML
    private Button btnImprimir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ParteFactura(false);
        CargarColumnasDetalle();
        CargarColumnasFactura();
        CargarFacturas("Ninguna");
        btnQuitarArticulo.setVisible(false);
        tblFacturas.setCursor(Cursor.CROSSHAIR);
        tblDetalleFactura.setCursor(Cursor.CROSSHAIR);
        btnQuitarArticulo.setCursor(Cursor.HAND);
        btnEditar.setCursor(Cursor.HAND);
        btnEliminar.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
    }    

    @FXML
    private void BusquedaFactura(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarFacturas("Ninguna");
        }else{
            CargarFacturas(txtBusqueda.getText());
        }
    }

    @FXML
    private void Editar(ActionEvent event) {
        btnEditar.setDisable(true);
        Object[] options = { "Editar", "Cancelar" };
        int choice = JOptionPane.showOptionDialog(null,
            "Desea Editar esta Factura", 
            "Editar Factura Confirmacion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        if (choice == JOptionPane.YES_OPTION){
            Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
            if(factura != null){
                int tipoPago = 1;
                if(rbtnTarjeta.isSelected()){
                    tipoPago = 2;
                }
                if(datosFactura.EditarFactura(tipoPago, factura)){
                    if(txtBusqueda.getText().equals("")){
                        CargarFacturas("Ninguna");
                    }else{
                        CargarFacturas(txtBusqueda.getText());
                    }
                    btnQuitarArticulo.setVisible(false);
                    ParteFactura(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Editar la Factura");
                }
            }
        }
        btnEditar.setDisable(false);
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        btnEliminar.setDisable(true);
        Object[] options = { "Eliminar", "Cancelar" };
        int choice = JOptionPane.showOptionDialog(null,
            "Desea Eliminar esta Factura\nEsta accion NO podra revertirse", 
            "Elimar Factura Confirmacion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        if (choice == JOptionPane.YES_OPTION){
            Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
            if(factura != null){
                if(datosFactura.EliminarFactura(factura)){
                    if(txtBusqueda.getText().equals("")){
                        CargarFacturas("Ninguna");
                    }else{
                        CargarFacturas(txtBusqueda.getText());
                    }
                    btnQuitarArticulo.setVisible(false);
                    ParteFactura(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar la Factura");
                }
            }
        }
        btnEliminar.setDisable(false);
    }

    @FXML
    private void MostrarFactura(MouseEvent event) {
        Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
        if(factura != null){
            ParteFactura(true);
            CargarDatosFactura(factura);
            btnQuitarArticulo.setVisible(false);
        }else{
            ParteFactura(false);
            btnQuitarArticulo.setVisible(false);
        }
    }

    @FXML
    private void MostrarQuitarArticulo(MouseEvent event) {
        DetalleFactura detalle = tblDetalleFactura.getSelectionModel().getSelectedItem();
        if(detalle != null){
            btnQuitarArticulo.setVisible(true);
        }else{
            btnQuitarArticulo.setVisible(false);
        }
    }

    @FXML
    private void QuitarArticulo(ActionEvent event) {
        btnQuitarArticulo.setDisable(true);
        DetalleFactura detalle = tblDetalleFactura.getSelectionModel().getSelectedItem();
        if(detalle != null){
            Object[] options = { "Quitar", "Cancelar" };
            int choice = JOptionPane.showOptionDialog(null,
                "Desea eliminar este articulo de la factura\nEsta accion NO podra revertirse", 
                "Eliminar Articulo Confirmacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);
            if (choice == JOptionPane.YES_OPTION){
                if(datosFactura.EliminarDetalleFactura(detalle)){
                    if(txtBusqueda.getText().equals("")){
                        CargarFacturas("Ninguna");
                    }else{
                        CargarFacturas(txtBusqueda.getText());
                    }
                    btnQuitarArticulo.setVisible(false);
                    ParteFactura(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar Detalle de Factura");
                }
            }
        }
        btnQuitarArticulo.setDisable(false);
    }
    
    private void ParteFactura(boolean bandera){
        tblDetalleFactura.setVisible(bandera);
        lblTipoPago.setVisible(bandera);
        rbtnEfectivo.setVisible(bandera);
        rbtnTarjeta.setVisible(bandera);
        btnEditar.setVisible(bandera);
        btnEliminar.setVisible(bandera);
        btnImprimir.setVisible(bandera);
    }
    
    private void CargarColumnasFactura() {
        TableColumn tblCCodigoFactura = new TableColumn("Codigo");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(101);
        TableColumn tblCMontoTotal = new TableColumn("Monto Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<>("MontoTotal"));
        tblCMontoTotal.setMinWidth(101);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tblCFecha.setMinWidth(101);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(98);
        tblFacturas.getColumns().addAll(tblCCodigoFactura, tblCMontoTotal, tblCFecha, tblCIdTipoDePago);
    }
    
    private void CargarColumnasDetalle() {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tblCNombreArticulo.setMinWidth(101);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        tblCPrecioArticulo.setMinWidth(101);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<>("Descuento"));
        tblCDescuento.setMinWidth(101);
        tblDetalleFactura.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
    }
    
    private void CargarFacturas(String busqueda){
        tblFacturas.getItems().clear();
        tblFacturas.setItems(datosFactura.TodasFacturas(busqueda));
        tblFacturas.getSelectionModel().select(null);
        ParteFactura(false);
    }
    
    private void CargarDatosFactura(Factura factura){
        tblDetalleFactura.getItems().clear();
        tblDetalleFactura.setItems(datosFactura.DetalleFactura(factura.getCodigoFactura()));
        if(factura.getIdTipoDePago() == 1){
            rbtnEfectivo.setSelected(true);
        }else{
            rbtnTarjeta.setSelected(true);
        }
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        Factura factura = tblFacturas.getSelectionModel().getSelectedItem();
        if(factura != null){
            datosFactura.ImprimirFactura(factura.getCodigoFactura());
        }
        btnImprimir.setDisable(false);
    }
}
