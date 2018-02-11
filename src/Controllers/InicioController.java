
package Controllers;

import Models.DatosInicio;
import Models.Fecha;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InicioController implements Initializable {

    private DatosInicio datosInicio = new DatosInicio();
    
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
    private AnchorPane anchorPane1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarDatos();
    }    
    
    private void CargarDatos(){
        Fecha fecha = datosInicio.FechaHoy();
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
