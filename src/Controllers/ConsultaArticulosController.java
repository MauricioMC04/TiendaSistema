
package Controllers;

import Models.Articulo;
import DataBase.DatosArticulos;
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

public class ConsultaArticulosController implements Initializable {

    DatosArticulos datosArticulos = new DatosArticulos();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Articulo> tblArticulos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasArticulos();
        CargarArticulos("Ninguna");
        tblArticulos.setCursor(Cursor.CROSSHAIR);
    }    

    @FXML
    private void BusquedaArticulo(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarArticulos("Ninguna");
        }else{
            CargarArticulos(txtBusqueda.getText());
        }
    }
    
    private void CargarColumnasArticulos() {
        TableColumn tblCCodigoArticulo = new TableColumn("Codigo");
        tblCCodigoArticulo.setCellValueFactory(new PropertyValueFactory<>("CodigoArticulo"));
        tblCCodigoArticulo.setMinWidth(272);
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tblCNombre.setMinWidth(272);
        TableColumn tblCPrecio = new TableColumn("Precio");
        tblCPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        tblCPrecio.setMinWidth(272);
        tblArticulos.getColumns().addAll(tblCCodigoArticulo, tblCNombre, tblCPrecio);  
    }
    
    private void CargarArticulos(String busqueda){
        tblArticulos.getItems().clear();
        tblArticulos.setItems(datosArticulos.Articulos(busqueda));
    }
}
