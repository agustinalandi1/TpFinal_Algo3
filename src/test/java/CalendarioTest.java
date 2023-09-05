import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.Assert.*;

public class CalendarioTest {
    @Test
    public void eventoAgregado() {
        //arrange
        var calendario = new Calendario();
        //act
        LocalDateTime horaEvento = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        int idEvento = calendario.crearEvento(horaEvento);
        var evento = calendario.getEventoConId(idEvento);
        //assert
        assertNotNull(evento);
    }

    @Test
    public void eventoEliminado(){
        //arrange
        var calendario = new Calendario();
        //act
        LocalDateTime horaEvento = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        int idEvento = calendario.crearEvento(horaEvento);
        LocalDateTime horaEvento2 = LocalDateTime.of(LocalDate.of(2023, 4, 15), LocalTime.of(9, 30));
        int idEvento2 = calendario.crearEvento(horaEvento2);
        calendario = calendario.eliminarEvento(idEvento);
        var evento = calendario.getEventoConId(idEvento);
        //assert
        assertNull(evento);
    }
   @Test
    public void tareaAgregada(){
       //arrange
       var calendario = new Calendario();
       //act
       LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
       int idTarea = calendario.crearTarea(horaTarea);
       var tarea = calendario.getTareaConId(idTarea);
       //assert
       assertNotNull(tarea);
   }
    @Test
    public void tareaEliminada() {
        //arrange
        var calendario = new Calendario();
        //act
        LocalDateTime horaTarea = LocalDateTime.of(LocalDate.of(2023, 4, 21), LocalTime.of(9, 30));
        int idTarea = calendario.crearTarea(horaTarea);
        LocalDateTime horaTarea2 = LocalDateTime.of(LocalDate.of(2023, 4, 15), LocalTime.of(9, 30));
        int idTarea2 = calendario.crearTarea(horaTarea2);
        calendario = calendario.eliminarTarea(idTarea);
        var tarea = calendario.getTareaConId(idTarea);
        //assert
        assertNull(tarea);
    }
}