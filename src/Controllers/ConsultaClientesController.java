
package Controllers;

import Models.Cliente;
import Models.DatosClientes;
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

public class ConsultaClientesController implements Initializable {

    DatosClientes datosClientes = new DatosClientes();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Cliente> tblClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasClientes(tblClientes);
        CargarClientes("Ninguna");
    }    

    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BusquedaCliente(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarClientes("Ninguna");
        }else{
            CargarClientes(txtBusqueda.getText());
        }
    }

    private void MouseDireccion(MouseEvent event) {
        tblClientes.setCursor(Cursor.CROSSHAIR);
    }
    
    private void CargarColumnasClientes(TableView<Cliente> table) {
        TableColumn tblCIdCliente = new TableColumn("IdCliente");
        tblCIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("idCliente"));
        tblCIdCliente.setMinWidth(204);
        TableColumn tblCNombre = new TableColumn("Nombre");
        tblCNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Nombre"));
        tblCNombre.setMinWidth(204);
        TableColumn tblCNumero = new TableColumn("Numero");
        tblCNumero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Numero"));
        tblCNumero.setMinWidth(204);
        TableColumn tblCCantidadApartados = new TableColumn("Cantidad Apartados");
        tblCCantidadApartados.setCellValueFactory(new PropertyValueFactory<Cliente, String>("CantidadApartados"));
        tblCCantidadApartados.setMinWidth(204);
        table.getColumns().addAll(tblCIdCliente, tblCNombre, tblCNumero, tblCCantidadApartados);
    }
    
    private void CargarClientes(String busqueda){
        tblClientes.getItems().clear();
        tblClientes.setItems(datosClientes.Clientes(busqueda));
    }
}
