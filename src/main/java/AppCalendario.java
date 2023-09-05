import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppCalendario extends Application implements Initializable {
    private Calendario calendario;
    private LocalDateTime fechaVistaActual;
    private volatile boolean frenarActualizacionFechaHoy;
    private LocalDateTime fechaDiaHoy;
    @FXML
    private Label fechaHoy;
    @FXML
    private ChoiceBox<String> seleccionVistas;
    @FXML
    private Label tipoVista;
    @FXML
    private Button previousButton;
    @FXML
    private Label fechaRangoActual;
    @FXML
    private Button forwardButton;
    @FXML
    private Button nuevaTarea;
    @FXML
    private Button nuevoEvento;
    @FXML
    private Button reloadButton;
    @FXML
    private VBox vistaCalendario;

    /*
    Metodo que se ejecuta al abrire la ventana
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Persistencia persistencia = new Persistencia();
        this.calendario = new Calendario();

        String currentDirectory = System.getProperty("user.dir");
        String pathArchivo = currentDirectory + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + "calendario.bin";

        this.calendario = persistencia.cargarEstado(calendario, pathArchivo);

        if(seleccionVistas.getItems().size() <3){
            seleccionVistas.getItems().addAll("Diaria", "Semanal", "Mensual");
        }
        seleccionVistas.setValue("Semanal");
        this.fechaVistaActual = LocalDateTime.now();

        frenarActualizacionFechaHoy = false;
        mostrarTiempoActual();

        mostrarFechaRangoActual();
        dibujarVistaCalendario("Semanal");
    }

    /*
    Verifica si la hora actual es igual a la hora que debe sonar la alarma
     */
    private boolean esHoraAlarma(Alarma alarmaActual){
        boolean esHora = false;
        LocalDateTime fecha = LocalDateTime.now();

        long diferenciaHoras = ChronoUnit.HOURS.between(fecha, alarmaActual.horaDispararNotificacion());
        long diferenciaMinutos = ChronoUnit.MINUTES.between(fecha, alarmaActual.horaDispararNotificacion());

        if(diferenciaHoras <= 0 && diferenciaMinutos <= 0){
            esHora = true;
        }

        return esHora;
    }

    /*
    Busca las alarmas que se encuentran actualmente activas y las dispara si es hora.
     */
    private void buscarAlarmas(Calendario calendario){

        List<Alarma> alarmasActivas = calendario.obtenerAlarmasActivas();

        for(Alarma alarma : alarmasActivas){
            if(esHoraAlarma(alarma) && !(alarma.seDisparoAlarma())){
                alarma.setAlarmaDisparada();

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("ALARMA!");
                alerta.setContentText(alarma.getMensajeAlarma());
                Optional<ButtonType> result = alerta.showAndWait();
            }
        }
    }

    /*
    Actualiza la vista para poder mostrar la fecha y hora actual
     */
    private void mostrarTiempoActual(){

         Thread thread = new Thread(() -> {
            DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd MMMM yyyy" + " " + "hh:mm");
            while(!frenarActualizacionFechaHoy){
                try{
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e);
                }
                fechaDiaHoy = LocalDateTime.now();

                Platform.runLater(() -> {
                    fechaHoy.setText(" " + fechaDiaHoy.format(formatoDia));
                    buscarAlarmas(calendario);
                });
            }
        });
        thread.start();

    }

    /*
    Muestra las actividades cuyas fechas estan dentro del rango de vista actual
     */
    private void dibujarVistaCalendario(String rangoVistaActual){
        VistaCalendario vistaActual = new VistaCalendario(calendario, vistaCalendario, rangoVistaActual);
        vistaCalendario.getChildren().clear();
        vistaCalendario.setStyle("-fx-border-color: black;");
        vistaCalendario = vistaActual.dibujarVista(fechaVistaActual);
    }

    /*
    muestra cuales son las fechas que se ven en la vista actual
     */
    private void mostrarFechaRangoActual(){
        if(seleccionVistas.getValue() == "Diaria" || seleccionVistas.getValue() == null){
            DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            fechaRangoActual.setText(" " + fechaVistaActual.format(formatoDia));
            dibujarVistaCalendario("Diaria");
        } else if(seleccionVistas.getValue() == "Semanal"){
            DateTimeFormatter formatoSemana = DateTimeFormatter.ofPattern("dd MMM yyyy");
            fechaRangoActual.setText(" " + fechaVistaActual.format(formatoSemana) + " - "
                                            + (fechaVistaActual.plusDays(7)).format(formatoSemana));
            dibujarVistaCalendario("Semanal");
        } else{
            DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMM yyyy");
            fechaRangoActual.setText(" " + fechaVistaActual.format(formatoMes));
            dibujarVistaCalendario("Mensual");
        }
    }
    /*
    Mueve el Rango de fechas que se esta mirando
     */
    public void cambiarFechaActual(int cantidadTemporalAMover){
        if(seleccionVistas.getValue() == "Mensual"){
            fechaVistaActual = fechaVistaActual.plusMonths(cantidadTemporalAMover);
        } else{
            fechaVistaActual = fechaVistaActual.plusDays(cantidadTemporalAMover);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ventana.fxml"));
        loader.setController(this);
        VBox contenedor = loader.load();

        var scene = new Scene(contenedor);

        seleccionVistas.setOnAction(actionEvent -> mostrarFechaRangoActual());

        previousButton.setOnAction(actionEvent -> {
            if(seleccionVistas.getValue() == "Semanal"){
                cambiarFechaActual(-6);
            }
            cambiarFechaActual(-1);
            mostrarFechaRangoActual();
        });

        forwardButton.setOnAction(actionEvent -> {
           if(seleccionVistas.getValue() == "Semanal"){
                cambiarFechaActual(6);
           }
           cambiarFechaActual(1);
           mostrarFechaRangoActual();
        });

        nuevoEvento.setOnAction(actionEvent -> {
            ControladorEvento controladorEvento = new ControladorEvento();
            controladorEvento.crearEventoNuevo(this.calendario);
        });

        nuevaTarea.setOnAction(actionEvent ->{
            ControladorTarea controladorTarea = new ControladorTarea();
            controladorTarea.crearTareaNueva(this.calendario);
        });

        reloadButton.setOnAction(actionEvent -> mostrarFechaRangoActual());

        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(actionEvent ->{
            System.out.println("Se esta cerrando la aplicacion");
            frenarActualizacionFechaHoy = true;

            Persistencia persistencia = new Persistencia();
            String currentDirectory = System.getProperty("user.dir");
            String pathArchivo = currentDirectory + System.getProperty("file.separator") + "src"
                                            + System.getProperty("file.separator") + "main"
                                            + System.getProperty("file.separator") + "resources"
                                            + System.getProperty("file.separator") + "calendario.bin";
            persistencia.guardarEstado(this.calendario, pathArchivo);

        });
    }
}
