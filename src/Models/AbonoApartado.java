
package Models;

import java.sql.Date;

public class AbonoApartado {
    
    private int idAbono;
    private int CodigoFactura;
    private double Monto;
    private Date fecha;
    private int idTipoDePago;

    public AbonoApartado() {
    }

    public AbonoApartado(int idAbono, int CodigoFactura, double Monto, Date fecha, int idTipoDePago) {
        this.idAbono = idAbono;
        this.CodigoFactura = CodigoFactura;
        this.Monto = Monto;
        this.fecha = fecha;
        this.idTipoDePago = idTipoDePago;
    }

    public int getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public int getCodigoFactura() {
        return CodigoFactura;
    }

    public void setCodigoFactura(int CodigoFactura) {
        this.CodigoFactura = CodigoFactura;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double Monto) {
        this.Monto = Monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdTipoDePago() {
        return idTipoDePago;
    }

    public void setIdTipoDePago(int idTipoDePago) {
        this.idTipoDePago = idTipoDePago;
    }
    
}
