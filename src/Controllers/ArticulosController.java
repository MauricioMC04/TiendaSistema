
package Controllers;

import Models.Articulo;
import Models.DatosArticulos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class ArticulosController implements Initializable {

    DatosArticulos datosArticulos = new DatosArticulos();
    
    @FXML
    private Tab TabAgregar;
    @FXML
    private TableView<Articulo> tblAgregar;
    @FXML
    private TextArea txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblNoPrecio;
    @FXML
    private Label lblNoNombre;
    @FXML
    private Tab TabEliminar;
    @FXML
    private TextField txtBusquedaEliminar;
    @FXML
    private TableView<Articulo> tblEliminar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Tab TabEditar;
    @FXML
    private TextField txtBusquedaEditar;
    @FXML
    private Label lblNombreEditar;
    @FXML
    private Label lblPrecioEditar;
    @FXML
    private TextArea txtNombreEditar;
    @FXML
    private TextField txtPrecioEditar;
    @FXML
    private Label lblNoNombreEditar;
    @FXML
    private Label lblNoPrecioEditar;
    @FXML
    private Button btnEditar;
    @FXML
    private TableView<Articulo> tblEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasArticulos(tblAgregar, 1);
        CargarColumnasArticulos(tblEliminar, 2);
        CargarColumnasArticulos(tblEditar, 3);
        PorDefectoAgregar();
        PorDefectoEliminar();
        PorDefectoEditar();
    }    


    private void MouseDireccion(MouseEvent event) {
        tblAgregar.setCursor(Cursor.CROSSHAIR);
        tblEliminar.setCursor(Cursor.CROSSHAIR);
        tblEditar.setCursor(Cursor.CROSSHAIR);
    }

    private void MouseEscribir(MouseEvent event) {
        txtBusquedaEditar.setCursor(Cursor.TEXT);
        txtBusquedaEliminar.setCursor(Cursor.TEXT);
        txtNombre.setCursor(Cursor.TEXT);
        txtNombreEditar.setCursor(Cursor.TEXT);
        txtPrecio.setCursor(Cursor.TEXT);
        txtPrecioEditar.setCursor(Cursor.TEXT);
    }

    private void MouseMano(MouseEvent event) {
        btnAgregar.setCursor(Cursor.HAND);
        btnEditar.setCursor(Cursor.HAND);
        btnEliminar.setCursor(Cursor.HAND);
    }

    @FXML
    private void Agregar(ActionEvent event) {
        btnAgregar.setDisable(true);
        if(txtNombre.getText().equals("")){
            lblNoNombre.setVisible(true);
        }else{
            if(txtPrecio.getText().equals("")){
                lblNoPrecio.setVisible(true);
            }else{
                try {
                    Double precio = Double.parseDouble(txtPrecio.getText());
                    if(!datosArticulos.ExisteArticulo(txtNombre.getText(), precio)){
                        if(datosArticulos.AgregarArticulo(txtNombre.getText(), precio)){
                            PorDefectoAgregar();
                            PorDefectoEditar();
                            PorDefectoEliminar();
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al Agregar Articulo");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el Articulo\nYa existe un Articulo con"
                            + " las mismas caracteristicas");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Precio invalido\nNo digite letras en el espacio del precio\n"
                        + "Redigite el precio");
                }
            }
        }
        btnAgregar.setDisable(false);
    }

    @FXML
    private void BusquedaEliminar(KeyEvent event) {
        if(txtBusquedaEliminar.getText().equals("")){
            CargarArticulosEliminar("Ninguna");
        }else{
            CargarArticulosEliminar(txtBusquedaEliminar.getText());
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        btnEliminar.setDisable(true);
        Articulo articulo = tblEliminar.getSelectionModel().getSelectedItem();
        if(articulo != null){
            if(datosArticulos.EliminarArticulo(articulo.getCodigoArticulo())){
                PorDefectoAgregar();
                PorDefectoEditar();
                PorDefectoEliminar();
            }else{
                JOptionPane.showMessageDialog(null, "Error al Eliminar Articulo");
            }
        }
        btnEliminar.setDisable(false);
    }

    @FXML
    private void Editar(ActionEvent event) {
        btnEditar.setDisable(true);
        Articulo articulo = tblEditar.getSelectionModel().getSelectedItem();
        if(articulo != null){
            if(txtNombreEditar.getText().equals("")){
                lblNoNombreEditar.setVisible(true);
            }else{
                if(txtPrecioEditar.getText().equals("")){
                    lblNoPrecioEditar.setVisible(true);
                }else{
                    try {
                        Double precio = Double.parseDouble(txtPrecioEditar.getText());
                        Object[] options = { "Editar", "Cancelar" };
                        int choice = JOptionPane.showOptionDialog(null,
                            "Seguro que desea editar este articulo?\nSi este articulo esta incluido en alguna factura "
                                + "podria ocacionar un problema\nSi alguna factura que contiene este articulo es modifi"
                                + "cada y se produce un\nrecalculamiento de su total este sera afectado por el nuevo mo"
                                + "nto del articulo,\nlo que generaria un alteracion de la informacion original", 
                            "Editar Confirmacion", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        options, 
                        options[0]);
                        if (choice == JOptionPane.YES_OPTION){
                            if(datosArticulos.EditarArticulo(articulo.getCodigoArticulo(),txtNombreEditar.getText(), precio)){
                                PorDefectoAgregar();
                                PorDefectoEditar();
                                PorDefectoEliminar();
                            }else{
                                JOptionPane.showMessageDialog(null, "Error al Editar Articulo");
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Precio invalido\nNo digite letras en el espacio del precio\n"
                            + "Redigite el precio");
                    }
                }
            }
        }
        btnEditar.setDisable(false);
    }

    @FXML
    private void BusquedaEditar(KeyEvent event) {
        if(txtBusquedaEditar.getText().equals("")){
            CargarArticulosEditar("Ninguna");
        }else{
            CargarArticulosEditar(txtBusquedaEditar.getText());
        }
    }
        
    private void CargarColumnasArticulos(TableView<Articulo> table, int tabla) {
        TableColumn tblCCodigoArticulo = new TableColumn("Codigo");
        tblCCodigoArticulo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("CodigoArticulo")); 
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Nombre"));
        TableColumn tblCPrecio = new TableColumn("Precio");
        tblCPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Precio"));
        switch(tabla){
            case 1:
                tblCCodigoArticulo.setMinWidth(153);
                tblCNombre.setMinWidth(153);
                tblCPrecio.setMinWidth(153);
            break;
            case 2:
                tblCCodigoArticulo.setMinWidth(155);
                tblCNombre.setMinWidth(155);
                tblCPrecio.setMinWidth(155);
            break;
            default:
                tblCCodigoArticulo.setMinWidth(154);
                tblCNombre.setMinWidth(154);
                tblCPrecio.setMinWidth(154);
            break;
        }
        table.getColumns().addAll(tblCCodigoArticulo, tblCNombre, tblCPrecio);
    }
    
    private void PorDefectoAgregar(){
        CargarArticulosAgregar();
        lblNoNombre.setVisible(false);
        lblNoPrecio.setVisible(false);
        txtNombre.setText("");
        txtPrecio.setText("");
    }
    
    private void PorDefectoEliminar(){
        if(txtBusquedaEliminar.getText().equals("")){
            CargarArticulosEliminar("Ninguna");
        }else{
            CargarArticulosEliminar(txtBusquedaEliminar.getText());
        }
        btnEliminar.setVisible(false);
    }
    
    private void PorDefectoEditar(){
        if(txtBusquedaEditar.getText().equals("")){
            CargarArticulosEditar("Ninguna");
        }else{
            CargarArticulosEditar(txtBusquedaEditar.getText());
        }
        lblNoNombreEditar.setVisible(false);
        lblNoPrecioEditar.setVisible(false);
        txtNombreEditar.setText("");
        txtPrecioEditar.setText("");
        btnEditar.setVisible(false);
    }
    
    private void CargarArticulosAgregar(){
        tblAgregar.getItems().clear();
        tblAgregar.setItems(datosArticulos.Articulos("Ninguna"));
    }
    
    private void CargarArticulosEliminar(String busqueda){
        tblEliminar.getItems().clear();
        tblEliminar.setItems(datosArticulos.ArticulosEliminar(busqueda));
    }
    
    private void CargarArticulosEditar(String busqueda){
        tblEditar.getItems().clear();
        tblEditar.setItems(datosArticulos.Articulos(busqueda));
    }

    @FXML
    private void MostrarEliminar(MouseEvent event) {
        Articulo articulo = tblEliminar.getSelectionModel().getSelectedItem();
        if(articulo != null){
            btnEliminar.setVisible(true);
        }else{
            PorDefectoEliminar();
        }
    }

    @FXML
    private void MostrarArticulo(MouseEvent event) {
        Articulo articulo = tblEditar.getSelectionModel().getSelectedItem();
        if(articulo != null){
            btnEditar.setVisible(true);
            txtNombreEditar.setText(articulo.getNombre());
            txtPrecioEditar.setText(String.valueOf(articulo.getPrecio()));
            lblNoNombreEditar.setVisible(false);
            lblNoPrecioEditar.setVisible(false);
        }else{
            PorDefectoEditar();
        }
    }
    
}
