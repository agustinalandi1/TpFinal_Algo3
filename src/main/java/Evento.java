import java.time.LocalDateTime;

public class Evento extends Actividad{
    private Repeticion repeticion;
    private Duracion duracion;
    private boolean duracionDiaCompleto;
    private LocalDateTime fechaFin;

    private int intervaloRepeticion;

    /*
    Como en Google calendar, si creo un Evento, se crea automaticamente
    si titulo, ni descripcion, ni alarmas; con duracion de 1hora entre la hora acutal
    y la proxima hora.
     */
    Evento(LocalDateTime fecha) {
        super(fecha);
        this.fechaFin = fecha;
        this.duracion = new DuracionTemporizada(fecha);
        this.repeticion = null;
        this.intervaloRepeticion = 0;
    }

    public void establecerHorarioEvento(LocalDateTime inicio, LocalDateTime fin){
        this.fechaActividad = inicio;
        this.fechaFin = fin;
        this.duracion.rangoHorario(inicio, fin);
        if(duracionDiaCompleto){
            this.duracionDiaCompleto = true;
        }
    }

    public void setDuracion(Duracion duracion){
        this.duracion = duracion;
    }

    public Duracion getDuracion(){
        return duracion;
    }

    public LocalDateTime getFechaFin(){
        return fechaFin;
    }

    public void setRepeticion(Repeticion repeticion){
        this.repeticion = repeticion;
    }
    private void setIndiceRepeticion(int indice){
        this.indice = indice;
    }
    public boolean seReptie(){
        return(repeticion != null);
    }

    public int getIntervaloRepeticion(){
        int intervalo = (this.repeticion).getFrecuencia().getIntervalo();
        return intervalo;
    }

    public boolean duraDiaCompleto(){
        boolean duracionDiaCompleto = false;
        if(fechaActividad.getHour() == 0 && fechaActividad.getMinute() == 0){
            if(fechaFin.getHour() == 23 && fechaFin.getMinute() == 59){
                duracionDiaCompleto = true;
            }
        }

        return duracionDiaCompleto;
    }

    public Evento crearRepeticion(){
        if(this.repeticion != null) {
            if (this.repeticion.seRepite(this.fechaActividad)) {

                LocalDateTime fechaNuevoEvento = repeticion.getProxFecha(this.fechaActividad);
                LocalDateTime fechaFinNuevoEvento = repeticion.getProxFecha(this.duracion.getFechaFin());
                Evento nuevoEvento = new Evento(fechaNuevoEvento);
                nuevoEvento.setIndiceRepeticion(this.indice);
                nuevoEvento.setTitulo(this.titulo);
                nuevoEvento.setDescripcion(this.descripcion);
                nuevoEvento.setRepeticion(this.repeticion);
                nuevoEvento.setDuracion(this.duracion);
                nuevoEvento.establecerHorarioEvento(fechaNuevoEvento, fechaFinNuevoEvento);
                nuevoEvento.alarmas = this.alarmas;

                return nuevoEvento;
            } else {
                return null;
            }
        }else{
            return null;
        }
    }

}
