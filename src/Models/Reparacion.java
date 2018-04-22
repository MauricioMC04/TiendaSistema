
package Models;

import java.sql.Date;

public class Reparacion {
    
    private int codigoReparacion;
    private Date fecha;
    private int idCliente;
    private String articulo;
    private String descripcion;
    private int estado;
    private double montoTotal;
    private double montoPagado;
    private int tipoPago;

    public Reparacion() {
    }

    public Reparacion(int codigoReparacion, Date fecha, int idCliente, String articulo, String descripcion, int estado,
            double montoTotal, double montoPagado, int tipoPago) {
        this.codigoReparacion = codigoReparacion;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.montoTotal = montoTotal;
        this.montoPagado = montoPagado;
        this.tipoPago = tipoPago;
    }

    public int getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(int codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }
}