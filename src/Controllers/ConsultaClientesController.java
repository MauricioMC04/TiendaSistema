
package Controllers;

import Models.Cliente;
import DataBase.DatosClientes;
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

public class ConsultaClientesController implements Initializable {

    DatosClientes datosClientes = new DatosClientes();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Cliente> tblClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasClientes();
        CargarClientes("Ninguna");
        tblClientes.setCursor(Cursor.CROSSHAIR);
    }    

    @FXML
    private void BusquedaCliente(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarClientes("Ninguna");
        }else{
            CargarClientes(txtBusqueda.getText());
        }
    }
    
    private void CargarColumnasClientes() {
        TableColumn tblCIdCliente = new TableColumn("IdCliente");
        tblCIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tblCIdCliente.setMinWidth(204);
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tblCNombre.setMinWidth(204);
        TableColumn tblCNumero = new TableColumn("Numero");
        tblCNumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        tblCNumero.setMinWidth(204);
        TableColumn tblCCantidadApartados = new TableColumn("Cantidad Apartados");
        tblCCantidadApartados.setCellValueFactory(new PropertyValueFactory<>("CantidadApartados"));
        tblCCantidadApartados.setPrefWidth(202);
        tblClientes.getColumns().addAll(tblCIdCliente, tblCNombre, tblCNumero, tblCCantidadApartados);
    }
    
    private void CargarClientes(String busqueda){
        tblClientes.getItems().clear();
        tblClientes.setItems(datosClientes.Clientes(busqueda));
    }
}
