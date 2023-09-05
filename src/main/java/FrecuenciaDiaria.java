import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FrecuenciaDiaria implements Frecuencia{
    private int ocurrencias;
    private int intervalo;

    public FrecuenciaDiaria(int intervalo){
        this.ocurrencias = 0;
        this.intervalo = intervalo;
    }
    public LocalDateTime getProxFecha(LocalDateTime fecha){
        return fecha.plusDays(intervalo);
    }

    public void aumentarOcurrencias(){
        this.ocurrencias++;
    }
    public int getOcurrencias(){
        return ocurrencias;
    }

    public int getIntervalo(){
        return intervalo;
    }
}
