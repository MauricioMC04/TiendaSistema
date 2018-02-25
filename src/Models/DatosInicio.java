
package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class DatosInicio {
    
    private final Conexion conex = Conexion.singleton();
    private String fechaHoy;
    
    public Fecha FechaHoy(){
        Calendar c = Calendar.getInstance();
        fechaHoy = Integer.toString(c.get(Calendar.YEAR)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/"
            + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        return DatosFecha();
    }
    
    private Fecha DatosFecha(){
        Fecha fecha = new Fecha();
        fecha.setFecha(fechaHoy);
        fecha.setCantidadVentas(CantidadVentas());
        fecha.setTotalVentas(TotalVentas());
        fecha.setTotalVentasEfectivo(TotalVentasEfectivo());
        fecha.setTotalVentasTarjeta(TotalVentasTarjeta());
        fecha.setCantidadApartados(CantidadApartados());
        fecha.setTotalApartados(TotalApartados());
        fecha.setTotalApartadosEfectivo(TotalApartadosEfectivo());
        fecha.setTotalApartadosTarjeta(TotalApartadosTarjeta());
        fecha.setCantidadAbonosApartados(CantidadAbonosApartados());
        fecha.setTotalAbonosApartados(TotalAbonosApartados());
        fecha.setTotalAbonosApartadosEfectivo(TotalAbonosApartadosEfectivo());
        fecha.setTotalAbonosApartadosTarjeta(TotalAbonosApartadosTarjeta());
        fecha.setCantidadReparaciones(CantidadReparaciones());
        fecha.setTotalReparaciones(TotalReparaciones());
        fecha.setTotalReparacionesEfectivo(TotalReparacioneEfectivo());
        fecha.setTotalReparacionesTarjeta(TotalReparacionesTarjeta());
        fecha.setCantidadAbonosReparaciones(CantidadAbonosReparaciones());
        fecha.setTotalAbonosReparaciones(TotalAbonosReparaciones());
        fecha.setTotalAbonosReparacionesEfectivo(TotalAbonosReparacionesEfectivo());
        fecha.setTotalAbonosReparacionesTarjeta(TotalAbonosReparacionesTarjeta());
        fecha.setTotalIngresos(fecha.getTotalAbonosApartados() + fecha.getTotalApartados() + fecha.getTotalVentas() + 
            fecha.getTotalReparaciones() + fecha.getTotalAbonosReparaciones());
        fecha.setTotalIngresosEfecttivo(fecha.getTotalAbonosApartadosEfectivo() + fecha.getTotalApartadosEfectivo() +
            fecha.getTotalVentasEfectivo() + fecha.getTotalReparacionesEfectivo() + 
            fecha.getTotalAbonosReparacionesEfectivo());
        fecha.setTotalIngresosTarjeta(fecha.getTotalAbonosApartadosTarjeta() + fecha.getTotalApartadosTarjeta() +
            fecha.getTotalVentasTarjeta() + fecha.getTotalReparacionesTarjeta() + 
            fecha.getTotalAbonosReparacionesTarjeta());
        return fecha;
    }
    
    private int CantidadVentas(){
        int cantidadVentas = 0;
        String sql = "select count(f.CodigoFactura) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            cantidadVentas = Integer.parseInt(datos[0]);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidad de Ventas \n" + ex);
        }
        conex.close();
        return cantidadVentas;
    }
    
    private double TotalVentas(){
        double TotalVentas = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalVentas = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total de Ventas \n" + ex);
        }
        conex.close();
        return TotalVentas;
    }
    
    private double TotalVentasEfectivo(){
        double TotalVentasEfectivo = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta' And "
            + "f.idTipoDePago = tp.idTipoDePago AND tp.Tipo = 'Efectivo'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalVentasEfectivo = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total de Ventas en Efectivo \n" + ex);
        }
        conex.close();
        return TotalVentasEfectivo;
    }
    
    private double TotalVentasTarjeta(){
        double TotalVentasTarjeta = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta' And "
            + "f.idTipoDePago = tp.idTipoDePago AND tp.Tipo = 'Tarjeta'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalVentasTarjeta = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total de Ventas en Tarjeta \n" + ex);
        }
        conex.close();
        return TotalVentasTarjeta;
    }
    
    private int CantidadApartados(){
        int cantidadApartados = 0;
        String sql = "select count(f.CodigoFactura) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            cantidadApartados = Integer.parseInt(datos[0]);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidad de Apartados \n" + ex);
        }
        conex.close();
        return cantidadApartados;
    }
    
    private double TotalApartados(){
        double TotalApartados = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalApartados = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Apartados \n" + ex);
        }
        conex.close();
        return TotalApartados;
    }
    
    private double TotalApartadosEfectivo(){
        double TotalApartadosEfectivo = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado' And "
            + "f.idTipoDePago = tp.idTipoDePago AND tp.Tipo = 'Efectivo'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalApartadosEfectivo = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Apartados en Efectivo \n" + ex);
        }
        conex.close();
        return TotalApartadosEfectivo;
    }
    
    private double TotalApartadosTarjeta(){
        double TotalApartadosTarjeta = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fechaHoy + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado' And "
            + "f.idTipoDePago = tp.idTipoDePago AND tp.Tipo = 'Tarjeta'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalApartadosTarjeta = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Apartados en Tarjeta \n" + ex);
        }
        conex.close();
        return TotalApartadosTarjeta;
    }
    
    private int CantidadAbonosApartados(){
        int cantidadAbonos = 0;
        String sql = "select count(idAbono) from abonos where Fecha = STR_TO_DATE('" + fechaHoy + "', '%Y/%m/%d')";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            cantidadAbonos = Integer.parseInt(datos[0]);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidad de Abonos \n" + ex);
        }
        conex.close();
        return cantidadAbonos;
    }
    
    private double TotalAbonosApartados(){
        double TotalAbonos = 0;
        String sql = "select SUM(Monto) from abonos where Fecha = STR_TO_DATE('" + fechaHoy + "', '%Y/%m/%d')";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalAbonos = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos \n" + ex);
        }
        conex.close();
        return TotalAbonos;
    }
    
    private double TotalAbonosApartadosEfectivo(){
        double TotalAbonosEfectivo = 0;
        String sql = "select SUM(a.Monto) from abonos a, tiposdepago t where Fecha = STR_TO_DATE('" + fechaHoy + "', "
            + "'%Y/%m/%d') And a.idTipoDePago = t.idTipoDePago And t.Tipo = 'Efectivo'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalAbonosEfectivo = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos en Efectivo \n" + ex);
        }
        conex.close();
        return TotalAbonosEfectivo;
    }
    
    private double TotalAbonosApartadosTarjeta(){
        double TotalAbonosTarjeta = 0;
        String sql = "select SUM(a.Monto) from abonos a, tiposdepago t where Fecha = STR_TO_DATE('" + fechaHoy + "', "
            + "'%Y/%m/%d') And a.idTipoDePago = t.idTipoDePago And t.Tipo = 'Tarjeta'";
        String[] datos = new String[1];
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
            }
            if(datos[0] != null && !datos[0].equals("")){
                TotalAbonosTarjeta = Double.parseDouble(datos[0]);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos en Tarjeta \n" + ex);
        }
        conex.close();
        return TotalAbonosTarjeta;
    }
    
    private int CantidadReparaciones(){
        int cantidadReparaciones = 0;
        String sql = "select count(CodigoReparacion) from reparaciones where Fecha = STR_TO_DATE('" + fechaHoy + "', "
            + "'%Y/%m/%d')";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            cantidadReparaciones = Integer.parseInt(dato);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidad de Reparaciones \n" + ex);
        }
        conex.close();
        return cantidadReparaciones;
    }
    
    private double TotalReparaciones(){
        double TotalReparaciones = 0;
        String sql = "select SUM(MontoPagado) from reparaciones where Fecha = STR_TO_DATE('" + fechaHoy + "', "
            + "'%Y/%m/%d')";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalReparaciones = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Reparaciones\n" + ex);
        }
        conex.close();
        return TotalReparaciones;
    }
    
    private double TotalReparacioneEfectivo(){
        double TotalReparacionesEfectivo = 0;
        String sql = "select SUM(r.MontoPagado) from reparaciones r, tiposdepago t where Fecha = STR_TO_DATE('" + 
            fechaHoy + "', '%Y/%m/%d') And r.idTipoDePago = t.idTipoDePago And t.Tipo = 'Efectivo'";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalReparacionesEfectivo = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Reparaciones Efectivo\n" + ex);
        }
        conex.close();
        return TotalReparacionesEfectivo;
    }
    
    private double TotalReparacionesTarjeta(){
        double TotalReparacionesTarjeta = 0;
        String sql = "select SUM(r.MontoPagado) from reparaciones r, tiposdepago t where Fecha = STR_TO_DATE('" + 
            fechaHoy + "', '%Y/%m/%d') And r.idTipoDePago = t.idTipoDePago And t.Tipo = 'Tarjeta'";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalReparacionesTarjeta = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Reparaciones Tarjeta\n" + ex);
        }
        conex.close();
        return TotalReparacionesTarjeta;
    }
    
    private int CantidadAbonosReparaciones(){
        int cantidadAbonosReparaciones = 0;
        String sql = "select count(CodigoAbono) from abonosreparacion where Fecha = STR_TO_DATE('" + fechaHoy + "', "
            + "'%Y/%m/%d')";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            cantidadAbonosReparaciones = Integer.parseInt(dato);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidad de Abonos de Reparaciones \n" + ex);
        }
        conex.close();
        return cantidadAbonosReparaciones;
    }
    
    private double TotalAbonosReparaciones(){
        double TotalAbonosReparaciones = 0;
        String sql = "select SUM(Monto) from abonosreparacion where Fecha = STR_TO_DATE('" + fechaHoy + "', '%Y/%m/%d')";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalAbonosReparaciones = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos Reparaciones\n" + ex);
        }
        conex.close();
        return TotalAbonosReparaciones;
    }
    
    private double TotalAbonosReparacionesEfectivo(){
        double TotalAbonosReparacionesEfectivo = 0;
        String sql = "select SUM(a.Monto) from abonosreparacion a, tiposdepago t where Fecha = STR_TO_DATE('" + 
            fechaHoy + "', '%Y/%m/%d') And a.idTipoDePago = t.idTipoDePago And t.Tipo = 'Efectivo'";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalAbonosReparacionesEfectivo = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos de Reparaciones Efectivo\n" + ex);
        }
        conex.close();
        return TotalAbonosReparacionesEfectivo;
    }
    
    private double TotalAbonosReparacionesTarjeta(){
        double TotalAbonosReparacionesTarjeta = 0;
        String sql = "select SUM(a.Monto) from abonosreparacion a, tiposdepago t where Fecha = STR_TO_DATE('" + 
            fechaHoy + "', '%Y/%m/%d') And a.idTipoDePago = t.idTipoDePago And t.Tipo = 'Tarjeta'";
        String dato = "";
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            if(dato != null && !dato.equals("")){
                TotalAbonosReparacionesTarjeta = Double.parseDouble(dato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Total Abonos de Reparaciones Tarjeta\n" + ex);
        }
        conex.close();
        return TotalAbonosReparacionesTarjeta;
    }
}
