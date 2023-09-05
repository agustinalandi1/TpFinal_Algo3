import java.io.Serializable;
import java.time.LocalDateTime;

public class Alarma implements Serializable {
    private DisparoAlarma disparoAlarma;
    private NotificacionAlarma notificacionAlarma;
    private boolean notificada;
    public Alarma(DisparoAlarma disparoAlarma, NotificacionAlarma notificacionAlarma){
        this.disparoAlarma = disparoAlarma;
        this.notificacionAlarma = notificacionAlarma;
        this.notificada = false;
    }
    public LocalDateTime horaDispararNotificacion() {
        notificacionAlarma.notificar(this.disparoAlarma.getHoraAlarma());
        return (this.disparoAlarma.getHoraAlarma());
    }

    public void setAlarmaDisparada(){
        notificada = true;
    }

    public boolean seDisparoAlarma(){
        return notificada;
    }

    public String getMensajeAlarma(){
       return(this.notificacionAlarma.getMensajeNotificacion());
    }
}
