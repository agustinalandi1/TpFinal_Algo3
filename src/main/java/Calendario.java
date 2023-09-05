import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;


public class Calendario implements Serializable {
    private List<Evento> eventos;
    private List<Tarea> tareas;

    public Calendario() {

        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public int crearEvento(LocalDateTime fecha){
        Evento evento = new Evento(fecha);
        this.eventos.add(evento);
        return evento.getIndice();
    }

    /*
    Devuelve un evento guardado dentro del calendario para
    que pueda ser modificado. Devuelve null en caso de no encontrarlo
     */
    public Evento getEventoConId(int idEvento){
       int i = 0;
       Evento buscado = null;
        for(Evento ev : this.eventos){
           if((this.eventos.get(i).getIndice() == idEvento))
               buscado = this.eventos.get(i);
           i++;
       }
        return buscado;
    }

    /*
    Devuelve el calendario actualizado, conteniendo los eventos correspondientes,
    con sus modificaciones realizadas.
     */
    public Calendario guardarCambiosEvento(Evento evento){
        int i = 0;
        for(Evento event : this.eventos){
            if((this.eventos.get(i)).getIndice() == evento.getIndice())
                this.eventos.set(i, evento);
            i++;
        }

        return this;
    }

    /*
    Elimina un evento del calendario segun su ID.
     */
    public Calendario eliminarEvento(int idEvento){

        Iterator<Evento> eventosIt = (this.eventos).iterator();
        while(eventosIt.hasNext()){
            Evento evento = eventosIt.next();
            if(evento.getIndice() == idEvento){
                eventosIt.remove();
            } else {
                eventosIt.next();
            }
        }
        return this;
    }

    public int crearTarea(LocalDateTime fecha){
        Tarea tarea = new Tarea(fecha);

        this.tareas.add(tarea);
        return tarea.getIndice();
    }

    public Tarea getTareaConId(int idTarea){
        int i = 0;
        Tarea buscada = null;
        for(Tarea task : this.tareas){
            if((this.tareas.get(i).getIndice() == idTarea))
                buscada = this.tareas.get(i);
            i++;
        }
        return buscada;
    }

    public Calendario guardarCambiosTarea(Tarea tarea){
        int i = 0;
        for(Tarea task : this.tareas){
            if((this.tareas.get(i)).getIndice() == tarea.getIndice())
                this.tareas.set(i, tarea);
            i++;
        }

        return this;
    }

    public Calendario eliminarTarea(int idTarea){
        Iterator<Tarea> tareasIt = (this.tareas).iterator();
        while(tareasIt.hasNext()){
            Tarea tarea = tareasIt.next();
            if(tarea.getIndice() == idTarea){
                tareasIt.remove();
            } else {
                tareasIt.next();
            }
        }
        return this;
    }

    private List<Evento> obtenerRepeticionesEventos(Evento evento){
        List<Evento> repeticionesDeEventos = new ArrayList<Evento>();
        repeticionesDeEventos.add(evento);
        Evento nuevoEvento = null;
        do {
            nuevoEvento = repeticionesDeEventos.get(repeticionesDeEventos.size()-1).crearRepeticion();
            if(nuevoEvento != null) {
                repeticionesDeEventos.add(nuevoEvento);
            }
        } while (nuevoEvento != null);

        return repeticionesDeEventos;
    }

    /*
    Verifica si la fecha de una Actividad (dia, mes anio) esta dentro del rango de fechas que se quiere
    visualizar en el calendario
     */
    private boolean actividadCaeEnElRango(LocalDateTime fechaActividad, LocalDateTime inicioRango, LocalDateTime finRango){
        boolean actividadDentroDelRango = false;

        LocalDate diaFechaActividad = fechaActividad.toLocalDate();
        LocalDate diaInicioRango = inicioRango.toLocalDate();
        LocalDate diaFinRango = finRango.toLocalDate();

        if(diaFechaActividad.isEqual(diaInicioRango) || diaFechaActividad.isAfter(diaInicioRango)){
            if(diaFechaActividad.isBefore(diaFinRango) || diaFechaActividad.isEqual(diaFinRango)){
                actividadDentroDelRango = true;
            }
        }

        return actividadDentroDelRango;
    }

    /*
    Devuelve los eventos cuyas fechas de inicio estan dentro del rango que se quiere
     */

    public List<Evento> obtenerEventosEnRango(LocalDateTime inicioRango, LocalDateTime finRango){
        List<Evento> eventosEnRango = new ArrayList<Evento>();

        for(int i = 0; i < this.eventos.size(); i++){
            List<Evento> repeticionesEvento = obtenerRepeticionesEventos(eventos.get(i));
            if(actividadCaeEnElRango((eventos.get(i).getFechaInicio()), inicioRango, finRango)){
                eventosEnRango.add(eventos.get(i));
            }
            for(int j = 1; j < repeticionesEvento.size(); j++){
                if(actividadCaeEnElRango((repeticionesEvento.get(j).getFechaInicio()), inicioRango, finRango)){
                    eventosEnRango.add(repeticionesEvento.get(j));
                }
            }
        }
        return eventosEnRango;
    }


    /*
    Devuelve las tareas cuyas fechas de inicio estan dentro del rango que se quiere
     */
    public List<Tarea> obtenerTareasEnRango(LocalDateTime inicioRango, LocalDateTime finRango){
        List<Tarea> tareasEnRango = new ArrayList<Tarea>();

        for(int i = 0; i < this.tareas.size(); i++) {
            if (actividadCaeEnElRango((tareas.get(i).getFechaInicio()), inicioRango, finRango)) {
                tareasEnRango.add(tareas.get(i));
            }
        }
        return tareasEnRango;
    }

    /*
    Busca en los eventos y tareas guardados si hay alguna alarma por sonar
     */
    public List<Alarma> obtenerAlarmasActivas(){
        List<Alarma> alarmasActivas = new ArrayList<Alarma>();

        for(Evento evento : this.eventos){
            if(evento.tieneAlarmas())
                alarmasActivas.addAll(evento.getAlarmas());
        }
        for(Tarea tarea : this.tareas)
            if(tarea.tieneAlarmas())
                alarmasActivas.addAll(tarea.getAlarmas());

        return alarmasActivas;
    }

}



