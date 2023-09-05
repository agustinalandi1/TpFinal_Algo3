import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RepeticionTest {

    @Test
    public void getProxFecha() {
        var frecuencia = new FrecuenciaAnual();
        var tipoFinFrecuencia = TipoFinFrecuencia.POST_OCURRRENCIAS;
        var terminarPostOcurrencias = 1;
        LocalDateTime fechaFin = null;
        var repeticion = new Repeticion(frecuencia, tipoFinFrecuencia, terminarPostOcurrencias, fechaFin);

        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaACalcular = repeticion.getProxFecha(fechaActual);
        LocalDateTime fechaEsperada = fechaActual.plusYears(1);

        assertEquals(fechaEsperada, fechaACalcular);
    }

    @Test
    public void getOcurrenciasRestantes() {
        var frecuencia = new FrecuenciaDiaria(1);
        var tipoFinFrecuencia = TipoFinFrecuencia.POST_OCURRRENCIAS;
        var terminarPostOcurrencias = 5;
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
        var repeticion = new Repeticion(frecuencia, tipoFinFrecuencia, terminarPostOcurrencias, fechaFin);

        var concurrenciasRestantes = repeticion.getOcurrenciasRestantes(LocalDateTime.now());

        var concurrenciasResultantes = terminarPostOcurrencias - frecuencia.getOcurrencias();
        repeticion.getFrecuencia().aumentarOcurrencias();

        assertEquals(concurrenciasResultantes, concurrenciasRestantes);
    }
}