
package Controllers;

import DataBase.DatosFechas;
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
    private Label lblTotalIngresosEfectivo;
    @FXML
    private Label lblTotalIEfectivo;
    @FXML
    private Label lblTotalIngresosTarjeta;
    @FXML
    private Label lblTotalITarjeta;
    @FXML
    private TableView<Fecha> tblFechas;
    @FXML
    private Label lblCantidadReparaciones;
    @FXML
    private Label lblReparaciones;
    @FXML
    private Label lblTotalPorReparaciones;
    @FXML
    private Label lblTotalReparaciones;
    @FXML
    private Label lblTotalReparacionesEfectivo;
    @FXML
    private Label lblTotalReparacionesTarjeta;
    @FXML
    private Label lblTotalRTarjeta;
    @FXML
    private Label lblCantidadAbonosReparacion;
    @FXML
    private Label lblAbonosReparacion;
    @FXML
    private Label lblTotalAbonosReparacion;
    @FXML
    private Label lblAbonosReparacionEfectivo;
    @FXML
    private Label lblAbonosReparacionTarjeta;
    @FXML
    private Label lblAbRTarjeta;
    @FXML
    private Label lblCantidadAbonosApartados;
    @FXML
    private Label lblAbonosApartados;
    @FXML
    private Label lblTotalPorAbonosApartados;
    @FXML
    private Label lblTotalAbonosApartados;
    @FXML
    private Label lblTotalAbonosApartadosEfectivo;
    @FXML
    private Label lblTotalAbApartadosEfectivo;
    @FXML
    private Label lblTotalAbonosApartadosTarjeta;
    @FXML
    private Label lblTotalAbApartadosTarjeta;
    @FXML
    private Label lblTotalAbReparacion;
    @FXML
    private Label lblTotalREfectivo;
    @FXML
    private Label lblTotalAbREfectivo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarColumnasFecha();
        CargarFechas("Ninguna");
        ParteFecha(false);
        tblFechas.setCursor(Cursor.CROSSHAIR);
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
    private void MostrarFecha(MouseEvent event) {
        Fecha fecha = tblFechas.getSelectionModel().getSelectedItem();
        if(fecha != null){
            ParteFecha(true);
            CargarDatosFecha(fecha);
        }else{
            ParteFecha(false);
        }
    }
    
    private void CargarColumnasFecha() {
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tblCFecha.setMinWidth(395);
        tblFechas.getColumns().addAll(tblCFecha);
    }
    
    private void CargarFechas(String busqueda){
        tblFechas.getItems().clear();
        tblFechas.setItems(datosFechas.Fechas(busqueda));
    }
    
    private void ParteFecha(boolean bandera){
        lblAbonosApartados.setVisible(bandera);
        lblApartados.setVisible(bandera);
        lblCantidadAbonosApartados.setVisible(bandera);
        lblCantidadApartados.setVisible(bandera);
        lblCantidadVentas.setVisible(bandera);
        lblIngresos.setVisible(bandera);
        lblTotalAEfectivo.setVisible(bandera);
        lblTotalATarjeta.setVisible(bandera);
        lblTotalAbApartadosEfectivo.setVisible(bandera);
        lblTotalAbApartadosTarjeta.setVisible(bandera);
        lblTotalAbonosApartados.setVisible(bandera);
        lblTotalAbonosApartadosEfectivo.setVisible(bandera);
        lblTotalAbonosApartadosTarjeta.setVisible(bandera);
        lblTotalApartados.setVisible(bandera);
        lblTotalApartadosEfectivo.setVisible(bandera);
        lblTotalApartadosTarjeta.setVisible(bandera);
        lblTotalIEfectivo.setVisible(bandera);
        lblTotalITarjeta.setVisible(bandera);
        lblTotalIngresos.setVisible(bandera);
        lblTotalIngresosEfectivo.setVisible(bandera);
        lblTotalIngresosTarjeta.setVisible(bandera);
        lblTotalPorAbonosApartados.setVisible(bandera);
        lblTotalPorApartados.setVisible(bandera);
        lblTotalPorVentas.setVisible(bandera);
        lblTotalVEfectivo.setVisible(bandera);
        lblTotalVTarjeta.setVisible(bandera);
        lblTotalVentas.setVisible(bandera);
        lblTotalVentasEfectivo.setVisible(bandera);
        lblTotalVentasTarjeta.setVisible(bandera);
        lblVentas.setVisible(bandera);
        lblCantidadReparaciones.setVisible(bandera);
        lblReparaciones.setVisible(bandera);
        lblTotalPorReparaciones.setVisible(bandera);
        lblTotalReparaciones.setVisible(bandera);
        lblTotalReparacionesEfectivo.setVisible(bandera);
        lblTotalREfectivo.setVisible(bandera);
        lblTotalReparacionesTarjeta.setVisible(bandera);
        lblTotalRTarjeta.setVisible(bandera);
        lblCantidadAbonosReparacion.setVisible(bandera);
        lblAbonosReparacion.setVisible(bandera);
        lblTotalAbonosReparacion.setVisible(bandera);
        lblAbonosReparacionEfectivo.setVisible(bandera);
        lblTotalAbREfectivo.setVisible(bandera);
        lblAbonosReparacionTarjeta.setVisible(bandera);
        lblAbRTarjeta.setVisible(bandera);
        lblTotalAbReparacion.setVisible(bandera);
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
        lblAbonosApartados.setText(Integer.toString(fecha.getCantidadAbonosApartados()));
        lblTotalAbonosApartados.setText(String.valueOf(fecha.getTotalAbonosApartados()));
        lblTotalAbApartadosEfectivo.setText(String.valueOf(fecha.getTotalAbonosApartadosEfectivo()));
        lblTotalAbApartadosTarjeta.setText(String.valueOf(fecha.getTotalAbonosApartadosTarjeta()));
        lblIngresos.setText(String.valueOf(fecha.getTotalIngresos()));
        lblTotalIEfectivo.setText(String.valueOf(fecha.getTotalIngresosEfecttivo()));
        lblTotalITarjeta.setText(String.valueOf(fecha.getTotalIngresosTarjeta()));
        lblReparaciones.setText(Integer.toString(fecha.getCantidadReparaciones()));
        lblTotalReparaciones.setText(String.valueOf(fecha.getTotalReparaciones()));
        lblTotalREfectivo.setText(String.valueOf(fecha.getTotalReparacionesEfectivo()));
        lblTotalRTarjeta.setText(String.valueOf(fecha.getTotalReparacionesTarjeta()));
        lblAbonosReparacion.setText(Integer.toString(fecha.getCantidadAbonosReparaciones()));
        lblTotalAbReparacion.setText(String.valueOf(fecha.getTotalAbonosReparaciones()));
        lblTotalAbREfectivo.setText(String.valueOf(fecha.getTotalAbonosReparacionesEfectivo()));
        lblAbRTarjeta.setText(String.valueOf(fecha.getTotalAbonosReparacionesTarjeta()));
    }
}
