import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public abstract class Actividad implements Serializable {
    protected String titulo;
    protected String descripcion;
    protected LocalDateTime fechaActividad;
    protected int indice;
    protected List<Alarma> alarmas;

    Actividad(LocalDateTime fecha){
        this.titulo = "Sin titulo";
        this.descripcion = "Sin descripcion";
        this.fechaActividad = fecha;
        this.indice = hashCode();
        this.alarmas = new ArrayList<Alarma>();
    }

    public void setTitulo(String tituloActividad){
        this.titulo = tituloActividad;
    }
    public void setDescripcion(String descripcionActividad){
        this.descripcion = descripcionActividad;
    }

    public void setAlarma(Alarma nuevaAlarma) {
        this.alarmas.add(nuevaAlarma);
    }

    public String getTitulo(){
        return this.titulo;
    }
    public String getDescripcion(){
        return this.descripcion;
    }

    public int getIndice(){
        return this.indice;
    }

    public LocalDateTime getFechaInicio(){
        return fechaActividad;
    }

    private void actualizarAlarmas(){
        for(int i = 0; i < alarmas.size(); i++){
            if(alarmas.get(i).seDisparoAlarma())
                alarmas.remove(i);
        }
    }
    public boolean tieneAlarmas(){
        actualizarAlarmas();
        return(alarmas.size() > 0);
    }

    public List<Alarma> getAlarmas(){
        return alarmas;
    }

}
