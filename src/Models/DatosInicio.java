
package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class DatosInicio {
    
    private final Conexion conex = Conexion.singleton();
    
    public Fecha FechaHoy(){
        Calendar c = Calendar.getInstance();   
        return DatosFecha(Integer.toString(c.get(Calendar.YEAR)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/"
            + Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
    }
    
    private Fecha DatosFecha(String fechaHoy){
        Fecha fecha = new Fecha();
        fecha.setFecha(fechaHoy);
        fecha.setCantidadVentas(CantidadVentas(fechaHoy));
        fecha.setTotalVentas(TotalVentas(fechaHoy));
        fecha.setTotalVentasEfectivo(TotalVentasEfectivo(fechaHoy));
        fecha.setTotalVentasTarjeta(TotalVentasTarjeta(fechaHoy));
        fecha.setCantidadApartados(CantidadApartados(fechaHoy));
        fecha.setTotalApartados(TotalApartados(fechaHoy));
        fecha.setTotalApartadosEfectivo(TotalApartadosEfectivo(fechaHoy));
        fecha.setTotalApartadosTarjeta(TotalApartadosTarjeta(fechaHoy));
        fecha.setCantidadAbonos(CantidadAbonos(fechaHoy));
        fecha.setTotalAbonos(TotalAbonos(fechaHoy));
        fecha.setTotalAbonosEfectivo(TotalAbonosEfectivo(fechaHoy));
        fecha.setTotalAbonosTarjeta(TotalAbonosTarjeta(fechaHoy));
        fecha.setTotalIngresos(fecha.getTotalAbonos() + fecha.getTotalApartados() + fecha.getTotalVentas());
        fecha.setTotalIngresosEfecttivo(fecha.getTotalAbonosEfectivo()+ fecha.getTotalApartadosEfectivo()+ fecha.getTotalVentasEfectivo());
        fecha.setTotalIngresosTarjeta(fecha.getTotalAbonosTarjeta()+ fecha.getTotalApartadosTarjeta()+ fecha.getTotalVentasTarjeta());
        return fecha;
    }
    
    private int CantidadVentas(String fecha){
        int cantidadVentas = 0;
        String sql = "select count(f.CodigoFactura) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta'";
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
    
    private double TotalVentas(String fecha){
        double TotalVentas = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta'";
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
    
    private double TotalVentasEfectivo(String fecha){
        double TotalVentasEfectivo = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta' And "
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
    
    private double TotalVentasTarjeta(String fecha){
        double TotalVentasTarjeta = 0;
        String sql = "select SUM(f.MontoTotal) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Venta' And "
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
    
    private int CantidadApartados(String fecha){
        int cantidadApartados = 0;
        String sql = "select count(f.CodigoFactura) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado'";
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
    
    private double TotalApartados(String fecha){
        double TotalApartados = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t where f.Fecha = STR_TO_DATE('" 
            + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado'";
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
    
    private double TotalApartadosEfectivo(String fecha){
        double TotalApartadosEfectivo = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado' And "
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
    
    private double TotalApartadosTarjeta(String fecha){
        double TotalApartadosTarjeta = 0;
        String sql = "select SUM(f.MontoPagado) from facturas f, tiposdefactura t, tiposdepago tp where f.Fecha = STR_TO_"
            + "DATE('" + fecha + "', '%Y/%m/%d') And f.TipoDeFactura = t.idTipoDeFactura And t.Tipo = 'Apartado' And "
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
    
    private int CantidadAbonos(String fecha){
        int cantidadAbonos = 0;
        String sql = "select count(idAbono) from abonos where Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d')";
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
    
    private double TotalAbonos(String fecha){
        double TotalAbonos = 0;
        String sql = "select SUM(Monto) from abonos where Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d')";
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
    
    private double TotalAbonosEfectivo(String fecha){
        double TotalAbonosEfectivo = 0;
        String sql = "select SUM(a.Monto) from abonos a, tiposdepago t where Fecha = STR_TO_DATE('" + fecha + "', "
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
    
    private double TotalAbonosTarjeta(String fecha){
        double TotalAbonosTarjeta = 0;
        String sql = "select SUM(a.Monto) from abonos a, tiposdepago t where Fecha = STR_TO_DATE('" + fecha + "', "
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
}
