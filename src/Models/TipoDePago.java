
package Models;

public class TipoDePago {
    
    private int idTipoDePago;
    private String Tipo;

    public TipoDePago() {
    }

    public TipoDePago(int idTipoDePago, String Tipo) {
        this.idTipoDePago = idTipoDePago;
        this.Tipo = Tipo;
    }

    public int getIdTipoDePago() {
        return idTipoDePago;
    }

    public void setIdTipoDePago(int idTipoDePago) {
        this.idTipoDePago = idTipoDePago;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
}
