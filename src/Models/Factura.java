
package Models;

import java.sql.Date;

public class Factura {
    
    private int CodigoFactura;
    private int TipoDeFactura;
    private double MontoTotal;
    private double MontoPagado;
    private Date Fecha;
    private int idCliente;
    private int idTipoDePago;

    public Factura() {
    }

    public Factura(int CodigoFactura, int TipoDeFactura, double MontoTotal, double MontoPagado, Date Fecha, int idCliente, int idTipoDePago) {
        this.CodigoFactura = CodigoFactura;
        this.TipoDeFactura = TipoDeFactura;
        this.MontoTotal = MontoTotal;
        this.MontoPagado = MontoPagado;
        this.Fecha = Fecha;
        this.idCliente = idCliente;
        this.idTipoDePago = idTipoDePago;
    }

    public int getCodigoFactura() {
        return CodigoFactura;
    }

    public void setCodigoFactura(int CodigoFactura) {
        this.CodigoFactura = CodigoFactura;
    }

    public int getTipoDeFactura() {
        return TipoDeFactura;
    }

    public void setTipoDeFactura(int TipoDeFactura) {
        this.TipoDeFactura = TipoDeFactura;
    }

    public double getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(double MontoTotal) {
        this.MontoTotal = MontoTotal;
    }

    public double getMontoPagado() {
        return MontoPagado;
    }

    public void setMontoPagado(double MontoPagado) {
        this.MontoPagado = MontoPagado;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTipoDePago() {
        return idTipoDePago;
    }

    public void setIdTipoDePago(int idTipoDePago) {
        this.idTipoDePago = idTipoDePago;
    }
    
}
