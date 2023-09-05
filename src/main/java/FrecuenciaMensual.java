import java.time.LocalDate;
import java.time.LocalDateTime;

public class FrecuenciaMensual implements Frecuencia{
    private int ocurrencias;

    public FrecuenciaMensual(){
        this.ocurrencias = 0;
    }

    public LocalDateTime getProxFecha(LocalDateTime fecha){
        return fecha.plusMonths(1);
    }

    public void aumentarOcurrencias(){
        this.ocurrencias++;
    }

    public int getOcurrencias() {
        return ocurrencias;
    }

    public int getIntervalo(){
        int intervaloMensual = 30;
        return intervaloMensual;
    }
}
