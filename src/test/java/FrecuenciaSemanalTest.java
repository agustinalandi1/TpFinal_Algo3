import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class FrecuenciaSemanalTest {

    @Test
    public void getProxFecha() {
        DayOfWeek diaDeSemana = DayOfWeek.MONDAY; // pongo de ejemplo que se repita cada lunes
        var repeticion = new FrecuenciaSemanal(diaDeSemana);
        LocalDateTime fechaActual = LocalDateTime.of(LocalDate.of(2023, 4, 24), LocalTime.of(9, 30));
        LocalDateTime fechaEsperada = LocalDateTime.of(LocalDate.of(2023, 5, 1), LocalTime.of(9, 30));

        LocalDateTime fechaCalculada = repeticion.getProxFecha(fechaActual);

        assertEquals(fechaEsperada, fechaCalculada);
    }

}