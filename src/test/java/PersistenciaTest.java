import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PersistenciaTest {

    @Test
    public void estadoCargadoArchivoExistente(){
        Calendario calendario = new Calendario();
        String archivo = "archivo.json";
        Persistencia persistencia = new Persistencia();

        Calendario calendarioCargado = persistencia.cargarEstado(calendario, archivo);

        assertNotNull(calendarioCargado);
    }

    @Test
    public void estadoCargadoArchivoInexistente() throws FileNotFoundException {
        Calendario calendario = new Calendario();
        String archivo = "Prueba.json";
        Persistencia persistencia = new Persistencia();

        try{
            Calendario calendarioCargado = persistencia.cargarEstado(calendario,archivo);
            assertNull(calendarioCargado);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void estadoCargadoArchivoExistenteVacio() throws IOException {
        Calendario calendario = new Calendario();
        File archivoTemporal = File.createTempFile("temp", ".json"); //creo un archivo temporal vacio
        String archivo = archivoTemporal.getAbsolutePath();
        Persistencia persistencia = new Persistencia();

        try{
            FileOutputStream archivoVacio = new FileOutputStream(archivo);
            archivoVacio.close();

            Calendario calendarioCargado = persistencia.cargarEstado(calendario, archivo);

            assertNotNull(calendarioCargado);

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            archivoTemporal.delete();
        }
    }

    @Test
    public void seGuardaElEstado() throws IOException, ClassNotFoundException {

        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(LocalDate.of(2023, 5, 21), LocalTime.of(10, 30));
        int idEvento = calendario.crearEvento(fecha);
        Evento evento = calendario.getEventoConId(idEvento);

        String archivo = "archivo.json";
        Persistencia persistencia = new Persistencia();

        persistencia.guardarEstado(calendario,archivo);

        Calendario calendarioDeserializado = new Calendario();
        calendarioDeserializado = persistencia.cargarEstado(calendarioDeserializado, archivo);

        assertNotNull(calendarioDeserializado);

        Evento evDeserializado = calendarioDeserializado.getEventoConId(idEvento);

        assertNotNull(evDeserializado);
        assertEquals(evDeserializado.getIndice(), evento.getIndice());

    }
}