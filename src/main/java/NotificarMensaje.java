import java.time.LocalDateTime;

public class NotificarMensaje implements NotificacionAlarma{

    LocalDateTime hora;
    String notificacion;

    public NotificarMensaje(LocalDateTime horaAlarma, String notificacion){
        this.hora = horaAlarma;
        this.notificacion = notificacion;
    }
    public void notificar(LocalDateTime horaAlarma){
        if(horaAlarma.isEqual(LocalDateTime.now())){
            //notificar con mensaje
        }
    }

    public String getMensajeNotificacion(){
        return this.notificacion;
    }
}
