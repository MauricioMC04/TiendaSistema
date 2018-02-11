
package Models;

public class Fecha {
    
    private String fecha;
    private int cantidadVentas;
    private double totalVentas;
    private double totalVentasEfectivo;
    private double totalVentasTarjeta;
    private int cantidadApartados;
    private double totalApartados;
    private double totalApartadosEfectivo;
    private double totalApartadosTarjeta;
    private int cantidadAbonos;
    private double totalAbonos;
    private double totalAbonosEfectivo;
    private double totalAbonosTarjeta;
    private double totalIngresos;
    private double totalIngresosEfecttivo;
    private double totalIngresosTarjeta;

    public Fecha() {
    }

    public Fecha(String fecha, int cantidadVentas, double totalVentas, double totalVentasEfectivo, double totalVentasTarjeta, int cantidadApartados, double totalApartados, double totalApartadosEfectivo, double totalApartadosTarjeta, int cantidadAbonos, double totalAbonos, double totalAbonosEfectivo, double totalAbonosTarjeta, double totalIngresos, double totalIngresosEfecttivo, double totalIngresosTarjeta) {
        this.fecha = fecha;
        this.cantidadVentas = cantidadVentas;
        this.totalVentas = totalVentas;
        this.totalVentasEfectivo = totalVentasEfectivo;
        this.totalVentasTarjeta = totalVentasTarjeta;
        this.cantidadApartados = cantidadApartados;
        this.totalApartados = totalApartados;
        this.totalApartadosEfectivo = totalApartadosEfectivo;
        this.totalApartadosTarjeta = totalApartadosTarjeta;
        this.cantidadAbonos = cantidadAbonos;
        this.totalAbonos = totalAbonos;
        this.totalAbonosEfectivo = totalAbonosEfectivo;
        this.totalAbonosTarjeta = totalAbonosTarjeta;
        this.totalIngresos = totalIngresos;
        this.totalIngresosEfecttivo = totalIngresosEfecttivo;
        this.totalIngresosTarjeta = totalIngresosTarjeta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public double getTotalVentasEfectivo() {
        return totalVentasEfectivo;
    }

    public void setTotalVentasEfectivo(double totalVentasEfectivo) {
        this.totalVentasEfectivo = totalVentasEfectivo;
    }

    public double getTotalVentasTarjeta() {
        return totalVentasTarjeta;
    }

    public void setTotalVentasTarjeta(double totalVentasTarjeta) {
        this.totalVentasTarjeta = totalVentasTarjeta;
    }

    public int getCantidadApartados() {
        return cantidadApartados;
    }

    public void setCantidadApartados(int cantidadApartados) {
        this.cantidadApartados = cantidadApartados;
    }

    public double getTotalApartados() {
        return totalApartados;
    }

    public void setTotalApartados(double totalApartados) {
        this.totalApartados = totalApartados;
    }

    public double getTotalApartadosEfectivo() {
        return totalApartadosEfectivo;
    }

    public void setTotalApartadosEfectivo(double totalApartadosEfectivo) {
        this.totalApartadosEfectivo = totalApartadosEfectivo;
    }

    public double getTotalApartadosTarjeta() {
        return totalApartadosTarjeta;
    }

    public void setTotalApartadosTarjeta(double totalApartadosTarjeta) {
        this.totalApartadosTarjeta = totalApartadosTarjeta;
    }

    public int getCantidadAbonos() {
        return cantidadAbonos;
    }

    public void setCantidadAbonos(int cantidadAbonos) {
        this.cantidadAbonos = cantidadAbonos;
    }

    public double getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(double totalAbonos) {
        this.totalAbonos = totalAbonos;
    }

    public double getTotalAbonosEfectivo() {
        return totalAbonosEfectivo;
    }

    public void setTotalAbonosEfectivo(double totalAbonosEfectivo) {
        this.totalAbonosEfectivo = totalAbonosEfectivo;
    }

    public double getTotalAbonosTarjeta() {
        return totalAbonosTarjeta;
    }

    public void setTotalAbonosTarjeta(double totalAbonosTarjeta) {
        this.totalAbonosTarjeta = totalAbonosTarjeta;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getTotalIngresosEfecttivo() {
        return totalIngresosEfecttivo;
    }

    public void setTotalIngresosEfecttivo(double totalIngresosEfecttivo) {
        this.totalIngresosEfecttivo = totalIngresosEfecttivo;
    }

    public double getTotalIngresosTarjeta() {
        return totalIngresosTarjeta;
    }

    public void setTotalIngresosTarjeta(double totalIngresosTarjeta) {
        this.totalIngresosTarjeta = totalIngresosTarjeta;
    }
}
