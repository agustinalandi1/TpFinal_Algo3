import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Repeticion implements Serializable {
    private Frecuencia frecuencia;
    private TipoFinFrecuencia tipoFinFrecuencia;
    private int terminarPostOcurrencias;
    private LocalDateTime fechaFin;

    public Repeticion(Frecuencia frecuencia, TipoFinFrecuencia tipoFinFrecuencia, int terminarPostOcurrencias, LocalDateTime fechaFin){
        this.frecuencia = frecuencia;
        this.tipoFinFrecuencia = tipoFinFrecuencia;
        this.terminarPostOcurrencias = terminarPostOcurrencias;
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getProxFecha(LocalDateTime fecha){
        return frecuencia.getProxFecha(fecha);
    }

    public boolean seRepite(LocalDateTime fecha){
        return(tipoFinFrecuencia == TipoFinFrecuencia.INFINITA || getOcurrenciasRestantes(fecha) > 0);
    }

    public int getOcurrenciasRestantes(LocalDateTime fecha){
        if(tipoFinFrecuencia == TipoFinFrecuencia.POST_OCURRRENCIAS){
            frecuencia.aumentarOcurrencias();
            return (terminarPostOcurrencias - frecuencia.getOcurrencias());
        }else if (tipoFinFrecuencia == TipoFinFrecuencia.EN_FECHA){
            if(fecha.isBefore(fechaFin)){
                return 1;
            }else{
                return 0;
            }
        }else{
            return Integer.MAX_VALUE;
        }
    }

    public Frecuencia getFrecuencia(){
        return this.frecuencia;
    }
}
