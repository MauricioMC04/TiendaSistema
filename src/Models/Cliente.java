
package Models;

public class Cliente {
    
    private int idCliente;
    private String Nombre;
    private String Numero;
    private int CantidadApartados;

    public Cliente() {
    }

    public Cliente(int idCliente, String Nombre, String Numero, int CantidadApartados) {
        this.idCliente = idCliente;
        this.Nombre = Nombre;
        this.Numero = Numero;
        this.CantidadApartados = CantidadApartados;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public int getCantidadApartados() {
        return CantidadApartados;
    }

    public void setCantidadApartados(int CantidadApartados) {
        this.CantidadApartados = CantidadApartados;
    }
}
