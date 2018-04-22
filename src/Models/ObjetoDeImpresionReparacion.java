
package Models;

import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ObjetoDeImpresionReparacion implements Printable{

    private Reparacion reparacion;
    private Cliente cliente;
    
    public ObjetoDeImpresionReparacion(Reparacion reparacion, Cliente cliente) {
        this.reparacion = reparacion;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Reparacion getReparacion() {
        return reparacion;
    }

    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }

    public int print(Graphics g, PageFormat f, int pageIndex){
        if(pageIndex == 0){
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Font font = g.getFont();
            g.setFont(new Font(font.getFontName(),font.getStyle(), 11));
            g.drawString("El Mundo del Calzado II", 90, 18);
            g.drawString("Santiago de Puriscal", 96, 36);
            g.drawString("Telef: 2416-65-56", 18, 54);
            g.drawString("Factura por Reparacion", 18, 72);
            g.drawString("N° Factura:  " + Integer.toString(this.getReparacion().getCodigoReparacion()), 18, 90);
            if(this.getReparacion().getTipoPago() == 1){
                g.drawString("Pago en Efectivo", 18, 108);
            }else{
                g.drawString("Pago por Tarjeta", 18, 108);
            }
            g.drawString("Fecha:  " + df.format(this.getReparacion().getFecha()), 18, 124);
            int y = 142;
            if(!this.getCliente().getNombre().equals("")){
                g.drawString("Cliente:  " + this.getCliente().getNombre(), 18, y);
                y+=18;
            }
            if(!this.getCliente().getNumero().equals("")){
                g.drawString("Telefono:  " + this.getCliente().getNumero(), 18, y);
                y+=18;
            }
            g.setFont(new Font(font.getFontName(),font.getStyle(), 10));
            g.drawString("Artículo", 18, y);
            y+=7;
            g.drawString("____________________________________________", 15, y);
            y+=15;
            int y2 = y;
            ArrayList<String> articulo = Division(this.getReparacion().getArticulo(),33);     
            for(int j = 0; j < articulo.size(); j++){
                g.drawString(articulo.get(j), 19, y2);
                y2+=15;
            }
            y2 = y;
            y+=5;
            g.drawString("____________________________________________", 15, y);
            y+=15;
            g.setFont(new Font(font.getFontName(),font.getStyle(), 11));
            g.drawString("Total a Pagar:  " + String.valueOf(this.getReparacion().getMontoTotal()), 18, y);
            y+=21;
            g.drawString("Total Pagado:  " + String.valueOf(this.getReparacion().getMontoPagado()), 18, y);
            y+=18;
            g.drawString("Faltante:  " + String.valueOf(this.getReparacion().getMontoTotal() - 
                this.getReparacion().getMontoPagado()), 18, y);
            y+=18;
            y+=5;
            g.drawString("Para cambios 8 días con factura", 18, y);
            y+=18;
            g.drawString("Regimen simplificado según", 18, y);
            y+=18;
            g.drawString("DGTD Oficio #4641001009626  23-07-2013", 18, y);
            return PAGE_EXISTS;
        }else{  
            return NO_SUCH_PAGE;
        }
    }
    
    private ArrayList<String> Division(String dato, int letras){
        ArrayList<String> datos = new ArrayList<>();
        if(dato.length() > letras){
            String subDato = dato;
            while(subDato.length() != 0){
                int blanco = 0;
                if(subDato.length() > letras){
                    blanco = Espacio(subDato.substring(0,letras));
                }else{
                    blanco = Espacio(subDato);
                }
                if(blanco != 0){
                    datos.add(subDato.substring(0, blanco));
                    subDato = subDato.substring(blanco+1, subDato.length());
                }else{
                    if(subDato.length() > letras){
                        datos.add(subDato.substring(0, letras) + "-");
                        subDato = subDato.substring(letras, subDato.length());
                    }else{
                        datos.add(subDato.substring(0, subDato.length()));
                        subDato = "";
                    }
                }   
            }
        }else{
            datos.add(dato);
        }
        return datos;
    }
    
    private int Espacio(String dato){
        for(int i = dato.length()-1; i >= 0; i--){
            if(dato.charAt(i) == ' '){
                return i;
            }
        }
        return 0;
    }
}