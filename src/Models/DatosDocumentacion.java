
package Models;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class DatosDocumentacion {
    
    private final Conexion conex = Conexion.singleton();
    
    public boolean FacturasDocumentacion(String direccion){
        try {
            ArrayList<Factura> facturas = Facturas();
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Facturas");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoFactura = filaEncabezado.createCell(0);
            ECodigoFactura.setCellValue("CodigoFactura");
            Cell ETipoDeFactura = filaEncabezado.createCell(1);
            ETipoDeFactura.setCellValue("TipoDeFactura");
            Cell EMontoTotal = filaEncabezado.createCell(2);
            EMontoTotal.setCellValue("MontoTotal");
            Cell EMontoPagado = filaEncabezado.createCell(3);
            EMontoPagado.setCellValue("MontoPagado");
            Cell EFecha = filaEncabezado.createCell(4);
            EFecha.setCellValue("Fecha");
            Cell EidCliente = filaEncabezado.createCell(5);
            EidCliente.setCellValue("idCliente");
            Cell EidTipoDePago = filaEncabezado.createCell(6);
            EidTipoDePago.setCellValue("idTipoDePago");
            for(int f=0;f<facturas.size();f++){
                Row fila = hoja.createRow(f+1);
                Cell CodigoFactura = fila.createCell(0);
                CodigoFactura.setCellValue(facturas.get(f).getCodigoFactura());
                Cell TipoDeFactura = fila.createCell(1);
                TipoDeFactura.setCellValue(facturas.get(f).getTipoDeFactura());
                Cell MontoTotal = fila.createCell(2);
                MontoTotal.setCellValue(facturas.get(f).getMontoTotal());
                Cell MontoPagado = fila.createCell(3);
                MontoPagado.setCellValue(facturas.get(f).getMontoPagado());
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Cell Fecha = fila.createCell(4);
                Fecha.setCellValue(df.format(facturas.get(f).getFecha()));
                Cell idCliente = fila.createCell(5);
                idCliente.setCellValue(facturas.get(f).getIdCliente());
                Cell idTipoDePago = fila.createCell(6);
                idTipoDePago.setCellValue(facturas.get(f).getIdTipoDePago());
            }
            for(int j = 0; j < 7; j++) { 
                hoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Facturas Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<Factura> Facturas(){
        ArrayList<Factura> facturas = new ArrayList<>();
        String sql ="select CodigoFactura, TipoDeFactura, MontoTotal, MontoPagado, Fecha, idCliente, idTipoDePago from"
            + " facturas";
        String[] datos = new String[6];
        Date fecha;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                fecha = rs.getDate(5);
                datos[4] = rs.getString(6);
                datos[5] = rs.getString(7);
                facturas.add(new Factura(Integer.parseInt(datos[0]),Integer.parseInt(datos[1]),
                    Double.parseDouble(datos[2]),Double.parseDouble(datos[3]),fecha,Integer.parseInt(datos[4]),
                    Integer.parseInt(datos[5])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar las Facturas \n" + ex);
        }
        conex.close();
        return facturas;
    }
    
    public boolean DetallesFacturasDocumentacion(String direccion){
        try {
            ArrayList<DetalleFactura> detallesFacturas = DetallesFacturas();
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("DetallesFacturas");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoFactura = filaEncabezado.createCell(0);
            ECodigoFactura.setCellValue("CodigoFactura");
            Cell ECodigoArticulo = filaEncabezado.createCell(1);
            ECodigoArticulo.setCellValue("CodigoArticulo");
            Cell EDescuento = filaEncabezado.createCell(2);
            EDescuento.setCellValue("Descuento");
            for(int f=0;f<detallesFacturas.size();f++){
                Row fila = hoja.createRow(f+1);
                Cell CodigoFactura = fila.createCell(0);
                CodigoFactura.setCellValue(detallesFacturas.get(f).getCodigoFactura());
                Cell CodigoArticulo = fila.createCell(1);
                CodigoArticulo.setCellValue(detallesFacturas.get(f).getCodigoArticulo());
                Cell Descuento = fila.createCell(2);
                Descuento.setCellValue(detallesFacturas.get(f).getDescuento());
            }
            for(int j = 0; j < 3; j++) { 
                hoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Detalles Facturas Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<DetalleFactura> DetallesFacturas(){
        ArrayList<DetalleFactura> detallesFacturas = new ArrayList<>();
        String sql ="select CodigoFactura, CodigoArticulo, Descuento from detallefactura";
        String[] datos = new String[3];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                detallesFacturas.add(new DetalleFactura(Integer.parseInt(datos[0]), Double.parseDouble(datos[2]), 
                    Integer.parseInt(datos[1]), "", 0));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Detalles Facturas \n" + ex);
        }
        conex.close();
        return detallesFacturas;
    }
    
    public boolean AbonosDocumentacion(String direccion){
        try {
            ArrayList<Abono> abonos = Abonos();
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Abonos");
            Row filaEncabezado = hoja.createRow(0);
            Cell EIdAbono = filaEncabezado.createCell(0);
            EIdAbono.setCellValue("idAbono");
            Cell ECodigoFactura = filaEncabezado.createCell(1);
            ECodigoFactura.setCellValue("CodigoFactura");
            Cell EMonto = filaEncabezado.createCell(2);
            EMonto.setCellValue("Monto");
            Cell EFecha = filaEncabezado.createCell(3);
            EFecha.setCellValue("Fecha");
            Cell EIdTipoDePago = filaEncabezado.createCell(4);
            EIdTipoDePago.setCellValue("idTipoDePago");
            for(int f=0;f<abonos.size();f++){
                Row fila = hoja.createRow(f+1);
                Cell IdAbono = fila.createCell(0);
                IdAbono.setCellValue(abonos.get(f).getIdAbono());
                Cell CodigoFactura = fila.createCell(1);
                CodigoFactura.setCellValue(abonos.get(f).getCodigoFactura());
                Cell Monto = fila.createCell(2);
                Monto.setCellValue(abonos.get(f).getMonto());
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Cell Fecha = fila.createCell(3);
                Fecha.setCellValue(df.format(abonos.get(f).getFecha()));
                Cell IdTipoDePago = fila.createCell(4);
                IdTipoDePago.setCellValue(abonos.get(f).getIdTipoDePago());
            }
            for(int j = 0; j < 5; j++) { 
                hoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Abonos Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<Abono> Abonos(){
        ArrayList<Abono> abonos = new ArrayList<>();
        String sql ="select idAbono, CodigoFactura, Monto, Fecha, idTipoDePago from abonos";
        String[] datos = new String[4];
        Date fecha;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                fecha = rs.getDate(4);
                datos[3] = rs.getString(5);
                abonos.add(new Abono(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), 
                     Double.parseDouble(datos[2]), fecha, Integer.parseInt(datos[3])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Abonos \n" + ex);
        }
        conex.close();
        return abonos;
    }
    
    public boolean ArticulosDocumentacion(String direccion){
        try {
            ArrayList<Articulo> articulos = Articulos();
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Articulos");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoArticulo = filaEncabezado.createCell(0);
            ECodigoArticulo.setCellValue("CodigoArticulo");
            Cell ENombre = filaEncabezado.createCell(1);
            ENombre.setCellValue("Nombre");
            Cell EPrecio = filaEncabezado.createCell(2);
            EPrecio.setCellValue("Precio");
            for(int f=0;f<articulos.size();f++){
                Row fila = hoja.createRow(f+1);
                Cell CodigoArticulo = fila.createCell(0);
                CodigoArticulo.setCellValue(articulos.get(f).getCodigoArticulo());
                Cell Nombre = fila.createCell(1);
                Nombre.setCellValue(articulos.get(f).getNombre());
                Cell Precio = fila.createCell(2);
                Precio.setCellValue(articulos.get(f).getPrecio());
            }
            for(int j = 0; j < 3; j++) { 
                hoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Articulos Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<Articulo> Articulos(){
        ArrayList<Articulo> articulos = new ArrayList<>();
        String sql ="select CodigoArticulo, Nombre, Precio from articulos";
        String[] datos = new String[3];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                articulos.add(new Articulo(Integer.parseInt(datos[0]), datos[1],Double.parseDouble(datos[2])));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Articulos \n" + ex);
        }
        conex.close();
        return articulos;
    }
    
    public boolean ClientesDocumentacion(String direccion){
        try {
            ArrayList<Cliente> clientes = Clientes();
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Clientes");
            Row filaEncabezado = hoja.createRow(0);
            Cell EIdCliente = filaEncabezado.createCell(0);
            EIdCliente.setCellValue("idCliente");
            Cell ENombre = filaEncabezado.createCell(1);
            ENombre.setCellValue("Nombre");
            Cell ENumero = filaEncabezado.createCell(2);
            ENumero.setCellValue("Numero");
            for(int f=0;f<clientes.size();f++){
                Row fila = hoja.createRow(f+1);
                Cell IdCliente = fila.createCell(0);
                IdCliente.setCellValue(clientes.get(f).getIdCliente());
                Cell Nombre = fila.createCell(1);
                Nombre.setCellValue(clientes.get(f).getNombre());
                Cell Numero = fila.createCell(2);
                Numero.setCellValue(clientes.get(f).getNumero());
            }
            for(int j = 0; j < 3; j++) { 
                hoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Clientes Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<Cliente> Clientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql ="select idCliente, Nombre, Numero from clientes";
        String[] datos = new String[3];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                clientes.add(new Cliente(Integer.parseInt(datos[0]), datos[1],datos[2],0));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Clientes \n" + ex);
        }
        conex.close();
        return clientes;
    }
    
    public boolean TodoDocumentacion(String direccion){
        try {
            ArrayList<Factura> facturas = Facturas();
            ArrayList<DetalleFactura> detallesFacturas = DetallesFacturas();
            ArrayList<Abono> abonos = Abonos();
            ArrayList<Articulo> articulos = Articulos();
            ArrayList<Cliente> clientes = Clientes();
            ArrayList<TipoDeFactura> tiposDeFacturas = TiposDeFactura();
            ArrayList<TipoDePago> tiposDePago = TiposDePago();
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet Articuloshoja = libro.createSheet("Articulos");
            Row filaEncabezadoArticulos = Articuloshoja.createRow(0);
            Cell ArticulosECodigoArticulo = filaEncabezadoArticulos.createCell(0);
            ArticulosECodigoArticulo.setCellValue("CodigoArticulo");
            Cell ArticulosENombre = filaEncabezadoArticulos.createCell(1);
            ArticulosENombre.setCellValue("Nombre");
            Cell ArticulosEPrecio = filaEncabezadoArticulos.createCell(2);
            ArticulosEPrecio.setCellValue("Precio");
            for(int f=0;f<articulos.size();f++){
                Row Articulosfila = Articuloshoja.createRow(f+1);
                Cell ArticulosCodigoArticulo = Articulosfila.createCell(0);
                ArticulosCodigoArticulo.setCellValue(articulos.get(f).getCodigoArticulo());
                Cell ArticulosNombre = Articulosfila.createCell(1);
                ArticulosNombre.setCellValue(articulos.get(f).getNombre());
                Cell ArticulosPrecio = Articulosfila.createCell(2);
                ArticulosPrecio.setCellValue(articulos.get(f).getPrecio());
            }
            for(int j = 0; j < 3; j++) { 
                Articuloshoja.autoSizeColumn((short)j);
            }
            Sheet Clienteshoja = libro.createSheet("Clientes");
            Row filaEncabezadoClientes = Clienteshoja.createRow(0);
            Cell ClientesEIdCliente = filaEncabezadoClientes.createCell(0);
            ClientesEIdCliente.setCellValue("idCliente");
            Cell ClientesENombre = filaEncabezadoClientes.createCell(1);
            ClientesENombre.setCellValue("Nombre");
            Cell ClientesENumero = filaEncabezadoClientes.createCell(2);
            ClientesENumero.setCellValue("Numero");
            for(int f=0;f<clientes.size();f++){
                Row Clientesfila = Clienteshoja.createRow(f+1);
                Cell ClientesIdCliente = Clientesfila.createCell(0);
                ClientesIdCliente.setCellValue(clientes.get(f).getIdCliente());
                Cell ClientesNombre = Clientesfila.createCell(1);
                ClientesNombre.setCellValue(clientes.get(f).getNombre());
                Cell ClientesNumero = Clientesfila.createCell(2);
                ClientesNumero.setCellValue(clientes.get(f).getNumero());
            }
            for(int j = 0; j < 3; j++) { 
                Clienteshoja.autoSizeColumn((short)j);
            }
            Sheet TiposDePagohoja = libro.createSheet("TiposDePago");
            Row filaEncabezadoTiposDePago = TiposDePagohoja.createRow(0);
            Cell TiposDePagoEIdTipoDePago = filaEncabezadoTiposDePago.createCell(0);
            TiposDePagoEIdTipoDePago.setCellValue("idTipoDePago");
            Cell TiposDePagoETipo = filaEncabezadoTiposDePago.createCell(1);
            TiposDePagoETipo.setCellValue("Tipo");
            for(int f=0;f<tiposDePago.size();f++){
                Row TiposDePagofila = TiposDePagohoja.createRow(f+1);
                Cell TiposDePagoIdTipoDePago = TiposDePagofila.createCell(0);
                TiposDePagoIdTipoDePago.setCellValue(tiposDePago.get(f).getIdTipoDePago());
                Cell TiposDePagoTipo = TiposDePagofila.createCell(1);
                TiposDePagoTipo.setCellValue(tiposDePago.get(f).getTipo());
            }
            for(int j = 0; j < 2; j++) { 
                TiposDePagohoja.autoSizeColumn((short)j);
            }
            Sheet TiposDeFacturahoja = libro.createSheet("TiposDeFactura");
            Row filaEncabezadoTiposDeFactura = TiposDeFacturahoja.createRow(0);
            Cell TiposDeFacturaEIdTipoDeFactura = filaEncabezadoTiposDeFactura.createCell(0);
            TiposDeFacturaEIdTipoDeFactura.setCellValue("idTipoDeFactura");
            Cell TiposDeFacturaETipo = filaEncabezadoTiposDeFactura.createCell(1);
            TiposDeFacturaETipo.setCellValue("Tipo");
            for(int f=0;f<tiposDeFacturas.size();f++){
                Row TiposDeFacturafila = TiposDeFacturahoja.createRow(f+1);
                Cell TiposDeFacturaIdTipoDeFactura = TiposDeFacturafila.createCell(0);
                TiposDeFacturaIdTipoDeFactura.setCellValue(tiposDeFacturas.get(f).getIdTipoDeFactura());
                Cell TiposDeFacturaTipo = TiposDeFacturafila.createCell(1);
                TiposDeFacturaTipo.setCellValue(tiposDeFacturas.get(f).getTipo());
            }
            for(int j = 0; j < 2; j++) { 
                TiposDeFacturahoja.autoSizeColumn((short)j);
            }
            Sheet Facturashoja = libro.createSheet("Facturas");
            Row filaEncabezadoFacturas = Facturashoja.createRow(0);
            Cell FacturasECodigoFactura = filaEncabezadoFacturas.createCell(0);
            FacturasECodigoFactura.setCellValue("CodigoFactura");
            Cell FacturasETipoDeFactura = filaEncabezadoFacturas.createCell(1);
            FacturasETipoDeFactura.setCellValue("TipoDeFactura");
            Cell FacturasEMontoTotal = filaEncabezadoFacturas.createCell(2);
            FacturasEMontoTotal.setCellValue("MontoTotal");
            Cell FacturasEMontoPagado = filaEncabezadoFacturas.createCell(3);
            FacturasEMontoPagado.setCellValue("MontoPagado");
            Cell FacturasEFecha = filaEncabezadoFacturas.createCell(4);
            FacturasEFecha.setCellValue("Fecha");
            Cell FacturasEidCliente = filaEncabezadoFacturas.createCell(5);
            FacturasEidCliente.setCellValue("idCliente");
            Cell FacturasEidTipoDePago = filaEncabezadoFacturas.createCell(6);
            FacturasEidTipoDePago.setCellValue("idTipoDePago");
            for(int f=0;f<facturas.size();f++){
                Row Facturasfila = Facturashoja.createRow(f+1);
                Cell FacturasCodigoFactura = Facturasfila.createCell(0);
                FacturasCodigoFactura.setCellValue(facturas.get(f).getCodigoFactura());
                Cell FacturasTipoDeFactura = Facturasfila.createCell(1);
                FacturasTipoDeFactura.setCellValue(facturas.get(f).getTipoDeFactura());
                Cell FacturasMontoTotal = Facturasfila.createCell(2);
                FacturasMontoTotal.setCellValue(facturas.get(f).getMontoTotal());
                Cell FacturasMontoPagado = Facturasfila.createCell(3);
                FacturasMontoPagado.setCellValue(facturas.get(f).getMontoPagado());
                Cell FacturasFecha = Facturasfila.createCell(4);
                FacturasFecha.setCellValue(df.format(facturas.get(f).getFecha()));
                Cell FacturasidCliente = Facturasfila.createCell(5);
                FacturasidCliente.setCellValue(facturas.get(f).getIdCliente());
                Cell FacturasidTipoDePago = Facturasfila.createCell(6);
                FacturasidTipoDePago.setCellValue(facturas.get(f).getIdTipoDePago());
            }
            for(int j = 0; j < 7; j++) { 
                Facturashoja.autoSizeColumn((short)j);
            }
            Sheet DetallesFacturashoja = libro.createSheet("DetallesFacturas");
            Row filaEncabezadoDetallesFacturas = DetallesFacturashoja.createRow(0);
            Cell DetallesFacturasECodigoFactura = filaEncabezadoDetallesFacturas.createCell(0);
            DetallesFacturasECodigoFactura.setCellValue("CodigoFactura");
            Cell DetallesFacturasECodigoArticulo = filaEncabezadoDetallesFacturas.createCell(1);
            DetallesFacturasECodigoArticulo.setCellValue("CodigoArticulo");
            Cell DetallesFacturasEDescuento = filaEncabezadoDetallesFacturas.createCell(2);
            DetallesFacturasEDescuento.setCellValue("Descuento");
            for(int f=0;f<detallesFacturas.size();f++){
                Row DetallesFacturasfila = DetallesFacturashoja.createRow(f+1);
                Cell DetallesFacturasCodigoFactura = DetallesFacturasfila.createCell(0);
                DetallesFacturasCodigoFactura.setCellValue(detallesFacturas.get(f).getCodigoFactura());
                Cell DetallesFacturasCodigoArticulo = DetallesFacturasfila.createCell(1);
                DetallesFacturasCodigoArticulo.setCellValue(detallesFacturas.get(f).getCodigoArticulo());
                Cell DetallesFacturasDescuento = DetallesFacturasfila.createCell(2);
                DetallesFacturasDescuento.setCellValue(detallesFacturas.get(f).getDescuento());
            }
            for(int j = 0; j < 3; j++) { 
                DetallesFacturashoja.autoSizeColumn((short)j);
            }
            Sheet Abonoshoja = libro.createSheet("Abonos");
            Row filaEncabezadoAbonos = Abonoshoja.createRow(0);
            Cell AbonosEIdAbono = filaEncabezadoAbonos.createCell(0);
            AbonosEIdAbono.setCellValue("idAbono");
            Cell AbonosECodigoFactura = filaEncabezadoAbonos.createCell(1);
            AbonosECodigoFactura.setCellValue("CodigoFactura");
            Cell AbonosEMonto = filaEncabezadoAbonos.createCell(2);
            AbonosEMonto.setCellValue("Monto");
            Cell AbonosEFecha = filaEncabezadoAbonos.createCell(3);
            AbonosEFecha.setCellValue("Fecha");
            Cell AbonosEIdTipoDePago = filaEncabezadoAbonos.createCell(4);
            AbonosEIdTipoDePago.setCellValue("idTipoDePago");
            for(int f=0;f<abonos.size();f++){
                Row Abonosfila = Abonoshoja.createRow(f+1);
                Cell AbonosIdAbono = Abonosfila.createCell(0);
                AbonosIdAbono.setCellValue(abonos.get(f).getIdAbono());
                Cell AbonosCodigoFactura = Abonosfila.createCell(1);
                AbonosCodigoFactura.setCellValue(abonos.get(f).getCodigoFactura());
                Cell AbonosMonto = Abonosfila.createCell(2);
                AbonosMonto.setCellValue(abonos.get(f).getMonto());
                Cell AbonosFecha = Abonosfila.createCell(3);
                AbonosFecha.setCellValue(df.format(abonos.get(f).getFecha()));
                Cell AbonosIdTipoDePago = Abonosfila.createCell(4);
                AbonosIdTipoDePago.setCellValue(abonos.get(f).getIdTipoDePago());
            }
            for(int j = 0; j < 5; j++) { 
                Abonoshoja.autoSizeColumn((short)j);
            }
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Documentacion: " + e);
        }
        return false;
    }
    
    public ArrayList<TipoDeFactura> TiposDeFactura(){
        ArrayList<TipoDeFactura> tipos = new ArrayList<>();
        String sql ="select idTipoDeFactura, Tipo from tiposdefactura";
        String[] datos = new String[2];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                tipos.add(new TipoDeFactura(Integer.parseInt(datos[0]), datos[1]));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Tipos de Facturas \n" + ex);
        }
        conex.close();
        return tipos;
    }
    
    public ArrayList<TipoDePago> TiposDePago(){
        ArrayList<TipoDePago> tipos = new ArrayList<>();
        String sql ="select idTipoDePago, Tipo from tiposdepago";
        String[] datos = new String[2];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                tipos.add(new TipoDePago(Integer.parseInt(datos[0]), datos[1]));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Tipos de Pago \n" + ex);
        }
        conex.close();
        return tipos;
    }
}
