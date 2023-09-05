import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class FrecuenciaSemanal implements Frecuencia{
    private int ocurrencias;
    private DayOfWeek diaDeSemana;

    public FrecuenciaSemanal(DayOfWeek diaDeSemana){
        this.ocurrencias = 0;
        this.diaDeSemana = diaDeSemana;
    }

    public LocalDateTime getProxFecha(LocalDateTime fecha){
        return fecha.with(TemporalAdjusters.next(diaDeSemana));
    }

    public int getIntervalo(){
        int intervaloSemanal = 7;
        return intervaloSemanal;
    }

    public void aumentarOcurrencias(){
        this.ocurrencias++;
    }

    public int getOcurrencias(){
        return ocurrencias;
    }
}
