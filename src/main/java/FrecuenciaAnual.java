import java.time.LocalDate;
import java.time.LocalDateTime;

public class FrecuenciaAnual implements Frecuencia{
    private int ocurrencias;
    public FrecuenciaAnual(){
        this.ocurrencias = 0;
    }

    public LocalDateTime getProxFecha(LocalDateTime fecha){
        return fecha.plusYears(1);
    }

    public void aumentarOcurrencias(){
        this.ocurrencias++;
    }

    public int getOcurrencias(){
        return ocurrencias;
    }

    public int getIntervalo(){
        int intervaloAnual = 365;
        return intervaloAnual;
    }
}
