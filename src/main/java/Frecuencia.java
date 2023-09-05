import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Frecuencia extends Serializable {
    LocalDateTime getProxFecha(LocalDateTime fecha);
    int getOcurrencias();

    int getIntervalo();
    void aumentarOcurrencias();
}
