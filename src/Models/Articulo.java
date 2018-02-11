
package Models;

public class Articulo {
    
    private int CodigoArticulo;
    private String Nombre;
    private double Precio;

    public Articulo() {
    }

    public Articulo(int CodigoArticulo, String Nombre, double Precio) {
        this.CodigoArticulo = CodigoArticulo;
        this.Nombre = Nombre;
        this.Precio = Precio;
    }

    public int getCodigoArticulo() {
        return CodigoArticulo;
    }

    public void setCodigoArticulo(int CodigoArticulo) {
        this.CodigoArticulo = CodigoArticulo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }
    
}
