import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VistaCalendario {
    private Calendario calendarioUsuario;
    private VBox vistaCalendario;
    private LocalDateTime fechaActualVista;
    private String vistaSeleccionada;
    private int cantidadFechasAMostrar;

    public VistaCalendario(Calendario calendario, VBox vistaCalendario, String vistaSeleccionada){
        this.calendarioUsuario = calendario;
        this.vistaCalendario = vistaCalendario;
        this.fechaActualVista = fechaActualVista;
        this.vistaSeleccionada = vistaSeleccionada;
    }

    /*
    Dada la fecha del rango actual que esta mirando el usuario, calcula las
    fechas de esa semana, de lunes a domingo.
     */
    private List<LocalDateTime> obtenerFechasSemana(LocalDateTime fechaHoy){
        List<LocalDateTime>fechasSemana = new ArrayList<LocalDateTime>();
        DayOfWeek diaSemana = fechaHoy.getDayOfWeek();
        int numeroDiaSemana = diaSemana.getValue();

        if(numeroDiaSemana > 1){
            int numeroDiaActual = numeroDiaSemana;
            LocalDateTime fechaActual = fechaHoy;
            while(numeroDiaActual > 1){
                fechaActual = fechaActual.minusDays(1);
                fechasSemana.add(0, fechaActual);
                numeroDiaActual -=1;
            }
        }
        fechasSemana.add(fechaHoy);
        if(numeroDiaSemana < 7){
            int numeroDiaActual = numeroDiaSemana;
            LocalDateTime fechaActual = fechaHoy;
            while(numeroDiaActual < 7){
                fechaActual = fechaActual.plusDays(1);
                fechasSemana.add(fechaActual);
                numeroDiaActual +=1;
            }
        }
        return fechasSemana;
    }

    /*
    Dada la fecha actual, devuelve las fechas del mes.
     */
    private List<LocalDateTime> obtenerFechasMes(LocalDateTime fechaHoy){
        List<LocalDateTime>fechasMes = new ArrayList<LocalDateTime>();

        int longitudMes = fechaHoy.getMonth().length(fechaHoy.toLocalDate().isLeapYear());

        for(int i = 1; i <= longitudMes; i++){
            fechasMes.add(fechaHoy.withDayOfMonth(i));
        }

        return fechasMes;
    }

    /*
    Formatea las fechas que va a ver el usuario
     */
    private Label obtenerFormatoFecha(LocalDateTime dia){
        Locale locale = new Locale("es");
        DayOfWeek day = dia.getDayOfWeek();
        String diaSemana = day.getDisplayName(TextStyle.SHORT, locale);
        DateTimeFormatter formatoSemana = DateTimeFormatter.ofPattern("dd MMM yyyy").localizedBy(locale);
        Label fecha = new Label(" " + diaSemana +" "+ dia.format(formatoSemana));
        return fecha;
    }

    /*
    Dado un dia devuelve los eventos cuya fecha de inicio coincide con esta.
     */
    private  List<Evento>obtenerEventosDelDia(LocalDateTime fechaDia, List<Evento>eventosRangoActual){
        LocalDate fechaHoy = fechaDia.toLocalDate();

        List<Evento>eventosDia = new ArrayList<Evento>();

        for(Evento evento: eventosRangoActual){
            LocalDate fechaEvento = evento.getFechaInicio().toLocalDate();
            if(fechaEvento.isEqual(fechaHoy))
                eventosDia.add(evento);
        }
        return eventosDia;
    }

    /*
    devuelve las tareas cuyo inicio coincide con un dia dado.
     */
    private  List<Tarea>obtenerTareasDelDia(LocalDateTime fechaDia, List<Tarea>tareasRangoActual){
        LocalDate fechaHoy = fechaDia.toLocalDate();

        List<Tarea>tareasDia = new ArrayList<Tarea>();
        for(Tarea tarea : tareasRangoActual){
            LocalDate fechaTarea = tarea.getFechaInicio().toLocalDate();
            if(fechaTarea.isEqual(fechaHoy))
                tareasDia.add(tarea);
        }
        return tareasDia;
    }

    /*
    Configura una nueva etiqueta con un mensaje dado.
     */
    private Label nuevaEtiqueta(double ancho, double altura, String texto){
        Label etiqueta = new Label();
        etiqueta.setStyle("-fx-border-color: black;");
        etiqueta.setPrefWidth(ancho);
        etiqueta.setPrefHeight(altura);
        etiqueta.setText(texto);
        return etiqueta;
    }

    /*
    Boton para expandir los datos de un Evento/Tarea
     */
    private Button configurarBotonPropiedades(double ancho, double alto){
        Button botonPropiedades = new Button();
        botonPropiedades.setPrefWidth(ancho/5);
        botonPropiedades.setPrefHeight(alto);
        botonPropiedades.setText("Ver propiedades");

        return botonPropiedades;
    }

    /*
    Devuelve los datos de un evento para mostrarlo en el calendario.
     */
    private HBox filaNuevoEvento(double anchoTotal, Evento evento){
        HBox filaEvento = new HBox();
        filaEvento.setPrefWidth(anchoTotal);
        double alturaFila = filaEvento.getPrefHeight();

        LocalDateTime fechaInicio = evento.getFechaInicio();
        LocalDateTime fechaFin = evento.getFechaFin();
        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd MMM yyyy");

        String duracion = fechaInicio.getHour() + ":"+ fechaInicio.getMinute() + " - " + fechaFin.getHour() + ":"+ fechaFin.getMinute();

        Button botonPropiedades = configurarBotonPropiedades(anchoTotal, alturaFila);

        filaEvento.getChildren().add(nuevaEtiqueta(anchoTotal/5, alturaFila, evento.getTitulo()));
        filaEvento.getChildren().add(nuevaEtiqueta(anchoTotal/5, alturaFila, evento.getDescripcion()));
        filaEvento.getChildren().add(nuevaEtiqueta(anchoTotal/5, alturaFila,"Fin: " +fechaFin.format(formatoDia)));

        Label nuevaEtiqueta = nuevaEtiqueta(anchoTotal/5, alturaFila, "Horario: " + duracion);

        if(evento.duraDiaCompleto()){
            nuevaEtiqueta.setTextFill(Color.PURPLE);
        } else {
            nuevaEtiqueta.setTextFill(Color.HOTPINK);
        }
        filaEvento.getChildren().add(nuevaEtiqueta);
        filaEvento.getChildren().add(botonPropiedades);

        botonPropiedades.setOnAction(actionEvent -> {
            ControladorEvento controladorEvento = new ControladorEvento();
            controladorEvento.verPropiedadesEvento(evento, calendarioUsuario);
        });

        return filaEvento;
    }

    /*
    Devuelve los datos de una tarea para ser mostrados en el calendario
     */
    private HBox filaNuevaTarea(double anchoTotal, Tarea tarea){
        HBox filaTarea = new HBox();
        filaTarea.setPrefWidth(anchoTotal);
        double alturaFila = filaTarea.getPrefHeight();

        LocalDateTime fechaInicio = tarea.getFechaInicio();
        String horaVencimiento = fechaInicio.getHour() + ":"+ fechaInicio.getMinute();
        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd MMM yyyy");

        CheckBox checkTareaCompletada = new CheckBox("Completada");
        checkTareaCompletada.setPrefWidth(anchoTotal/5);
        if(tarea.fueCompletada())
            checkTareaCompletada.setSelected(true);

        Button botonPropiedades = configurarBotonPropiedades(anchoTotal, alturaFila);

        filaTarea.getChildren().add(nuevaEtiqueta(anchoTotal/5, alturaFila, tarea.getTitulo()));
        filaTarea.getChildren().add(nuevaEtiqueta(anchoTotal/5, alturaFila, tarea.getDescripcion()));

        if(tarea.esDeDiaCompleto()){
            Label nuevaEtiqueta = nuevaEtiqueta(anchoTotal/5, alturaFila, "Vence: " + fechaInicio.format(formatoDia));
            nuevaEtiqueta.setTextFill(Color.RED);
            filaTarea.getChildren().add(nuevaEtiqueta);
        } else{
            Label nuevaEtiqueta = nuevaEtiqueta(anchoTotal/5, alturaFila, "Vence: " + horaVencimiento);
            nuevaEtiqueta.setTextFill(Color.GREEN);
            filaTarea.getChildren().add(nuevaEtiqueta);
        }
        filaTarea.getChildren().add(checkTareaCompletada);
        filaTarea.getChildren().add(botonPropiedades);

        botonPropiedades.setOnAction(actionEvent -> {
            ControladorTarea controladorTarea = new ControladorTarea();
            controladorTarea.verPropiedadesTarea(tarea, calendarioUsuario);
        });

        checkTareaCompletada.setOnAction(actionEvent -> tarea.completarTarea());

        return filaTarea;
    }

    /*
    Settea como se muestran todas las actividades en un dia dado.
     */
    private VBox crearFilaVistaActual(double alturaTotal, double anchoTotal, Label fechaFila,
                                       LocalDateTime fechaDia, List<Evento>eventosRangoActual,
                                       List<Tarea>tareasRangoActual){
        VBox filaDiaCalendario = new VBox();

        filaDiaCalendario.setPrefHeight(alturaTotal/cantidadFechasAMostrar);
        filaDiaCalendario.setPrefWidth(anchoTotal);
        filaDiaCalendario.setStyle("-fx-border-color: black;");
        filaDiaCalendario.getChildren().add(fechaFila);

        List<Evento>eventosDelDia = obtenerEventosDelDia(fechaDia, eventosRangoActual);
        for(Evento evento: eventosDelDia){
            HBox nuevoEvento = filaNuevoEvento(anchoTotal, evento);
            filaDiaCalendario.getChildren().add(nuevoEvento);
            filaDiaCalendario.setMaxHeight(nuevoEvento.getPrefHeight() * eventosDelDia.size());
        }

        List<Tarea>tareasDelDia = obtenerTareasDelDia(fechaDia, tareasRangoActual);
        for(Tarea tarea: tareasDelDia){
            HBox nuevaTarea = filaNuevaTarea(anchoTotal, tarea);
            filaDiaCalendario.getChildren().add(nuevaTarea);
            filaDiaCalendario.setMaxHeight(filaDiaCalendario.getPrefHeight() + nuevaTarea.getPrefHeight()* tareasDelDia.size());
        }
        return filaDiaCalendario;
    }

    /*
    settea como se ve el calendario segun una vista actual; con todos los eventos y tareas en el rango mostrado
     */
    public VBox dibujarVista(LocalDateTime fechaVistaActual){

        List<LocalDateTime> fechasRangoVista = new ArrayList<LocalDateTime>();

        if(vistaSeleccionada == "Semanal"){
            cantidadFechasAMostrar = 7;
            fechasRangoVista = obtenerFechasSemana(fechaVistaActual);
        } else if(vistaSeleccionada == "Mensual"){
            cantidadFechasAMostrar = fechaVistaActual.getMonth().length(fechaVistaActual.toLocalDate().isLeapYear());
            fechasRangoVista = obtenerFechasMes(fechaVistaActual);
        } else if(vistaSeleccionada == "Diaria"){
            cantidadFechasAMostrar = 1;
            fechasRangoVista.add(fechaVistaActual);
        }

        List<Evento> eventosRango = calendarioUsuario.obtenerEventosEnRango(fechasRangoVista.get(0),
                                                                            fechasRangoVista.get(fechasRangoVista.size()-1));
        List<Tarea> tareasRango = calendarioUsuario.obtenerTareasEnRango(fechasRangoVista.get(0),
                                                                        fechasRangoVista.get(fechasRangoVista.size()-1));

        double alturaTotal = vistaCalendario.getPrefHeight();
        double anchoTotal = vistaCalendario.getPrefWidth();

        for(int i = 0; i< fechasRangoVista.size() ; i++){
            Label fechaLabel = obtenerFormatoFecha(fechasRangoVista.get(i));
            fechaLabel.setStyle("-fx-font-weight: bold");
            VBox filaDiaCalendario = crearFilaVistaActual(alturaTotal, anchoTotal, fechaLabel, fechasRangoVista.get(i),
                    eventosRango, tareasRango);
            vistaCalendario.getChildren().add(filaDiaCalendario);
        }
        return vistaCalendario;
    }

}
