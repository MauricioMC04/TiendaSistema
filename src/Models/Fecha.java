
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
    private int cantidadAbonosApartados;
    private double totalAbonosApartados;
    private double totalAbonosApartadosEfectivo;
    private double totalAbonosApartadosTarjeta;
    private double totalIngresos;
    private double totalIngresosEfecttivo;
    private double totalIngresosTarjeta;
    private int cantidadReparaciones;
    private double totalReparaciones;
    private double totalReparacionesEfectivo;
    private double totalReparacionesTarjeta;
    private int cantidadAbonosReparaciones;
    private double totalAbonosReparaciones;
    private double totalAbonosReparacionesEfectivo;
    private double totalAbonosReparacionesTarjeta;

    public Fecha() {
    }

    public Fecha(String fecha, int cantidadVentas, double totalVentas, double totalVentasEfectivo, 
            double totalVentasTarjeta, int cantidadApartados, double totalApartados, double totalApartadosEfectivo, 
            double totalApartadosTarjeta, int cantidadAbonosApartados, double totalAbonosApartados, 
            double totalAbonosApartadosEfectivo, double totalAbonosApartadosTarjeta, double totalIngresos, 
            double totalIngresosEfecttivo, double totalIngresosTarjeta, int cantidadReparaciones, 
            double totalReparaciones, double totalReparacionesEfectivo, double totalReparacionesTarjeta, 
            int cantidadAbonosReparaciones, double totalAbonosReparaciones, double totalAbonosReparacionesEfectivo, 
            double totalAbonosReparacionesTarjeta) {
        this.fecha = fecha;
        this.cantidadVentas = cantidadVentas;
        this.totalVentas = totalVentas;
        this.totalVentasEfectivo = totalVentasEfectivo;
        this.totalVentasTarjeta = totalVentasTarjeta;
        this.cantidadApartados = cantidadApartados;
        this.totalApartados = totalApartados;
        this.totalApartadosEfectivo = totalApartadosEfectivo;
        this.totalApartadosTarjeta = totalApartadosTarjeta;
        this.cantidadAbonosApartados = cantidadAbonosApartados;
        this.totalAbonosApartados = totalAbonosApartados;
        this.totalAbonosApartadosEfectivo = totalAbonosApartadosEfectivo;
        this.totalAbonosApartadosTarjeta = totalAbonosApartadosTarjeta;
        this.totalIngresos = totalIngresos;
        this.totalIngresosEfecttivo = totalIngresosEfecttivo;
        this.totalIngresosTarjeta = totalIngresosTarjeta;
        this.cantidadReparaciones = cantidadReparaciones;
        this.totalReparaciones = totalReparaciones;
        this.totalReparacionesEfectivo = totalReparacionesEfectivo;
        this.totalReparacionesTarjeta = totalReparacionesTarjeta;
        this.cantidadAbonosReparaciones = cantidadAbonosReparaciones;
        this.totalAbonosReparaciones = totalAbonosReparaciones;
        this.totalAbonosReparacionesEfectivo = totalAbonosReparacionesEfectivo;
        this.totalAbonosReparacionesTarjeta = totalAbonosReparacionesTarjeta;
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

    public int getCantidadAbonosApartados() {
        return cantidadAbonosApartados;
    }

    public void setCantidadAbonosApartados(int cantidadAbonosApartados) {
        this.cantidadAbonosApartados = cantidadAbonosApartados;
    }

    public double getTotalAbonosApartados() {
        return totalAbonosApartados;
    }

    public void setTotalAbonosApartados(double totalAbonosApartados) {
        this.totalAbonosApartados = totalAbonosApartados;
    }

    public double getTotalAbonosApartadosEfectivo() {
        return totalAbonosApartadosEfectivo;
    }

    public void setTotalAbonosApartadosEfectivo(double totalAbonosApartadosEfectivo) {
        this.totalAbonosApartadosEfectivo = totalAbonosApartadosEfectivo;
    }

    public double getTotalAbonosApartadosTarjeta() {
        return totalAbonosApartadosTarjeta;
    }

    public void setTotalAbonosApartadosTarjeta(double totalAbonosApartadosTarjeta) {
        this.totalAbonosApartadosTarjeta = totalAbonosApartadosTarjeta;
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

    public int getCantidadReparaciones() {
        return cantidadReparaciones;
    }

    public void setCantidadReparaciones(int cantidadReparaciones) {
        this.cantidadReparaciones = cantidadReparaciones;
    }

    public double getTotalReparaciones() {
        return totalReparaciones;
    }

    public void setTotalReparaciones(double totalReparaciones) {
        this.totalReparaciones = totalReparaciones;
    }

    public double getTotalReparacionesEfectivo() {
        return totalReparacionesEfectivo;
    }

    public void setTotalReparacionesEfectivo(double totalReparacionesEfectivo) {
        this.totalReparacionesEfectivo = totalReparacionesEfectivo;
    }

    public double getTotalReparacionesTarjeta() {
        return totalReparacionesTarjeta;
    }

    public void setTotalReparacionesTarjeta(double totalReparacionesTarjeta) {
        this.totalReparacionesTarjeta = totalReparacionesTarjeta;
    }

    public int getCantidadAbonosReparaciones() {
        return cantidadAbonosReparaciones;
    }

    public void setCantidadAbonosReparaciones(int cantidadAbonosReparaciones) {
        this.cantidadAbonosReparaciones = cantidadAbonosReparaciones;
    }

    public double getTotalAbonosReparaciones() {
        return totalAbonosReparaciones;
    }

    public void setTotalAbonosReparaciones(double totalAbonosReparaciones) {
        this.totalAbonosReparaciones = totalAbonosReparaciones;
    }

    public double getTotalAbonosReparacionesEfectivo() {
        return totalAbonosReparacionesEfectivo;
    }

    public void setTotalAbonosReparacionesEfectivo(double totalAbonosReparacionesEfectivo) {
        this.totalAbonosReparacionesEfectivo = totalAbonosReparacionesEfectivo;
    }

    public double getTotalAbonosReparacionesTarjeta() {
        return totalAbonosReparacionesTarjeta;
    }

    public void setTotalAbonosReparacionesTarjeta(double totalAbonosReparacionesTarjeta) {
        this.totalAbonosReparacionesTarjeta = totalAbonosReparacionesTarjeta;
    }
    
}
