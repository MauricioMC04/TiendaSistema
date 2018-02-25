
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

public class ApartadoController implements Initializable {

    DatosArticulos datosArticulos = new DatosArticulos();
    ObservableList<DetalleFactura> listaApartado = FXCollections.observableArrayList();
    DatosFactura datosApartado = new DatosFactura();
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
    private TableView<DetalleFactura> tblApartado;
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
    private TextField txtNombrePersona;
    @FXML
    private TextField txtNumeroPersona;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblNoNombre;
    @FXML
    private Label lblNoNumero;
    @FXML
    private TextField txtPagoInicial;
    @FXML
    private Label lblArticuloSeleccionado;
    @FXML
    private Label lblPrecioSeleccionado;
    @FXML
    private Button btnNuevoVenta;
    @FXML
    private Button btnImprimir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarColumnasArticulos(tblArticulos);
        CargarColumnasVenta(tblApartado);
        CargarArticulos("Ninguna");
        ParteArticulo(false);
        lblNoNombre.setVisible(false);
        lblNoNumero.setVisible(false);
        CalcularPrecio();
        btnImprimir.setVisible(false);
        btnNuevoVenta.setVisible(false);
    }    


    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
        txtDescuento.setCursor(Cursor.TEXT);
        txtNombrePersona.setCursor(Cursor.TEXT);
        txtNumeroPersona.setCursor(Cursor.TEXT);
        txtPagoInicial.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BuscarArticulos(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarArticulos("Ninguna");
        }else{
            CargarArticulos(txtBusqueda.getText());
        }
        ParteArticulo(false);
    }

    private void MouseDireccion(MouseEvent event) {
        tblApartado.setCursor(Cursor.CROSSHAIR);
        tblArticulos.setCursor(Cursor.CROSSHAIR);
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
                    if(AgregarEnApartado(articulo, desc)){
                        ParteArticulo(false);
                        tblArticulos.getSelectionModel().select(null);
                        txtDescuento.setText("");
                    }
                }else if(rbtnPorcentaje.isSelected()){
                    desc = articulo.getPrecio() * (desc / 100);
                    if(AgregarEnApartado(articulo, desc)){
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
        rbtnColones.setCursor(Cursor.HAND);
        rbtnPorcentaje.setCursor(Cursor.HAND);
        btnAgregar.setCursor(Cursor.HAND);
        btnDescartar.setCursor(Cursor.HAND);
        rbtnEfectivo.setCursor(Cursor.HAND);
        rbtnTarjeta.setCursor(Cursor.HAND);
        btnRealizarVenta.setCursor(Cursor.HAND);
        btnNuevoVenta.setCursor(Cursor.HAND);
        btnImprimir.setCursor(Cursor.HAND);
    }

    @FXML
    private void DescartarArticulo(ActionEvent event) {
        btnDescartar.setDisable(true);
         btnDescartar.setDisable(true);
        if(listaApartado.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se puede descartar el articulo \n Aun no se ha agregado ningun"
                    + " articulo");
        }else{
            DetalleFactura detalle = tblApartado.getSelectionModel().getSelectedItem();
            if(detalle == null){
                JOptionPane.showMessageDialog(null, "No se puede descartar el articulo \n Seleccione el articulo a des"
                        + "cartar");
            }else{
                listaApartado.remove(detalle);
                CargarApartado();
                CalcularPrecio();
            }
        }
        btnDescartar.setDisable(false);
    }

    @FXML
    private void RealizarVenta(ActionEvent event) {
        btnRealizarVenta.setDisable(true);
        if(listaApartado.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se puede realizar la venta \nNo se han agregado Articulos");
            btnRealizarVenta.setDisable(false);
        }else{
            if(txtNombrePersona.getText().equals("")){
                lblNoNombre.setVisible(true);
                btnRealizarVenta.setDisable(false);
            }else{
                if(txtNumeroPersona.getText().equals("")){
                    lblNoNumero.setVisible(true);
                    btnRealizarVenta.setDisable(false);
                }else{
                    if(txtPagoInicial.getText().equals("")){
                        Object[] options = { "Realizar", "Cancelar" };
                        int choice = JOptionPane.showOptionDialog(null,
                            "Se asumira que el Pago Inicial es 0\nDesea realizar el Apartado?", 
                            "Apartado Confirmacion", 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            options, 
                            options[0]);
                        if (choice == JOptionPane.YES_OPTION)
                        {
                            Apartado();
                        }else{
                            btnRealizarVenta.setDisable(false);
                        }
                    }else{
                        Apartado();
                    }
                }
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
    
    private void CargarArticulos(String busqueda){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosArticulos.Articulos(busqueda));
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
    
    private void CargarColumnasVenta(TableView<DetalleFactura> table) {
        TableColumn tblCNombreArticulo = new TableColumn("Nombre Articulo");
        tblCNombreArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Nombre"));
        tblCNombreArticulo.setMinWidth(123);
        TableColumn tblCPrecioArticulo = new TableColumn("Precio Articulo");
        tblCPrecioArticulo.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Precio"));
        tblCPrecioArticulo.setMinWidth(123);
        TableColumn tblCDescuento = new TableColumn("Descuento");
        tblCDescuento.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Descuento"));
        tblCDescuento.setMinWidth(123);
        table.getColumns().addAll(tblCNombreArticulo, tblCPrecioArticulo, tblCDescuento);
    }
    
    private void CalcularPrecio(){
        double precio = 0;
        for(int i = 0; i < listaApartado.size(); i++){
            precio += listaApartado.get(i).getPrecio() - listaApartado.get(i).getDescuento();
        }
        lblTotal.setText(String.valueOf(precio));
    }
    
    private boolean AgregarEnApartado(Articulo articulo, double descuento){
        if(descuento > articulo.getPrecio()){
            JOptionPane.showMessageDialog(null, "Descuento invalido \n El descuento para este articulo no puede ser mayor"
               + " a " + articulo.getPrecio() + "\n Recalcule su descuento");
            return false;
        }else{
            listaApartado.add(new DetalleFactura(0,descuento,articulo.getCodigoArticulo(),articulo.getNombre(),
                articulo.getPrecio()));
            CargarApartado();
            CalcularPrecio();
            return true;
        }
    }
    
    private void CargarApartado(){
        tblApartado.setItems(listaApartado);
    }
    
    private void Apartado(){
        Calendar c = Calendar.getInstance();
        Date fecha = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        int tipoDePago = 1;
        if(rbtnTarjeta.isSelected()){
            tipoDePago = 2;
        }
        double pagoInicial = PagoInicial();
        if(pagoInicial != -1){
            if(datosApartado.RealizarApartado(new Factura(0,2,Double.parseDouble(lblTotal.getText()),
                pagoInicial,fecha,0,tipoDePago), listaApartado, txtNombrePersona.getText(),txtNumeroPersona.getText())){
                btnImprimir.setVisible(true);
                btnNuevoVenta.setVisible(true);
                btnAgregar.setDisable(true);
                btnDescartar.setDisable(true);
                btnRealizarVenta.setDisable(true);
            }else{
                JOptionPane.showMessageDialog(null, "Error al realizar el Apartado");
                btnRealizarVenta.setDisable(false);
            }
        }else{
            btnRealizarVenta.setDisable(false);
        }
    }
    
    private double PagoInicial(){
        double pago = 0;
        try {
            if(txtPagoInicial.getText().equals("")){
                return pago;
            }else{
                pago = Double.parseDouble(txtPagoInicial.getText());
                return pago;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pago Inicial invalido\nNo digite letras en el espacio del Pago Inicial"
                + "\nRedigite el Pago Inicial");
        }
        return -1;
    }

    @FXML
    private void NuevoVenta(ActionEvent event) {
        btnNuevoVenta.setDisable(true);
        listaApartado = FXCollections.observableArrayList();
        factura = null;       
        CalcularPrecio();
        ParteArticulo(false);
        CargarApartado();
        tblArticulos.getSelectionModel().select(null);
        rbtnColones.setSelected(true);
        rbtnEfectivo.setSelected(true);
        txtNombrePersona.setText("");
        txtNumeroPersona.setText("");
        txtPagoInicial.setText("");
        lblNoNombre.setVisible(false);
        lblNoNumero.setVisible(false);
        btnAgregar.setDisable(false);
        btnDescartar.setDisable(false);
        btnImprimir.setVisible(false);
        btnNuevoVenta.setVisible(false);
        btnRealizarVenta.setDisable(false);
        btnNuevoVenta.setDisable(false);
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        btnImprimir.setDisable(true);
        datosApartado.Imprimir(factura, listaApartado);
        btnImprimir.setDisable(false);
    }
}
