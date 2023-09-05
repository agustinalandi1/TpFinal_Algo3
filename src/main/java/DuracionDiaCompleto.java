import java.time.LocalDateTime;
import java.time.LocalTime;


public class DuracionDiaCompleto extends Duracion{
    public DuracionDiaCompleto(LocalDateTime fecha){
        this.fechaInicio = LocalDateTime.of(fecha.toLocalDate(), LocalTime.of(00, 00));
        this.fechaFin = this.fechaInicio.plusHours(23).plusMinutes(59);
    }

    @Override
    public void rangoHorario(LocalDateTime inicio, LocalDateTime fin) {
        this.fechaInicio = LocalDateTime.of(inicio.toLocalDate(), LocalTime.of(00, 00));
        this.fechaFin = LocalDateTime.of(fin.toLocalDate(), LocalTime.of(23, 59));
    }
}
