import java.time.Duration;
import java.time.LocalDateTime;

public class DisparoAlarmaConIntervalo implements DisparoAlarma{
    private LocalDateTime horaAlarma;

    public DisparoAlarmaConIntervalo(LocalDateTime horaAlarma){
        this.horaAlarma = horaAlarma;
    }

    public LocalDateTime getHoraAlarma() {
        return this.horaAlarma;
    }
}
