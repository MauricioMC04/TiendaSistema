
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
    private Label lblTotalIngresosEfectivo;
    @FXML
    private Label lblTotalIEfectivo;
    @FXML
    private Label lblTotalIngresosTarjeta;
    @FXML
    private Label lblTotalITarjeta;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private Label lblCantidadReparaciones;
    @FXML
    private Label lblReparaciones;
    @FXML
    private Label lblTotalPorReparaciones;
    @FXML
    private Label lblTotalReparacionesEfectivo;
    @FXML
    private Label lblTotalReparacionesTarjeta;
    @FXML
    private Label lblTotalReparaciones;
    @FXML
    private Label lblTortalREjectivo;
    @FXML
    private Label lblTotalRTarjeta;
    @FXML
    private Label lblAbonosApartadoTarjeta;
    @FXML
    private Label lblTotalAbAEfectivo;
    @FXML
    private Label lblAbonosEfectivo;
    @FXML
    private Label lblTotalAbonosApartado;
    @FXML
    private Label lblCantidadAbonosApartado;
    @FXML
    private Label lblAbonosApartado;
    @FXML
    private Label lblTotalAbATarjeta;
    @FXML
    private Label lblCantidadAbonosReparacion;
    @FXML
    private Label lblTotalAbonosReparacion;
    @FXML
    private Label lblAbonosReparacionEfectivo;
    @FXML
    private Label lblAbonosReparacionTarjeta;
    @FXML
    private Label lblAbonosReparacion;
    @FXML
    private Label lblTotalAbREjectivo;
    @FXML
    private Label lblAbRTarjeta;

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
        lblAbonosApartado.setText(Integer.toString(fecha.getCantidadAbonos()));
        lblTotalAbonosApartado.setText(String.valueOf(fecha.getTotalAbonos()));
        lblTotalAbAEfectivo.setText(String.valueOf(fecha.getTotalAbonosEfectivo()));
        lblTotalAbATarjeta.setText(String.valueOf(fecha.getTotalAbonosTarjeta()));
        lblIngresos.setText(String.valueOf(fecha.getTotalIngresos()));
        lblTotalIEfectivo.setText(String.valueOf(fecha.getTotalIngresosEfecttivo()));
        lblTotalITarjeta.setText(String.valueOf(fecha.getTotalIngresosTarjeta()));
    }
}
