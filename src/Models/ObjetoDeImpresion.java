
package Models;

import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class ObjetoDeImpresion implements Printable{

    private Factura factura;
    private ObservableList<DetalleFactura> detalles;
    private Cliente cliente;
    
    public ObjetoDeImpresion(Factura factura, ObservableList<DetalleFactura> detalles, Cliente cliente) {
        this.factura = factura;
        this.detalles = detalles;
        this.cliente = cliente;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public ObservableList<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(ObservableList<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int print(Graphics g, PageFormat f, int pageIndex){
        if(pageIndex == 0){
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Font font = g.getFont();
            g.setFont(new Font(font.getFontName(),font.getStyle(), 11));
            g.drawString("El Mundo del Calzado II", 90, 18);
            g.drawString("Santiago de Puriscal", 96, 36);
            g.drawString("Telef: 2416-65-56", 18, 54);
            if(this.factura.getTipoDeFactura() == 1){
                g.drawString("Factura por Venta", 18, 72);
            }else{
                g.drawString("Factura por Apartado", 18, 72);
            }
            g.drawString("N° Factura:  " + Integer.toString(this.factura.getCodigoFactura()), 18, 90);
            if(this.factura.getIdTipoDePago() == 1){
                g.drawString("Pago en Efectivo", 18, 108);
            }else{
                g.drawString("Pago por Tarjeta", 18, 108);
            }
            g.drawString("Fecha:  " + df.format(this.factura.getFecha()), 18, 124);
            int y = 142;
            if(this.factura.getIdCliente() != 0){
                if(!this.cliente.getNombre().equals("")){
                    g.drawString("Cliente:  " + this.cliente.getNombre(), 18, y);
                    y+=18;
                }
                if(!this.cliente.getNumero().equals("")){
                    g.drawString("Telefono:  " + this.cliente.getNumero(), 18, y);
                    y+=18;
                }
            }
            g.setFont(new Font(font.getFontName(),font.getStyle(), 10));
            g.drawString("Código", 18, y);
            g.drawString("Artículo", 65, y);
            g.drawString("Monto", 210, y);
            y+=7;
            g.drawString("____________________________________________", 15, y);
            y+=15;
            double descuento = 0;
            double subTotal = 0;
            for (int i = 0; i < this.detalles.size(); i++) {
                int y2 = y;
                ArrayList<String> codigo = Division(Integer.toString(this.detalles.get(i).getCodigoArticulo()),7);
                ArrayList<String> articulo = Division(this.detalles.get(i).getNombre(),26);
                ArrayList<String> monto = Division(String.valueOf(this.detalles.get(i).getPrecio()),12); 
                descuento += this.detalles.get(i).getDescuento();
                subTotal += this.detalles.get(i).getPrecio();
                int subY = codigo.size();
                if(articulo.size() > subY){
                    subY = articulo.size();
                }
                if(monto.size() > subY){
                    subY = monto.size();
                }
                for(int j = 0; j < codigo.size(); j++){
                    g.drawString(codigo.get(j), 18, y2);
                    y2+=15;
                }
                y2 = y;
                for(int j = 0; j < articulo.size(); j++){
                    g.drawString(articulo.get(j), 65, y2);
                    y2+=15;
                }
                y2 = y;
                for(int j = 0; j < monto.size(); j++){
                    g.drawString(monto.get(j), 210, y2);
                    y2+=15;
                }
                y += subY*15;
            }
            y+=5;
            g.drawString("____________________________________________", 15, y);
            y+=15;
            g.setFont(new Font(font.getFontName(),font.getStyle(), 11));
            g.drawString("SubTotal:  " + String.valueOf(subTotal), 18, y);
            y+=18;
            g.drawString("Descuento:  " + String.valueOf(descuento), 18, y);
            y+=18;
            g.drawString("Total a Pagar:  " + String.valueOf(this.factura.getMontoTotal()), 18, y);
            y+=21;
            if(this.factura.getTipoDeFactura() == 2){
                g.drawString("Total Pagado:  " + String.valueOf(this.factura.getMontoPagado()), 18, y);
                y+=18;
                g.drawString("Faltante:  " + String.valueOf(this.factura.getMontoTotal()- this.factura.getMontoPagado()
                    ), 18, y);
                y+=18;
            }
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

// Anterior impresion para hoja ancha 
//
//public int print(Graphics g, PageFormat f, int pageIndex){
//        if(pageIndex == 0){
//            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            Font font = g.getFont();
//            g.setFont(new Font(font.getFontName(),font.getStyle(), 18));
//            g.drawString("Mundo del Calzado II", 30, 30);
//            if(factura.getTipoDeFactura() == 1){
//                g.drawString("Factura Venta", 425, 30);
//            }else{
//                g.drawString("Factura Apartado", 425, 30);
//            }
//            g.setFont(font);
//            g.drawString("Puriscal, San Jose", 30, 50);
//            g.drawString("Fecha:  " + df.format(factura.getFecha()), 425, 50);
//            g.drawString("Telef:  24166556", 30, 70);
//            g.drawString("N° Factura:  " + Integer.toString(factura.getCodigoFactura()), 425, 70);
//            if(factura.getIdCliente() != 0){
//                if(cliente.getNumero().equals("")){
//                    g.drawString("Cliente:  " + cliente.getNombre(), 30, 90);
//                }else{
//                    g.drawString("Cliente:  " + cliente.getNombre() + " - " + cliente.getNumero(), 30, 90);
//                }    
//            }
//            if(factura.getIdTipoDePago() == 1){
//                g.drawString("Pago en Efectivo", 425, 90);
//            }else{
//                g.drawString("Pago por Tarjeta", 425, 90);
//            }
//            g.drawRect( 50, 110, 47, 20);
//            g.drawRect( 97, 110, 141, 20);
//            g.drawRect( 238, 110, 94, 20);
//            g.drawRect( 332, 110, 94, 20);
//            g.drawRect( 426, 110, 104, 20);
//            g.drawString("Codigo", 52, 125);
//            g.drawString("Nombre", 147, 125);
//            g.drawString("Precio", 255, 125);
//            g.drawString("Descuento", 346, 125);
//            g.drawString("Total", 444, 125);
//            int y = 145;
//            int largo = 0;
//            for (int i = 0; i < detalles.size(); i++) {
//                g.drawString(Integer.toString(detalles.get(i).getCodigoArticulo()), 55, y);
//                g.drawString(detalles.get(i).getNombre(), 102, y);
//                g.drawString(String.valueOf(detalles.get(i).getPrecio()), 257, y);
//                g.drawString(String.valueOf(detalles.get(i).getDescuento()), 352, y);
//                g.drawString(String.valueOf(detalles.get(i).getPrecio()-detalles.get(i).getDescuento()), 445, y);
//                y += 15;
//                largo += 15;
//            }
//            y += 5;
//            g.drawString("Total:" + String.valueOf(factura.getMontoTotal()), 439, y);
//            largo +=10;
//            if(factura.getTipoDeFactura() == 2){
//                y += 15;
//                g.drawString("Pagado:" + String.valueOf(factura.getMontoPagado()), 433, y);
//                largo += 15;
//                y+= 15;
//                g.drawString("Faltante:" + String.valueOf(factura.getMontoTotal() - factura.getMontoPagado()), 433, y);
//                largo += 15;
//            }
//            y += 20;
//            g.drawRect( 50, 130, 47, 20 + largo);
//            g.drawRect( 97, 130, 141, 20 + largo);
//            g.drawRect( 238, 130, 94, 20 + largo);
//            g.drawRect( 332, 130, 94, 20 + largo);
//            g.drawRect( 426, 130, 104, 20 + largo);
//            g.drawString("Regimen simplificado segun DGTD Oficio #4641001009626  23-07-2013", 30, y);
//            return PAGE_EXISTS;
//        }else{  
//            return NO_SUCH_PAGE;
//        }
//    }


//
// if(pageIndex == 0){
//            
//            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            if(factura.getTipoDeFactura() == 1){
//                g.drawString("Factura por venta", 50, 50);
//            }else{
//                g.drawString("Factura por Apartado", 50, 50);
//            }
//            g.drawString("Codigo de factura:  " + Integer.toString(factura.getCodigoFactura()), 50, 70);
//            g.drawString("Fecha:  " + df.format(factura.getFecha()), 50, 90);
//            if(factura.getIdTipoDePago() == 1){
//                g.drawString("Pago en:  Efectivo", 50, 110);
//            }else{
//                g.drawString("Pago por:  Tarjeta", 50, 110);
//            }
//            g.drawString("Monto Total:  ₡" + String.valueOf(factura.getMontoTotal()), 50, 130);
//            if(factura.getTipoDeFactura() == 2){
//                g.drawString("Monto Pagado:  " + String.valueOf(factura.getMontoPagado()), 50, 150);
//                g.drawString("Cliente:  " + Integer.toString(factura.getIdCliente()), 50, 170);
//            }
//            g.drawString("Codigo Articulo", 80, 190);
//            g.drawString("Nombre", 170, 190);
//            g.drawString("Precio", 240, 190);
//            g.drawString("Descuento", 310, 190);
//            int y = 210;
//            for (int i = 0; i < detalles.size(); i++) {
//                g.drawString(Integer.toString(detalles.get(i).getCodigoArticulo()), 100, y);
//                g.drawString(detalles.get(i).getNombre(), 170, y);
//                g.drawString(String.valueOf(detalles.get(i).getPrecio()), 240, y);
//                g.drawString(String.valueOf(detalles.get(i).getDescuento()), 310, y);
//                y += 20;
//            }
//            return PAGE_EXISTS;
//        }else{  
//            return NO_SUCH_PAGE;
//        }