
package DataBase;

import Models.Fecha;
import java.util.Calendar;

public class DatosInicio {
    
    private String fechaHoy;
    
    public Fecha FechaHoy(){
        Calendar c = Calendar.getInstance();
        fechaHoy = Integer.toString(c.get(Calendar.YEAR)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/"
            + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        DatosFechas datosFechas = new DatosFechas();
        return datosFechas.DatosFecha(fechaHoy);
    }
}