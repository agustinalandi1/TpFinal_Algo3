import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Tarea extends Actividad{
    private boolean completada;
    private boolean diaCompleto;

    private LocalDateTime vencimiento;

    Tarea(LocalDateTime fecha) {
        super(LocalDateTime.of(fecha.toLocalDate(), LocalTime.of(0, 0)));
        this.completada = false;
        this.diaCompleto = true;
        this.vencimiento = fecha;
    }

    public void completarTarea(){
        this.completada = true;
    }

    /*
    Si creas una tarea en Google Calendar, por default te la arma de dia completo
    y vos despues si queres le podes poner hora de vencimineto
    */
    public void setVencimiento(LocalDateTime fechaVencimiento){
        this.fechaActividad = fechaVencimiento;
        vencimiento = fechaVencimiento;
        diaCompleto = false;
    }

    public void setDiaCompleto(LocalDateTime fecha){
        this.fechaActividad = LocalDateTime.of(fecha.toLocalDate(), LocalTime.of(0, 0));
        diaCompleto = true;
    }

    public boolean esDeDiaCompleto(){
        return diaCompleto;
    }

    public boolean fueCompletada(){
        return completada;
    }

    public LocalDateTime getVencimiento(){
        return this.vencimiento;
    }

}

