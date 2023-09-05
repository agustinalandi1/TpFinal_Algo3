import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class EventoTest {

    @Test
    public void tituloSeteado() {
        //arange
        LocalDateTime horaEvento = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var evento = new Evento(horaEvento);
        //act
        var titulo = "Este es el titulo";
        evento.setTitulo(titulo);
        //assert
        assertEquals(evento.titulo, titulo);
    }

    @Test
    public void descripcionSeteada(){
        //arange
        LocalDateTime horaEvento = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var evento = new Evento(horaEvento);
        //act
        var descripcion = "Esta es la descripcion";
        evento.setDescripcion(descripcion);
        //assert
        assertEquals(evento.descripcion, descripcion);
    }

    @Test
    public void setAlarma() {

    }

    @Test
    public void horarioEventoEstablecido() {
        //arange
        LocalDateTime inicio = LocalDateTime.of(LocalDate.of(2023, 4, 15), LocalTime.of(9, 30));
        LocalDateTime fin = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var evento = new Evento(inicio);

        //act
        evento.establecerHorarioEvento(inicio, fin);

        //assert
        assertEquals(evento.getDuracion().fechaInicio, inicio);
        assertEquals(evento.getDuracion().fechaFin, fin);
    }

    @Test
    public void setDuracion() {
        LocalDateTime inicio = LocalDateTime.of(LocalDate.of(2023, 4, 15), LocalTime.of(9, 30));
        var evento = new Evento(inicio);
        var duracion = new DuracionTemporizada(inicio);

        evento.setDuracion(duracion);

        assertEquals(evento.getDuracion(), duracion);
    }
}