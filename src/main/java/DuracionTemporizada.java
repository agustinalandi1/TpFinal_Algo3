import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DuracionTemporizada extends Duracion{

    public DuracionTemporizada(LocalDateTime fecha){
        this.fechaInicio = fecha;
        this.fechaFin = fecha.plusHours(1);
    }

    @Override
    public void rangoHorario(LocalDateTime inicio, LocalDateTime fin){
        this.fechaInicio = inicio;
        this.fechaFin = fin;
    }
}
