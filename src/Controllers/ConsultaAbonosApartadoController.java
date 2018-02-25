
package Controllers;

import Models.AbonoApartado;
import Models.DatosAbonoApartado;
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

public class ConsultaAbonosApartadoController implements Initializable {

    DatosAbonoApartado datosAbono = new DatosAbonoApartado();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<AbonoApartado> tblAbonos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasAbono(tblAbonos);
        CargarAbonos("Ninguna");
    }    

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

    private void MouseDireccion(MouseEvent event) {
        tblAbonos.setCursor(Cursor.CROSSHAIR);
    }
    
    private void CargarColumnasAbono(TableView<AbonoApartado> table) {
        TableColumn tblCIdAbono = new TableColumn("IdAbono");
        tblCIdAbono.setCellValueFactory(new PropertyValueFactory<AbonoApartado, String>("idAbono"));
        tblCIdAbono.setMinWidth(163);
        TableColumn tblCCodigoFactura = new TableColumn("Codigo Factura");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<AbonoApartado, String>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(163);
        TableColumn tblCMonto = new TableColumn("Monto");
        tblCMonto.setCellValueFactory(new PropertyValueFactory<AbonoApartado, String>("Monto"));
        tblCMonto.setMinWidth(163);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<AbonoApartado, String>("Fecha"));
        tblCFecha.setMinWidth(163);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<AbonoApartado, String>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(163);
        table.getColumns().addAll(tblCIdAbono, tblCCodigoFactura, tblCMonto, tblCFecha, tblCIdTipoDePago);
    }
    
    private void CargarAbonos(String busqueda){
        tblAbonos.getItems().clear();
        tblAbonos.setItems(datosAbono.Abonos(busqueda));
    }
}
