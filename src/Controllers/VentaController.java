
package Controllers;

import Models.Articulo;
import Models.DatosArticulos;
import Models.DatosFactura;
import Models.DetalleFactura;
import Models.Factura;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class VentaController implements Initializable {

    DatosArticulos datosArticulos = new DatosArticulos();
    ObservableList<DetalleFactura> listaVenta = FXCollections.observableArrayList();
    DatosFactura datosVenta = new DatosFactura();
    public static Factura factura;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Articulo> tblArticulos;
    @FXML
    private Label lblArticulo;
    @FXML
    private Label lblPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private RadioButton rbtnColones;
    @FXML
    private ToggleGroup rbtngDescuentos;
    @FXML
    private RadioButton rbtnPorcentaje;
    @FXML
    private TextField txtDescuento;
    @FXML
    private TableView<DetalleFactura> tblVenta;
    @FXML
    private Button btnDescartar;
    @FXML
    private RadioButton rbtnEfectivo;
    @FXML
    private ToggleGroup rbtngTipoPago;
    @FXML
    private RadioButton rbtnTarjeta;
    @FXML
    private Button btnRealizarVenta;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblArticuloSeleccionado;
    @FXML
    private Label lblPrecioSeleccionado;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnNuevaVenta;
    @FXML
    private TextField txtNombrePersona;
    @FXML
    private TextField txtNumeroPersona;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarArticulos("Ninguna");
        ParteArticulo(false);
        CalcularPrecio();
        CargarColumnasVenta(tblVenta);
        cargarColumnasArticulos(tblArticulos);
        btnImprimir.setVisible(false);
        btnNuevaVenta.setVisible(false);
    }    

    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
        txtDescuento.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BuscarArticulos(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarArticulos("Ninguna");
        }else{
            CargarArticulos(txtBusqueda.getText());
        }
    }

    private void MouseDireccion(MouseEvent event) {
        tblArticulos.setCursor(Cursor.CROSSHAIR);
        tblVenta.setCursor(Cursor.CROSSHAIR);
    }

    @FXML
    private void Agregar(ActionEvent event) {
        btnAgregar.setDisable(true);
        Articulo articulo = tblArticulos.getSelectionModel().getSelectedItem();
        if(articulo != null){
            String descuento = txtDescuento.getText();
            if(descuento.equals("")){
                descuento = "0";
            }
            try {
                double desc = Double.parseDouble(descuento);
                if(rbtnColones.isSelected()){
                    if(AgregarEnVenta(articulo, desc)){
                        ParteArticulo(false);
                        tblArticulos.getSelectionModel().select(null);
                        txtDescuento.setText("");
                    }
                }else if(rbtnPorcentaje.isSelected()){
                    desc = articulo.getPrecio() * (desc / 100);
                    if(AgregarEnVenta(articulo, desc)){
                        ParteArticulo(false);
                        tblArticulos.getSelectionModel().select(null);
                        txtDescuento.setText("");
                    }                    
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Descuento invalido \n No digite letras en el campo del Descuento \n "
                        + "Recalcule su descuento");
            }
        }
        btnAgregar.setDisable(false);
    }

    private void MouseMano(MouseEvent event) {
        btnAgregar.setCursor(Cursor.HAND);
        btnDescartar.setCursor(Cursor.HAND);
        btnRealizarVenta.setCursor(Cursor.HAND);
        rbtnColones.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnPorcentaje.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnNuevaVenta.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
    }

    @FXML
    private void DescartarArticulo(ActionEvent event) {
        btnDescartar.setDisable(true);
        if(listaVenta.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se puede descartar el articulo \n Aun no se ha agregado ningun"
                    + " articulo");
        }else{
            DetalleFactura detalle = tblVenta.getSelectionModel().getSelectedItem();
            if(detalle == null){
                JOptionPane.showMessageDialog(null, "No se puede descartar el articulo \n Seleccione el articulo a des"
                        + "cartar");
            }else{
                listaVenta.remove(detalle);
                CargarVenta();
                CalcularPrecio();
            }
        }
        btnDescartar.setDisable(false);
    }

    @FXML
    private void RealizarVenta(ActionEvent event) {
        btnRealizarVenta.setDisable(true);
        if(listaVenta.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se puede realizar la venta \nNo se han agregado Articulos");
             btnRealizarVenta.setDisable(false);
        }else{
            Calendar c = Calendar.getInstance();   
            Date fecha = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            int tipoDePago = 1;
            if(rbtnTarjeta.isSelected()){
                tipoDePago = 2;
            }
            int idCliente = 0;
            if(!txtNombrePersona.getText().equals("")){
                idCliente = datosVenta.ObtenerCliente(txtNombrePersona.getText(), txtNumeroPersona.getText());
            }
            if(datosVenta.RealizarVenta(new Factura(0,1,Double.parseDouble(lblTotal.getText()),
                Double.parseDouble(lblTotal.getText()),fecha,idCliente,tipoDePago), listaVenta)){
                btnRealizarVenta.setDisable(true);
                btnAgregar.setDisable(true);
                btnDescartar.setDisable(true);
                btnImprimir.setVisible(true);
                btnNuevaVenta.setVisible(true);
                rbtnColones.setDisable(true);
                rbtnPorcentaje.setDisable(true);
                rbtnEfectivo.setDisable(true);
                rbtnTarjeta.setDisable(true);
            }else{
                JOptionPane.showMessageDialog(null, "Error al realizar la venta");
                 btnRealizarVenta.setDisable(false);
            }            
        }
       
    }

    @FXML
    private void MostrarParteArticulo(MouseEvent event) {
        Articulo seleccionado = tblArticulos.getSelectionModel().getSelectedItem();
        if(seleccionado == null){
            ParteArticulo(false);
        }else{        
            ParteArticulo(true);
            txtDescuento.setText("");
            lblArticulo.setText(seleccionado.getCodigoArticulo() + " - " + seleccionado.getNombre());
            lblPrecio.setText(String.valueOf(seleccionado.getPrecio()));
        }
    }
    
    private void CargarArticulos(String busqueda){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosArticulos.Articulos(busqueda));
    }
    
    private void cargarColumnasArticulos(TableView<Articulo> table) {
        TableColumn tblCCodigoArticulo = new TableColumn("Codigo");
        tblCCodigoArticulo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("CodigoArticulo"));
        tblCCodigoArticulo.setMinWidth(121);
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Nombre"));
        tblCNombre.setMinWidth(121);
        TableColumn tblCPrecio = new TableColumn("Precio");
        tblCPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Precio"));
        tblCPrecio.setMinWidth(121);
        table.getColumns().addAll(tblCCodigoArticulo, tblCNombre, tblCPrecio);
    }
    
    private void ParteArticulo(boolean bandera){
        lblArticuloSeleccionado.setVisible(bandera);
        lblPrecioSeleccionado.setVisible(bandera);
        lblArticulo.setVisible(bandera);
        lblPrecio.setVisible(bandera);
        rbtnColones.setVisible(bandera);
        rbtnPorcentaje.setVisible(bandera);
        txtDescuento.setVisible(bandera);
        btnAgregar.setVisible(bandera);
    }
    
    private void CalcularPrecio(){
        double precio = 0;
        for(int i = 0; i < listaVenta.size(); i++){
            precio += listaVenta.get(i).getPrecio() - listaVenta.get(i).getDescuento();
        }
        lblTotal.setText(String.valueOf(precio));
    }
    
    private boolean AgregarEnVenta(Articulo articulo, double descuento){
        if(descuento > articulo.getPrecio()){
            JOptionPane.showMessageDialog(null, "Descuento invalido \n El descuento para este articulo no puede ser mayor"
               + " a " + articulo.getPrecio() + "\n Recalcule su descuento");
            return false;
        }else{
            listaVenta.add(new DetalleFactura(0,descuento,articulo.getCodigoArticulo(),articulo.getNombre(),
                articulo.getPrecio()));
            CargarVenta();
            CalcularPrecio();
            return true;
        }
    }
    
    private void CargarVenta(){
        tblVenta.setItems(listaVenta);
    }
    
    private void CargarColumnasVenta(TableView<DetalleFactura> table) {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Nombre"));
        tblCNombreArticulo.setMinWidth(121);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Precio"));
        tblCPrecioArticulo.setMinWidth(121);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Descuento"));
        tblCDescuento.setMinWidth(121);
        table.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        datosVenta.Imprimir(factura, listaVenta);
        btnImprimir.setDisable(false);
    }

    @FXML
    private void NuevaVenta(ActionEvent event) {
        btnNuevaVenta.setDisable(true);
        listaVenta = FXCollections.observableArrayList();
        factura = null;
        CalcularPrecio();
        ParteArticulo(false);
        CargarVenta();
        tblArticulos.getSelectionModel().select(null);
        rbtnColones.setSelected(true);
        rbtnEfectivo.setSelected(true);
        btnAgregar.setDisable(false);
        btnDescartar.setDisable(false);
        btnRealizarVenta.setDisable(false);
        btnNuevaVenta.setDisable(false);
        btnImprimir.setVisible(false);
        btnNuevaVenta.setVisible(false);
        rbtnColones.setDisable(false);
        rbtnPorcentaje.setDisable(false);
        rbtnEfectivo.setDisable(false);
        rbtnTarjeta.setDisable(false);
        txtNombrePersona.setText("");
        txtNumeroPersona.setText("");
    }
}
