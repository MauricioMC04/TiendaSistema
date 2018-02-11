
package Models;

public class TipoDeFactura {
    
    private int idTipoDeFactura;
    private String Tipo;

    public TipoDeFactura() {
    }

    public TipoDeFactura(int idTipoDeFactura, String Tipo) {
        this.idTipoDeFactura = idTipoDeFactura;
        this.Tipo = Tipo;
    }

    public int getIdTipoDeFactura() {
        return idTipoDeFactura;
    }

    public void setIdTipoDeFactura(int idTipoDeFactura) {
        this.idTipoDeFactura = idTipoDeFactura;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
}
