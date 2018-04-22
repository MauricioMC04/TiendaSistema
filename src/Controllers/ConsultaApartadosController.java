
package Controllers;

import Models.Cliente;
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

public class ConsultaApartadosController implements Initializable {

    DatosFactura datosFactura = new DatosFactura();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Factura> tblApartados;
    @FXML
    private TableView<DetalleFactura> tblArticulos;
    @FXML
    private Button btnQuitarArticulo;
    @FXML
    private Label lblTipoPago;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private ToggleGroup rbtngTipoPago;
    @FXML
    private RadioButton rbtnTarjeta;
    @FXML
    private Label lblPersona;
    @FXML
    private Label lblNombre;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNumero;
    @FXML
    private Label lblNoNombre;
    @FXML
    private Label lblNoNumero;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblNumero;
    @FXML
    private Button btnImprimir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnQuitarArticulo.setVisible(false);
        lblNoNombre.setVisible(false);
        lblNoNumero.setVisible(false);
        ParteApartado(false);
        CargarColumnasFactura();
        CargarColumnasDetalle();
        CargarApartados("Ninguna");
        tblApartados.setCursor(Cursor.CROSSHAIR);
        tblArticulos.setCursor(Cursor.CROSSHAIR);
        btnEditar.setCursor(Cursor.HAND);
        btnEliminar.setCursor(Cursor.HAND);
        btnQuitarArticulo.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
    }    

    @FXML
    private void BusquedaApartado(KeyEvent event) {
         if(txtBusqueda.getText().equals("")){
            CargarApartados("Ninguna");
        }else{
            CargarApartados(txtBusqueda.getText());
        }
    }

    @FXML
    private void QuitarArticulo(ActionEvent event) {
        btnQuitarArticulo.setDisable(true);
        DetalleFactura detalle = tblArticulos.getSelectionModel().getSelectedItem();
        if(detalle != null){
            Object[] options = { "Quitar", "Cancelar" };
            int choice = JOptionPane.showOptionDialog(null,
                "Desea eliminar este articulo del apartado\nEsta accion NO podra revertirse", 
                "Eliminar Articulo Confirmacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);
            if (choice == JOptionPane.YES_OPTION){
                if(datosFactura.EliminarDetalleFactura(detalle)){
                    if(txtBusqueda.getText().equals("")){
                        CargarApartados("Ninguna");
                    }else{
                        CargarApartados(txtBusqueda.getText());
                    }
                    btnQuitarArticulo.setVisible(false);
                    ParteApartado(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar Detalle del Apartado");
                }
            }
        }
        btnQuitarArticulo.setDisable(false);
    }

    @FXML
    private void Editar(ActionEvent event) {
        btnEditar.setDisable(true);
        Object[] options = { "Editar", "Cancelar" };
        int choice = JOptionPane.showOptionDialog(null,
            "Desea Editar este Apartado", 
            "Editar Factura Confirmacion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        if (choice == JOptionPane.YES_OPTION){
            Factura apartado = tblApartados.getSelectionModel().getSelectedItem();
            if(apartado != null){
                int tipoPago = 1;
                if(rbtnTarjeta.isSelected()){
                    tipoPago = 2;
                }
                if(txtNombre.getText().equals("")){
                    lblNoNombre.setVisible(true);
                }else{
                    if(txtNumero.getText().equals("")){
                        lblNoNumero.setVisible(true);
                    }else{
                        if(datosFactura.EditarApartado(txtNombre.getText(), txtNumero.getText(), tipoPago, apartado)){
                            if(txtBusqueda.getText().equals("")){
                                CargarApartados("Ninguna");
                            }else{
                                CargarApartados(txtBusqueda.getText());
                            }
                            btnQuitarArticulo.setVisible(false);
                            ParteApartado(false);
                            lblNoNombre.setVisible(false);
                            lblNoNumero.setVisible(false);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al Editar el Apartado");
                        }
                    }
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
            "Desea Eliminar este Apartado\nEsta accion NO podra revertirse", 
            "Elimar Factura Confirmacion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        if (choice == JOptionPane.YES_OPTION){
            Factura apartado = tblApartados.getSelectionModel().getSelectedItem();
            if(apartado != null){
                if(datosFactura.EliminarFactura(apartado)){
                    if(txtBusqueda.getText().equals("")){
                        CargarApartados("Ninguna");
                    }else{
                        CargarApartados(txtBusqueda.getText());
                    }
                    btnQuitarArticulo.setVisible(false);
                    ParteApartado(false);
                    lblNoNombre.setVisible(false);
                    lblNoNumero.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar el Apartado");
                }
            }
        }
        btnEliminar.setDisable(false);
    }

    @FXML
    private void MostrarApartado(MouseEvent event) {
        Factura apartado = tblApartados.getSelectionModel().getSelectedItem();
        if(apartado != null){
            ParteApartado(true);
            CargarDatosApartado(apartado);
            btnQuitarArticulo.setVisible(false);
        }else{
            ParteApartado(false);
            btnQuitarArticulo.setVisible(false);
        }
    }

    @FXML
    private void MostrarQuitarArticulo(MouseEvent event) {
        DetalleFactura detalle = tblArticulos.getSelectionModel().getSelectedItem();
        if(detalle != null){
            btnQuitarArticulo.setVisible(true);
        }else{
            btnQuitarArticulo.setVisible(false);
        }
    }
    
    private void ParteApartado(boolean bandera){
        tblArticulos.setVisible(bandera);
        btnEditar.setVisible(bandera);
        btnEliminar.setVisible(bandera);
        lblPersona.setVisible(bandera);
        lblNombre.setVisible(bandera);
        lblNumero.setVisible(bandera);
        txtNombre.setVisible(bandera);
        txtNumero.setVisible(bandera);
        lblTipoPago.setVisible(bandera);
        rbtnEfectivo.setVisible(bandera);
        rbtnTarjeta.setVisible(bandera);
        btnImprimir.setVisible(bandera);
    }
    
    private void CargarColumnasFactura() {
        TableColumn tblCCodigoFactura = new TableColumn("Codigo");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(10);
        tblCCodigoFactura.setPrefWidth(71);
        TableColumn tblCMontoTotal = new TableColumn("Total");
        tblCMontoTotal.setCellValueFactory(new PropertyValueFactory<>("MontoTotal"));
        tblCMontoTotal.setMinWidth(10);
        tblCMontoTotal.setPrefWidth(71);
        TableColumn tblCMontoPagado = new TableColumn("Pagado");
        tblCMontoPagado.setCellValueFactory(new PropertyValueFactory<>("MontoPagado"));
        tblCMontoPagado.setMinWidth(10);
        tblCMontoPagado.setPrefWidth(71);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tblCFecha.setMinWidth(10);
        tblCFecha.setPrefWidth(71);
        TableColumn tblCIdCliente = new TableColumn("Cliente");
        tblCIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tblCIdCliente.setMinWidth(10);
        tblCIdCliente.setPrefWidth(71);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(10);
        tblCIdTipoDePago.setPrefWidth(69);
        tblApartados.getColumns().addAll(tblCCodigoFactura, tblCMontoTotal, tblCMontoPagado, tblCFecha, tblCIdCliente, tblCIdTipoDePago);
    }
    
    private void CargarColumnasDetalle() {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tblCNombreArticulo.setMinWidth(118);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        tblCPrecioArticulo.setMinWidth(96);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<>("Descuento"));
        tblCDescuento.setMinWidth(80);
        tblArticulos.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
    }
    
    private void CargarApartados(String busqueda){
        tblApartados.getItems().clear();
        tblApartados.setItems(datosFactura.TodosApartados(busqueda));
        tblApartados.getSelectionModel().select(null);
        ParteApartado(false);
    }
    
    private void CargarDatosApartado(Factura apartado){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosFactura.DetalleFactura(apartado.getCodigoFactura()));
        if(apartado.getIdTipoDePago() == 1){
            rbtnEfectivo.setSelected(true);
        }else{
            rbtnTarjeta.setSelected(true);
        }
        Cliente cliente = datosFactura.Cliente(apartado.getIdCliente());
        txtNombre.setText(cliente.getNombre());
        txtNumero.setText(cliente.getNumero());
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        Factura factura = tblApartados.getSelectionModel().getSelectedItem();
        if(factura != null){
            datosFactura.ImprimirFactura(factura.getCodigoFactura());
        }
        btnImprimir.setDisable(false);
    }
}
