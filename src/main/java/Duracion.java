import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public abstract class Duracion implements Serializable {
    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFin;

    public LocalDateTime getFechaFin(){
        return fechaFin;
    }

    /*
    Define el rango horario de un evento (fecha y hora de comienzo; fecha y hora de fin)
     */
    public abstract void rangoHorario(LocalDateTime inicio, LocalDateTime fin);

    public void mostrarDuracion(){
        System.out.println("Fecha inicio: " + fechaInicio);
        System.out.println("Fecha fin: " + fechaFin);
    }


}
