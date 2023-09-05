import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void tituloSetteado() {
        LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var tarea = new Tarea(horaTarea);

        var titulo = "Titulo Tarea";
        tarea.setTitulo(titulo);

        assertEquals(tarea.titulo, titulo);
    }

    @Test
    public void descripcionSetteada() {
        LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var tarea = new Tarea(horaTarea);

        var descripcion = "Descripcion Tarea";
        tarea.setDescripcion(descripcion);

        assertEquals(tarea.descripcion, descripcion);
    }

    @Test
    public void setAlarma() {
    }

    @Test
    public void vencimientoSetteado() {
        LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var tarea = new Tarea(horaTarea);
        var fechaVencimiento = LocalDateTime.of(LocalDate.of(2023, 4, 30), LocalTime.of(9,30));

        tarea.setVencimiento(fechaVencimiento);

        assertEquals(tarea.fechaActividad, fechaVencimiento);
    }

    @Test
    public void diaCompletoSetteado() {
        LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        var tarea = new Tarea(horaTarea);
        var fechaCompleta = LocalDateTime.of(horaTarea.toLocalDate(), LocalTime.of(0, 0));

        tarea.setDiaCompleto(fechaCompleta);

        assertEquals(tarea.fechaActividad, fechaCompleta);
    }
}