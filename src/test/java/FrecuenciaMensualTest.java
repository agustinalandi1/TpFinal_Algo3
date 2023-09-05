import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class FrecuenciaMensualTest {

    @Test
    public void getProxFecha() {
        var repeticion = new FrecuenciaMensual();
        LocalDateTime fechaActual = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        LocalDateTime fechaEsperada = LocalDateTime.of(LocalDate.of(2023, 5, 21), LocalTime.of(9, 30));

        LocalDateTime fechaCalculada = repeticion.getProxFecha(fechaActual);

        assertEquals(fechaEsperada, fechaCalculada);
    }
}