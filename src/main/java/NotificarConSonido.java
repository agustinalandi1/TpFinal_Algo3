import java.time.LocalDateTime;

public class NotificarConSonido implements NotificacionAlarma{

    private String sonido;
    public NotificarConSonido(String sonido){
        this.sonido = sonido;
    }
    public void notificar(LocalDateTime horaAlarma){
        if(horaAlarma.isEqual(LocalDateTime.now())){
            //notificar con sonido
        }
    }
    public String getMensajeNotificacion(){
        return(" ");
    }
}
