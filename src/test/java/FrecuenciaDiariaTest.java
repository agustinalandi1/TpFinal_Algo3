import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class FrecuenciaDiariaTest {

    @Test
    public void getProxFecha() {
        var intervalo = 2; // pongo de ejemplo que se repita cada 2 dias
        var repeticion = new FrecuenciaDiaria(intervalo);
        LocalDateTime fechaActual = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        LocalDateTime fechaEsperada = LocalDateTime.of(LocalDate.of(2023, 4, 23), LocalTime.of(9, 30));

        LocalDateTime fechaCalculada = repeticion.getProxFecha(fechaActual);

        assertEquals(fechaEsperada, fechaCalculada);
    }
}