import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;

import static org.junit.Assert.*;

public class DuracionDiaCompletoTest {

    @Test
    public void rangoHorario() {
        LocalDateTime tiempoDuracion = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var duracion = new DuracionDiaCompleto(tiempoDuracion);
        LocalDateTime inicio = LocalDateTime.of(tiempoDuracion.toLocalDate(), LocalTime.of(0, 0));
        LocalDateTime fin = LocalDateTime.of(tiempoDuracion.toLocalDate(), LocalTime.of(23, 59));

        duracion.rangoHorario(inicio, fin);

        assertEquals(duracion.fechaInicio, inicio);
        assertEquals(duracion.fechaFin, fin);
    }
}