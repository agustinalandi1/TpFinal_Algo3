import java.time.LocalDateTime;

public class NotificarConEMail implements NotificacionAlarma{

    private String email;
    public NotificarConEMail(String email){
        this.email = email;
    }

    public void notificar(LocalDateTime horaAlarma){
        if(horaAlarma.isEqual(LocalDateTime.now())){
            //notificar con email: le mandamos: su evento esta por comenzar
            //y deberiamos pasarle los datos del evento
        }
    }

    public String getMensajeNotificacion(){
        return("Se ha enviado una notificacion al correo " + email);
    }
}