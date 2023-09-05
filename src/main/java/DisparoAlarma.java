import java.io.Serializable;
import java.time.LocalDateTime;

public interface DisparoAlarma extends Serializable {
    public LocalDateTime getHoraAlarma();
}