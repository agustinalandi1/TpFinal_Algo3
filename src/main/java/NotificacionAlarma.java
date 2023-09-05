import java.io.Serializable;
import java.time.LocalDateTime;

public interface NotificacionAlarma extends Serializable {
    public void notificar(LocalDateTime horaAlarma);

    public String getMensajeNotificacion();
}
