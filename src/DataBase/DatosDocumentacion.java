
package DataBase;

import Models.Abono;
import Models.Articulo;
import Models.Cliente;
import Models.DetalleFactura;
import Models.Factura;
import Models.Reparacion;
import Models.TipoDeFactura;
import Models.TipoDePago;
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
    
    private ArrayList<Factura> Facturas(){
        ArrayList<Factura> facturas = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select CodigoFactura, TipoDeFactura, MontoTotal, MontoPagado, Fecha, "
                + "idCliente, idTipoDePago from facturas");
            while (rs.next()) {
                facturas.add(new Factura(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getDate(5), 
                    rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar las Facturas \n" + ex);
        }
        conex.close();
        return facturas;
    }
    
    private ArrayList<DetalleFactura> DetallesFacturas(){
        ArrayList<DetalleFactura> detallesFacturas = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select CodigoFactura, CodigoArticulo, Descuento from detallefactura");
            while (rs.next()) {
                detallesFacturas.add(new DetalleFactura(rs.getInt(1), rs.getDouble(3), 
                    rs.getInt(2), "", 0));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Detalles Facturas \n" + ex);
        }
        conex.close();
        return detallesFacturas;
    }
    
    private ArrayList<Abono> Abonos(){
        ArrayList<Abono> abonos = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select idAbono, CodigoFactura, Monto, Fecha, idTipoDePago from abonos");
            while (rs.next()) {
                abonos.add(new Abono(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDate(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Abonos \n" + ex);
        }
        conex.close();
        return abonos;
    }
    
    private ArrayList<Articulo> Articulos(){
        ArrayList<Articulo> articulos = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select CodigoArticulo, Nombre, Precio from articulos");
            while (rs.next()) {
                articulos.add(new Articulo(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Articulos \n" + ex);
        }
        conex.close();
        return articulos;
    }
    
    private ArrayList<Cliente> Clientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select idCliente, Nombre, Numero from clientes");
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt(1), rs.getString(2),rs.getString(3),0));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Clientes \n" + ex);
        }
        conex.close();
        return clientes;
    }
    
    private ArrayList<TipoDeFactura> TiposDeFactura(){
        ArrayList<TipoDeFactura> tipos = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select idTipoDeFactura, Tipo from tiposdefactura");
            while (rs.next()) {
                tipos.add(new TipoDeFactura(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Tipos de Facturas \n" + ex);
        }
        conex.close();
        return tipos;
    }
    
    private ArrayList<TipoDePago> TiposDePago(){
        ArrayList<TipoDePago> tipos = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select idTipoDePago, Tipo from tiposdepago");
            while (rs.next()) {
                tipos.add(new TipoDePago(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Tipos de Pago \n" + ex);
        }
        conex.close();
        return tipos;
    }
    
    private ArrayList<Reparacion> Reparaciones(){
        ArrayList<Reparacion> reparaciones = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("Select CodigoReparacion, Fecha, Articulo, Descripcion, MontoTotal, "
                + "MontoPagado, idEstadoReparacion, idTipoDePago, idCliente from reparaciones");
            while (rs.next()) {
                reparaciones.add(new Reparacion(rs.getInt(1), rs.getDate(2), rs.getInt(9), rs.getString(3), 
                    rs.getString(4), rs.getInt(7), rs.getDouble(5), rs.getDouble(6), rs.getInt(8)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar las Reparaciones\n" + ex);
        }
        conex.close();
        return reparaciones;
    }
    
    private ArrayList<Abono> AbonosReparacion(){
        ArrayList<Abono> abonos = new ArrayList<>();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("Select CodigoAbono, Fecha, Monto, CodigoReparacion, idTipoDePago from "
                + "abonosReparacion");
            while (rs.next()) {
                abonos.add(new Abono(rs.getInt(1), rs.getInt(4), rs.getDouble(3), rs.getDate(2), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Cargar los Abonos Reparaciones\n" + ex);
        }
        conex.close();
        return abonos;
    }
    
    private FileOutputStream Documentacion(String direccion){
        try {
            File archivoXLS = new File(direccion);
            if(archivoXLS.exists()){
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            return archivo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Documentacion\n" + e);
        }
        return null;
    }
    
    private void HojaFacturas(Workbook libro){
        try {
            ArrayList<Factura> facturas = Facturas();
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
            for(int f = 0; f < facturas.size(); f++){
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja de Facturas\n" + e);
        }
    }
    
    private void HojaDetallesFactura(Workbook libro){
        try {
            ArrayList<DetalleFactura> detallesFacturas = DetallesFacturas();
            Sheet hoja = libro.createSheet("DetallesFacturas");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoFactura = filaEncabezado.createCell(0);
            ECodigoFactura.setCellValue("CodigoFactura");
            Cell ECodigoArticulo = filaEncabezado.createCell(1);
            ECodigoArticulo.setCellValue("CodigoArticulo");
            Cell EDescuento = filaEncabezado.createCell(2);
            EDescuento.setCellValue("Descuento");
            for(int f = 0; f < detallesFacturas.size(); f++){
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja de Detalles de Facturas\n" + e);
        }
    }
    
    private void HojaAbonosApartados(Workbook libro){
        try {
            ArrayList<Abono> abonos = Abonos();   
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
            for(int f = 0; f < abonos.size(); f++){
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Abonos Apartados\n" + e);
        }
    }
    
    private void HojaArticulos(Workbook libro){
        try {
            ArrayList<Articulo> articulos = Articulos();
            Sheet hoja = libro.createSheet("Articulos");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoArticulo = filaEncabezado.createCell(0);
            ECodigoArticulo.setCellValue("CodigoArticulo");
            Cell ENombre = filaEncabezado.createCell(1);
            ENombre.setCellValue("Nombre");
            Cell EPrecio = filaEncabezado.createCell(2);
            EPrecio.setCellValue("Precio");
            for(int f = 0; f < articulos.size(); f++){
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Articulos\n" + e);
        }
    }
    
    private void HojaClientes(Workbook libro){
        try {
            ArrayList<Cliente> clientes = Clientes();
            Sheet hoja = libro.createSheet("Clientes");
            Row filaEncabezado = hoja.createRow(0);
            Cell EIdCliente = filaEncabezado.createCell(0);
            EIdCliente.setCellValue("idCliente");
            Cell ENombre = filaEncabezado.createCell(1);
            ENombre.setCellValue("Nombre");
            Cell ENumero = filaEncabezado.createCell(2);
            ENumero.setCellValue("Numero");
            for(int f = 0; f < clientes.size(); f++){
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Clientes\n" + e);
        }
    }
    
    private void HojaReparaciones(Workbook libro){
        try {
            ArrayList<Reparacion> reparaciones = Reparaciones();
            Sheet hoja = libro.createSheet("Reparaciones");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoReparacion = filaEncabezado.createCell(0);
            ECodigoReparacion.setCellValue("CodigoReparacion");
            Cell EFecha = filaEncabezado.createCell(1);
            EFecha.setCellValue("Fecha");
            Cell EArticulo = filaEncabezado.createCell(2);
            EArticulo.setCellValue("Articulo");
            Cell EDescripcion = filaEncabezado.createCell(3);
            EDescripcion.setCellValue("Descripcion");
            Cell EMontoTotal = filaEncabezado.createCell(4);
            EMontoTotal.setCellValue("MontoTotal");
            Cell EMontoPagado = filaEncabezado.createCell(5);
            EMontoPagado.setCellValue("MontoPagado");
            Cell EidEstadoReparacion = filaEncabezado.createCell(6);
            EidEstadoReparacion.setCellValue("idEstadoReparacion");
            Cell EidTipoDePago = filaEncabezado.createCell(7);
            EidTipoDePago.setCellValue("idTipoDePago");
            Cell EidCliente = filaEncabezado.createCell(8);
            EidCliente.setCellValue("idCliente");
            for(int f = 0; f < reparaciones.size(); f++){
                Row fila = hoja.createRow(f + 1);
                Cell CodigoReparacion = fila.createCell(0);
                CodigoReparacion.setCellValue(reparaciones.get(f).getCodigoReparacion());
                Cell Fecha = fila.createCell(1);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Fecha.setCellValue(df.format(reparaciones.get(f).getFecha()));
                Cell Articulo = fila.createCell(2);
                Articulo.setCellValue(reparaciones.get(f).getArticulo());
                Cell Descripcion = fila.createCell(3);
                Descripcion.setCellValue(reparaciones.get(f).getDescripcion());
                Cell MontoTotal = fila.createCell(4);
                MontoTotal.setCellValue(reparaciones.get(f).getMontoTotal());
                Cell MontoPagado = fila.createCell(5);
                MontoPagado.setCellValue(reparaciones.get(f).getMontoPagado());
                Cell idEstadoReparacion = fila.createCell(6);
                idEstadoReparacion.setCellValue(reparaciones.get(f).getEstado());
                Cell idTipoDePago = fila.createCell(7);
                idTipoDePago.setCellValue(reparaciones.get(f).getTipoPago());
                Cell idCliente = fila.createCell(8);
                idCliente.setCellValue(reparaciones.get(f).getIdCliente());
            }
            for(int j = 0; j < 9; j++) { 
                hoja.autoSizeColumn((short)j);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Reparaciones\n" + e);
        }
    }
    
    private void HojaAbonosReparaciones(Workbook libro){
        try {
            ArrayList<Abono> abonosReparacion = AbonosReparacion();   
            Sheet hoja = libro.createSheet("AbonosReparaciones");
            Row filaEncabezado = hoja.createRow(0);
            Cell ECodigoAbono = filaEncabezado.createCell(0);
            ECodigoAbono.setCellValue("CodigoAbono");
            Cell EFecha = filaEncabezado.createCell(1);
            EFecha.setCellValue("Fecha");
            Cell EMonto = filaEncabezado.createCell(2);
            EMonto.setCellValue("Monto");
            Cell ECodigoReparacion = filaEncabezado.createCell(3);
            ECodigoReparacion.setCellValue("CodigoReparacion");
            Cell EIdTipoDePago = filaEncabezado.createCell(4);
            EIdTipoDePago.setCellValue("idTipoDePago");
            for(int f = 0; f < abonosReparacion.size(); f++){
                Row fila = hoja.createRow(f + 1);
                Cell CodigoAbono = fila.createCell(0);
                CodigoAbono.setCellValue(abonosReparacion.get(f).getIdAbono());
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Cell Fecha = fila.createCell(1);
                Fecha.setCellValue(df.format(abonosReparacion.get(f).getFecha()));
                Cell Monto = fila.createCell(2);
                Monto.setCellValue(abonosReparacion.get(f).getMonto());
                Cell CodigoReparacion = fila.createCell(3);
                CodigoReparacion.setCellValue(abonosReparacion.get(f).getCodigoFactura());
                Cell IdTipoDePago = fila.createCell(4);
                IdTipoDePago.setCellValue(abonosReparacion.get(f).getIdTipoDePago());
            }
            for(int j = 0; j < 5; j++) { 
                hoja.autoSizeColumn((short)j);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Abonos Reparaciones\n" + e);
        }
    }
    
    private void HojaTiposDePago(Workbook libro){
        try {
            ArrayList<TipoDePago> tiposDePago = TiposDePago();
            Sheet TiposDePagohoja = libro.createSheet("TiposDePago");
            Row filaEncabezadoTiposDePago = TiposDePagohoja.createRow(0);
            Cell TiposDePagoEIdTipoDePago = filaEncabezadoTiposDePago.createCell(0);
            TiposDePagoEIdTipoDePago.setCellValue("idTipoDePago");
            Cell TiposDePagoETipo = filaEncabezadoTiposDePago.createCell(1);
            TiposDePagoETipo.setCellValue("Tipo");
            for(int f = 0; f < tiposDePago.size(); f++){
                Row TiposDePagofila = TiposDePagohoja.createRow(f+1);
                Cell TiposDePagoIdTipoDePago = TiposDePagofila.createCell(0);
                TiposDePagoIdTipoDePago.setCellValue(tiposDePago.get(f).getIdTipoDePago());
                Cell TiposDePagoTipo = TiposDePagofila.createCell(1);
                TiposDePagoTipo.setCellValue(tiposDePago.get(f).getTipo());
            }
            for(int j = 0; j < 2; j++) { 
                TiposDePagohoja.autoSizeColumn((short)j);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Tipos de Pago\n" + e);
        }
    }
    
    private void HojaTiposDeFactura(Workbook libro){
        try {
            ArrayList<TipoDeFactura> tiposDeFacturas = TiposDeFactura();
            Sheet TiposDeFacturahoja = libro.createSheet("TiposDeFactura");
            Row filaEncabezadoTiposDeFactura = TiposDeFacturahoja.createRow(0);
            Cell TiposDeFacturaEIdTipoDeFactura = filaEncabezadoTiposDeFactura.createCell(0);
            TiposDeFacturaEIdTipoDeFactura.setCellValue("idTipoDeFactura");
            Cell TiposDeFacturaETipo = filaEncabezadoTiposDeFactura.createCell(1);
            TiposDeFacturaETipo.setCellValue("Tipo");
            for(int f = 0; f < tiposDeFacturas.size(); f++){
                Row TiposDeFacturafila = TiposDeFacturahoja.createRow(f+1);
                Cell TiposDeFacturaIdTipoDeFactura = TiposDeFacturafila.createCell(0);
                TiposDeFacturaIdTipoDeFactura.setCellValue(tiposDeFacturas.get(f).getIdTipoDeFactura());
                Cell TiposDeFacturaTipo = TiposDeFacturafila.createCell(1);
                TiposDeFacturaTipo.setCellValue(tiposDeFacturas.get(f).getTipo());
            }
            for(int j = 0; j < 2; j++) { 
                TiposDeFacturahoja.autoSizeColumn((short)j);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en Hoja Tipos de Factura\n" + e);
        }
    }
        
    public boolean FacturasDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaFacturas(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Facturas Documentacion: " + e);
        }
        return false;
    }
    
    public boolean DetallesFacturasDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaDetallesFactura(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Detalles Facturas Documentacion: " + e);
        }
        return false;
    }
    
    public boolean AbonosDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaAbonosApartados(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Abonos Documentacion: " + e);
        }
        return false;
    }

    public boolean ArticulosDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaArticulos(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Articulos Documentacion: " + e);
        }
        return false;
    }
       
    public boolean ClientesDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaClientes(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Clientes Documentacion: " + e);
        }
        return false;
    }
    
    public boolean ReparacionesDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaReparaciones(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Reparaciones Documentacion:\n" + e);
        }
        return false;
    }
    
    public boolean AbonosReparacionDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaAbonosReparaciones(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Abonos Reparacion Documentacion:\n" + e);
        }
        return false;
    }

    public boolean TodoDocumentacion(String direccion){
        try {
            FileOutputStream archivo = Documentacion(direccion);
            Workbook libro = new HSSFWorkbook();
            HojaArticulos(libro);
            HojaClientes(libro);
            HojaTiposDePago(libro);
            HojaTiposDeFactura(libro);
            HojaFacturas(libro);
            HojaDetallesFactura(libro);
            HojaAbonosApartados(libro);
            HojaReparaciones(libro);
            HojaAbonosReparaciones(libro);
            libro.write(archivo);
            archivo.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Documentacion: " + e);
        }
        return false;
    }   
}