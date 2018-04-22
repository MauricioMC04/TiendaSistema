
package DataBase;

import Models.Fecha;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatosFechas {
     
    private final Conexion conex = Conexion.singleton();
    
    public ObservableList<Fecha> Fechas(String busqueda){
        ObservableList <Fecha> modelo = FXCollections.observableArrayList();
        ObservableList <Fecha> fechasFacturas = FechasBusqueda(busqueda,0);
        ObservableList <Fecha> fechasAbonosAparados = FechasBusqueda(busqueda, 1);
        ObservableList <Fecha> fechasReparaciones = FechasBusqueda(busqueda, 2);
        ObservableList <Fecha> fechasAbonosReparaciones = FechasBusqueda(busqueda, 3);
        UnificarFechas(modelo, fechasFacturas);
        UnificarFechas(modelo, fechasAbonosAparados);
        UnificarFechas(modelo, fechasReparaciones);
        UnificarFechas(modelo, fechasAbonosReparaciones);
        return modelo;
    }
    
    private void UnificarFechas(ObservableList<Fecha> modelo, ObservableList<Fecha> otra){
        for (int i = 0; i < otra.size(); i++) {
            boolean existe = false;
            for (int j = 0; j < modelo.size() && existe != true; j++) {
                if(modelo.get(j).getFecha().equals(otra.get(i).getFecha())){
                    existe = true;
                }
            }
            if(!existe){
                modelo.add(otra.get(i));
            }
        }
    }
    
    private ObservableList<Fecha> FechasBusqueda(String busqueda, int tipo){
        ObservableList <Fecha> modelo = FXCollections.observableArrayList();
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlFechasBusqueda(busqueda, tipo));
            while (rs.next()) {
                modelo.add(new Fecha(rs.getString(1),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Fechas\n" + ex);
        }
        conex.close();
        return modelo;
    }
    
    private String GenerarSqlFechasBusqueda(String busqueda, int tipo){
        switch(tipo){
            case 1:
                if(busqueda.equals("Ninguna")){
                    return "select Fecha from abonos group by Fecha";
                }
                return "select Fecha from abonos where Fecha Like '%" + busqueda + "%'group by Fecha";
            case 2:
                if(busqueda.equals("Ninguna")){
                    return "select Fecha from reparaciones group by Fecha";
                }
                return "select Fecha from reparaciones where Fecha Like '%" + busqueda + "%'group by Fecha";
            case 3:
                if(busqueda.equals("Ninguna")){
                    return "select Fecha from abonosreparacion group by Fecha";
                }
                return "select Fecha from abonosreparacion where Fecha Like '%" + busqueda + "%'group by Fecha";
            default:
                if(busqueda.equals("Ninguna")){
                    return "select Fecha from facturas group by Fecha";
                }
                return "select Fecha from facturas where Fecha Like '%" + busqueda + "%'group by Fecha";
        }
    }

    public Fecha DatosFecha(String fechaHoy){
        Fecha fecha = new Fecha();
        fecha.setFecha(fechaHoy);
        fecha.setCantidadVentas(Cantidades(fechaHoy, 0));
        fecha.setTotalVentas(Totales(fechaHoy, 0, 0));
        fecha.setTotalVentasEfectivo(Totales(fechaHoy, 0, 1));
        fecha.setTotalVentasTarjeta(Totales(fechaHoy, 0, 2));
        fecha.setCantidadApartados(Cantidades(fechaHoy, 1));
        fecha.setTotalApartados(Totales(fechaHoy, 1, 0));
        fecha.setTotalApartadosEfectivo(Totales(fechaHoy, 1, 1));
        fecha.setTotalApartadosTarjeta(Totales(fechaHoy, 1, 2));
        fecha.setCantidadAbonosApartados(Cantidades(fechaHoy, 2));
        fecha.setTotalAbonosApartados(Totales(fechaHoy, 2, 0));
        fecha.setTotalAbonosApartadosEfectivo(Totales(fechaHoy, 2, 1));
        fecha.setTotalAbonosApartadosTarjeta(Totales(fechaHoy, 2, 2));
        fecha.setCantidadReparaciones(Cantidades(fechaHoy, 3));
        fecha.setTotalReparaciones(Totales(fechaHoy, 3, 0));
        fecha.setTotalReparacionesEfectivo(Totales(fechaHoy, 3, 1));
        fecha.setTotalReparacionesTarjeta(Totales(fechaHoy, 3, 2));
        fecha.setCantidadAbonosReparaciones(Cantidades(fechaHoy, 4));
        fecha.setTotalAbonosReparaciones(Totales(fechaHoy, 4, 0));
        fecha.setTotalAbonosReparacionesEfectivo(Totales(fechaHoy, 4, 1));
        fecha.setTotalAbonosReparacionesTarjeta(Totales(fechaHoy, 4, 2));
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
    
    private int Cantidades(String fecha, int tipo){
        int cantidadVentas = 0;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlCantidades(fecha, tipo));
            while (rs.next()) {
                cantidadVentas = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cantidades Fechas\n" + ex);
        }
        conex.close();
        return cantidadVentas;
    }
    
    private String GenerarSqlCantidades(String fecha, int tipo){
        switch(tipo){
            case 1:
                return "SELECT count(f.CodigoFactura) FROM facturas f INNER JOIN tiposdefactura t ON f.TipoDeFactura = "
                    + "t.idTipoDeFactura And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = 'Apartado'";
            case 2:
                return "select count(idAbono) from abonos where Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d')";
            case 3:
                return "select count(CodigoReparacion) from reparaciones where Fecha = STR_TO_DATE('" + fecha + "', "
                + "'%Y/%m/%d')";
            case 4:
                return "select count(CodigoAbono) from abonosreparacion where Fecha = STR_TO_DATE('" + fecha + "', "
                + "'%Y/%m/%d')";
            default:
                return "SELECT count(f.CodigoFactura) FROM facturas f INNER JOIN tiposdefactura t ON f.TipoDeFactura = "
                    + "t.idTipoDeFactura And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = 'Venta'";
        }
    }
    
    private double Totales(String fecha, int tipo, int pago){
        double TotalVentas = 0;
        Connection conexion = conex.open();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(GenerarSqlTotales(fecha, tipo, pago));
            while (rs.next()) {
                TotalVentas = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: Totales Fecha\n" + ex);
        }
        conex.close();
        return TotalVentas;
    }
    
    private String GenerarSqlTotales(String fecha, int tipo, int pago){
        switch(tipo){
            case 1:
                switch(pago){
                    case 1:
                        return "SELECT SUM(f.MontoPagado) FROM facturas f INNER JOIN tiposdefactura t ON "
                            + "f.TipoDeFactura = t.idTipoDeFactura INNER JOIN tiposdepago tp ON f.idTipoDePago = "
                            + "tp.idTipoDePago And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Apartado' And tp.Tipo = 'Efectivo'";
                    case 2:
                        return "SELECT SUM(f.MontoPagado) FROM facturas f INNER JOIN tiposdefactura t ON "
                            + "f.TipoDeFactura = t.idTipoDeFactura INNER JOIN tiposdepago tp ON f.idTipoDePago = "
                            + "tp.idTipoDePago And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Apartado' And tp.Tipo = 'Tarjeta'";
                    default:
                        return "SELECT SUM(f.MontoPagado) FROM facturas f INNER JOIN tiposdefactura t ON "
                            + "f.TipoDeFactura = t.idTipoDeFactura And f.Fecha = STR_TO_DATE('" + fecha + "', "
                            + "'%Y/%m/%d') And t.Tipo = 'Apartado'";
                }
            case 2: 
                switch(pago){
                    case 1:
                        return "SELECT SUM(a.Monto) FROM abonos a INNER JOIN tiposdepago t ON a.idTipoDePago = "
                            + "t.idTipoDePago And a.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Efectivo'";
                    case 2:
                        return "SELECT SUM(a.Monto) FROM abonos a INNER JOIN tiposdepago t ON a.idTipoDePago = "
                            + "t.idTipoDePago And a.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Tarjeta'";
                    default:
                        return "select SUM(Monto) from abonos where Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d')";
                }
            case 3:
                switch(pago){
                    case 1:
                        return "SELECT SUM(r.MontoPagado) FROM reparaciones r INNER JOIN tiposdepago t ON "
                            + "r.idTipoDePago = t.idTipoDePago And r.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') "
                            + "And t.Tipo = 'Efectivo'";
                    case 2:
                        return "SELECT SUM(r.MontoPagado) FROM reparaciones r INNER JOIN tiposdepago t ON "
                            + "r.idTipoDePago = t.idTipoDePago And r.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') "
                            + "And t.Tipo = 'Tarjeta'";
                    default:
                        return "select SUM(MontoPagado) from reparaciones where Fecha = STR_TO_DATE('" + fecha + "', "
                        + "'%Y/%m/%d')";
                }
            case 4:
                switch(pago){
                    case 1:
                        return "SELECT SUM(a.Monto) FROM abonosreparacion a INNER JOIN tiposdepago t ON a.idTipoDePago "
                            + "= t.idTipoDePago And a.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Efectivo'";
                    case 2:
                        return "SELECT SUM(a.Monto) FROM abonosreparacion a INNER JOIN tiposdepago t ON a.idTipoDePago "
                            + "= t.idTipoDePago And a.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Tarjeta'";
                    default:
                        return "select SUM(Monto) from abonosreparacion where Fecha = STR_TO_DATE('" + fecha + "', '%Y/"
                        + "%m/%d')";
                }
            default:
                switch(pago){
                    case 1:
                        return "SELECT SUM(f.MontoTotal) FROM facturas f INNER JOIN tiposdefactura t ON "
                            + "f.TipoDeFactura = t.idTipoDeFactura INNER JOIN tiposdepago tp ON f.idTipoDePago = "
                            + "tp.idTipoDePago And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = "
                            + "'Venta' And tp.Tipo = 'Efectivo'";
                    case 2:
                        return "SELECT SUM(f.MontoTotal) FROM facturas f INNER JOIN tiposdefactura t ON f.TipoDeFactura"
                            + " = t.idTipoDeFactura INNER JOIN tiposdepago tp ON f.idTipoDePago = tp.idTipoDePago And "
                            + "f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo = 'Venta' And tp.Tipo = "
                            + "'Tarjeta'";
                    default:
                        return "SELECT SUM(f.MontoTotal) FROM facturas f INNER JOIN tiposdefactura t ON f.TipoDeFactura"
                            + " = t.idTipoDeFactura And f.Fecha = STR_TO_DATE('" + fecha + "', '%Y/%m/%d') And t.Tipo ="
                            + " 'Venta'";
                }  
        }
    }
}