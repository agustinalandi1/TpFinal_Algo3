import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class AlarmaTest {

    @Test
    public void notificacionDisparada() {
        var horaAlarma = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var tiempoPrevio = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 0));
        var disparoAlarma = new DisparoAlarmaConIntervalo(horaAlarma);
        var notificacion = new NotificarMensaje(LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30)), "Reunion");

        var alarma = new Alarma(disparoAlarma, notificacion);

        LocalDateTime resultado = alarma.horaDispararNotificacion();

        assertEquals(resultado, tiempoPrevio);
    }
}