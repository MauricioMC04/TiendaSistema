
package Models;

public class DetalleFactura extends Articulo{
    
    private int CodigoFactura;
    private double Descuento;

    public DetalleFactura() {
    }

    public DetalleFactura(int CodigoFactura, double Descuento) {
        this.CodigoFactura = CodigoFactura;
        this.Descuento = Descuento;
    }

    public DetalleFactura(int CodigoFactura, double Descuento, int CodigoArticulo, String Nombre, double Precio) {
        super(CodigoArticulo, Nombre, Precio);
        this.CodigoFactura = CodigoFactura;
        this.Descuento = Descuento;
    }

    public int getCodigoFactura() {
        return CodigoFactura;
    }

    public void setCodigoFactura(int CodigoFactura) {
        this.CodigoFactura = CodigoFactura;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double Descuento) {
        this.Descuento = Descuento;
    }

    
}
