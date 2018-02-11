
package Controllers;

import Models.Abono;
import Models.DatosAbono;
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

public class ConsultaAbonosController implements Initializable {

    DatosAbono datosAbono = new DatosAbono();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Abono> tblAbonos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasAbono(tblAbonos);
        CargarAbonos("Ninguna");
    }    

    @FXML
    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BuscarAbono(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarAbonos("Ninguna");
        }else{
            CargarAbonos(txtBusqueda.getText());
        }
    }

    @FXML
    private void MouseDireccion(MouseEvent event) {
        tblAbonos.setCursor(Cursor.CROSSHAIR);
    }
    
    private void CargarColumnasAbono(TableView<Abono> table) {
        TableColumn tblCIdAbono = new TableColumn("IdAbono");
        tblCIdAbono.setCellValueFactory(new PropertyValueFactory<Abono, String>("idAbono"));
        tblCIdAbono.setMinWidth(163);
        TableColumn tblCCodigoFactura = new TableColumn("Codigo Factura");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<Abono, String>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(163);
        TableColumn tblCMonto = new TableColumn("Monto");
        tblCMonto.setCellValueFactory(new PropertyValueFactory<Abono, String>("Monto"));
        tblCMonto.setMinWidth(163);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<Abono, String>("Fecha"));
        tblCFecha.setMinWidth(163);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<Abono, String>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(163);
        table.getColumns().addAll(tblCIdAbono, tblCCodigoFactura, tblCMonto, tblCFecha, tblCIdTipoDePago);
    }
    
    private void CargarAbonos(String busqueda){
        tblAbonos.getItems().clear();
        tblAbonos.setItems(datosAbono.Abonos(busqueda));
    }
}
