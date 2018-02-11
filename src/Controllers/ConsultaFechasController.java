
package Controllers;

import Models.DatosFechas;
import Models.Fecha;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ConsultaFechasController implements Initializable {

    DatosFechas datosFechas = new DatosFechas();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Label lblTotalIngresos;
    @FXML
    private Label lblIngresos;
    @FXML
    private Label lblCantidadVentas;
    @FXML
    private Label lblVentas;
    @FXML
    private Label lblTotalPorVentas;
    @FXML
    private Label lblTotalVentas;
    @FXML
    private Label lblTotalVentasEfectivo;
    @FXML
    private Label lblTotalVEfectivo;
    @FXML
    private Label lblTotalVentasTarjeta;
    @FXML
    private Label lblTotalVTarjeta;
    @FXML
    private Label lblTotalPorApartados;
    @FXML
    private Label lblTotalApartados;
    @FXML
    private Label lblTotalApartadosEfectivo;
    @FXML
    private Label lblTotalAEfectivo;
    @FXML
    private Label lblTotalApartadosTarjeta;
    @FXML
    private Label lblTotalATarjeta;
    @FXML
    private Label lblCantidadApartados;
    @FXML
    private Label lblApartados;
    @FXML
    private Label lblCantidadAbonos;
    @FXML
    private Label lblAbonos;
    @FXML
    private Label lblTotalPorAbonos;
    @FXML
    private Label lblTotalAbonos;
    @FXML
    private Label lblTotalAbonosEfectivo;
    @FXML
    private Label lblTotalAbEfectivo;
    @FXML
    private Label lblTotalAbonosTarjeta;
    @FXML
    private Label lblTotalAbTarjeta;
    @FXML
    private Label lblTotalIngresosEfectivo;
    @FXML
    private Label lblTotalIEfectivo;
    @FXML
    private Label lblTotalIngresosTarjeta;
    @FXML
    private Label lblTotalITarjeta;
    @FXML
    private TableView<Fecha> tblFechas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasFecha(tblFechas);
        CargarFechas("Ninguna");
        ParteFecha(false);
    }    

    @FXML
    private void MouseEscribir(MouseEvent event) {
        txtBusqueda.setCursor(Cursor.TEXT);
    }

    @FXML
    private void BusquedaFecha(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarFechas("Ninguna");
        }else{
            CargarFechas(txtBusqueda.getText());
        }
    }

    @FXML
    private void MouseDireccion(MouseEvent event) {
        tblFechas.setCursor(Cursor.CROSSHAIR);
    }

    @FXML
    private void MostrarFecha(MouseEvent event) {
        Fecha fecha = tblFechas.getSelectionModel().getSelectedItem();
        if(fecha != null){
            ParteFecha(true);
            CargarDatosFecha(fecha);
        }else{
            ParteFecha(false);
        }
    }
    
    private void CargarColumnasFecha(TableView<Fecha> table) {
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<Fecha, String>("fecha"));
        tblCFecha.setMinWidth(397);
        table.getColumns().addAll(tblCFecha);
    }
    
    private void CargarFechas(String busqueda){
        tblFechas.getItems().clear();
        tblFechas.setItems(datosFechas.Fechas(busqueda));
    }
    
    private void ParteFecha(boolean bandera){
        lblAbonos.setVisible(bandera);
        lblApartados.setVisible(bandera);
        lblCantidadAbonos.setVisible(bandera);
        lblCantidadApartados.setVisible(bandera);
        lblCantidadVentas.setVisible(bandera);
        lblIngresos.setVisible(bandera);
        lblTotalAEfectivo.setVisible(bandera);
        lblTotalATarjeta.setVisible(bandera);
        lblTotalAbEfectivo.setVisible(bandera);
        lblTotalAbTarjeta.setVisible(bandera);
        lblTotalAbonos.setVisible(bandera);
        lblTotalAbonosEfectivo.setVisible(bandera);
        lblTotalAbonosTarjeta.setVisible(bandera);
        lblTotalApartados.setVisible(bandera);
        lblTotalApartadosEfectivo.setVisible(bandera);
        lblTotalApartadosTarjeta.setVisible(bandera);
        lblTotalIEfectivo.setVisible(bandera);
        lblTotalITarjeta.setVisible(bandera);
        lblTotalIngresos.setVisible(bandera);
        lblTotalIngresosEfectivo.setVisible(bandera);
        lblTotalIngresosTarjeta.setVisible(bandera);
        lblTotalPorAbonos.setVisible(bandera);
        lblTotalPorApartados.setVisible(bandera);
        lblTotalPorVentas.setVisible(bandera);
        lblTotalVEfectivo.setVisible(bandera);
        lblTotalVTarjeta.setVisible(bandera);
        lblTotalVentas.setVisible(bandera);
        lblTotalVentasEfectivo.setVisible(bandera);
        lblTotalVentasTarjeta.setVisible(bandera);
        lblVentas.setVisible(bandera);
    }
    
    private void CargarDatosFecha(Fecha fecha){
        fecha = datosFechas.DatosFecha(fecha.getFecha().replace('-', '/'));
        lblVentas.setText(Integer.toString(fecha.getCantidadVentas()));
        lblTotalVentas.setText(String.valueOf(fecha.getTotalVentas()));
        lblTotalVEfectivo.setText(String.valueOf(fecha.getTotalVentasEfectivo()));
        lblTotalVTarjeta.setText(String.valueOf(fecha.getTotalVentasTarjeta()));
        lblApartados.setText(Integer.toString(fecha.getCantidadApartados()));
        lblTotalApartados.setText(String.valueOf(fecha.getTotalApartados()));
        lblTotalAEfectivo.setText(String.valueOf(fecha.getTotalApartadosEfectivo()));
        lblTotalATarjeta.setText(String.valueOf(fecha.getTotalApartadosTarjeta()));
        lblAbonos.setText(Integer.toString(fecha.getCantidadAbonos()));
        lblTotalAbonos.setText(String.valueOf(fecha.getTotalAbonos()));
        lblTotalAbEfectivo.setText(String.valueOf(fecha.getTotalAbonosEfectivo()));
        lblTotalAbTarjeta.setText(String.valueOf(fecha.getTotalAbonosTarjeta()));
        lblIngresos.setText(String.valueOf(fecha.getTotalIngresos()));
        lblTotalIEfectivo.setText(String.valueOf(fecha.getTotalIngresosEfecttivo()));
        lblTotalITarjeta.setText(String.valueOf(fecha.getTotalIngresosTarjeta()));
    }
}
