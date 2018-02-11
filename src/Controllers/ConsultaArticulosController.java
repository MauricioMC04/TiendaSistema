
package Controllers;

import Models.Articulo;
import Models.DatosArticulos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ConsultaArticulosController implements Initializable {

    DatosArticulos datosArticulos = new DatosArticulos();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Articulo> tblArticulos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasArticulos(tblArticulos);
        CargarArticulos("Ninguna");
    }    

    @FXML
    private void MouseEscribir(MouseEvent event) {
         txtBusqueda.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BusquedaArticulo(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarArticulos("Ninguna");
        }else{
            CargarArticulos(txtBusqueda.getText());
        }
    }

    @FXML
    private void MouseDireccion(MouseEvent event) {
        tblArticulos.setCursor(Cursor.CROSSHAIR);
    }
    
    private void CargarColumnasArticulos(TableView<Articulo> table) {
        TableColumn tblCCodigoArticulo = new TableColumn("Codigo");
        tblCCodigoArticulo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("CodigoArticulo"));
        tblCCodigoArticulo.setMinWidth(272);
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Nombre"));
        tblCNombre.setMinWidth(272);
        TableColumn tblCPrecio = new TableColumn("Precio");
        tblCPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("Precio"));
        tblCPrecio.setMinWidth(272);
        table.getColumns().addAll(tblCCodigoArticulo, tblCNombre, tblCPrecio);
    }
    
    private void CargarArticulos(String busqueda){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosArticulos.Articulos(busqueda));
    }
}
