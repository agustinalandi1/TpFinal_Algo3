import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorTarea implements Initializable {

    private boolean duracionDiaCompleto;
    private List<Alarma> alarmasTarea;

    @FXML
    private Button nuevaAlarmaTarea;
    @FXML
    private TextField tituloTarea;
    @FXML
    private TextArea descripcionTarea;
    @FXML
    private DatePicker fechaTarea;
    @FXML
    private RadioButton tareaDiaCompleto;
    @FXML
    private RadioButton seleccionarHoraVencimiento;
    @FXML
    private Button saveNewTaskButton;
    @FXML
    private Button eliminarTareaButton;
    @FXML
    private TextField horaVencimientoTarea;
    @FXML
    private TextField minutosVencimientoTarea;
    @FXML
    private Button closeGuardarHorarioTarea;
    @FXML
    private Label mensajeCambiosGuardados;

    @FXML
    private Button guardarAlarmaButton;
    @FXML
    private RadioButton alarmaEnMomentoAct;
    @FXML
    private RadioButton alarmaMinAntesAct;
    @FXML
    private RadioButton alarmaHrsAntesAct;
    @FXML
    private RadioButton alarmaDiasAntesAct;
    @FXML
    private TextField intervaloTiempoAlarma;

    /*
    Se guardan todos los cambios efecutados a la tarea.
     */
    private void guardarCambiosTarea(Tarea tarea, Calendario calendario){
        tarea.setTitulo(tituloTarea.getText());
        tarea.setDescripcion(descripcionTarea.getText());
        if(guardarHorarioTarea(tarea)){
            for(Alarma alarma : alarmasTarea){
                tarea.setAlarma(alarma);
            }
            calendario.guardarCambiosTarea(tarea);
            mensajeCambiosGuardados.setVisible(true);
        }
    }

    /*
    Con los datos ingresados por el usuario le indica al controlador la hora de vencimiento a guardar.
     */
    private void guardarHorarioVencimiento(Tarea tarea){
        int hVencimiento = Integer.parseInt(horaVencimientoTarea.getText());
        int minVencimiento = Integer.parseInt(minutosVencimientoTarea.getText());
        tarea.setVencimiento(fechaTarea.getValue().atTime(hVencimiento, minVencimiento, 0));
    }

    /*
    determina si un horario ingresado por el usuario es valido para ser asignado al
    horario de vencimiento de la tarea
     */
    private boolean guardarHorarioTarea(Tarea tarea){
        boolean seGuardoElHorario = false;
        Verificador verificador = new Verificador();

        if(!(verificador.esFechaValida(fechaTarea, fechaTarea)))
            verificador.mostrarAlerta("Por favor ingrese una fecha para la tarea.");
        else if(!(tareaDiaCompleto.isSelected()) && !(seleccionarHoraVencimiento.isSelected())){
            verificador.mostrarAlerta("Por favor seleccione un horario para la tarea.");
        } else if(tareaDiaCompleto.isSelected()){
            tarea.setDiaCompleto(fechaTarea.getValue().atStartOfDay());
            seGuardoElHorario = true;
        } else if(seleccionarHoraVencimiento.isSelected()){
            guardarHorarioVencimiento(tarea);
            seGuardoElHorario = true;
        }

        return seGuardoElHorario;
    }
    /*
    Abre una ventana que permite elegir el horario de vencimiento de una tarea
     */
    private void abrirVentanaDuracionTarea() throws Exception{
        Stage escenarioHorarioTarea = new Stage();
        FXMLLoader loaderHorarioView = new FXMLLoader(getClass().getResource("ventanaHorarioTarea.fxml"));
        loaderHorarioView.setController(this);
        VBox contenedor = loaderHorarioView.load();

        var scene = new Scene(contenedor);

        escenarioHorarioTarea.setScene(scene);
        escenarioHorarioTarea.show();

        closeGuardarHorarioTarea.setOnAction(actionEvent -> {
            Verificador verificador = new Verificador();
            if(verificador.esHorarioValido(horaVencimientoTarea.getText(), "0",
                                            minutosVencimientoTarea.getText(), "0")){
                Stage escenario = (Stage) closeGuardarHorarioTarea.getScene().getWindow();
                escenario.close();
            } else{
                Alert alerta = verificador.mostrarAlerta("Por favor ingrese un horario valido");
            }
        });
    }

    /*
    Segun lo ingresado por el usuario guarda una alarma para el evento
     */
    private void guardarAlarma(Tarea tarea){
        LocalDateTime fechaActividad = tarea.getFechaInicio();
        LocalDateTime horaAlarma = fechaActividad;
        if(alarmaEnMomentoAct.isSelected())
            horaAlarma = fechaActividad;
        else if(alarmaMinAntesAct.isSelected())
            horaAlarma = fechaActividad.minusMinutes(Integer.parseInt(intervaloTiempoAlarma.getText()));
        else if(alarmaHrsAntesAct.isSelected())
            horaAlarma = fechaActividad.minusHours(Integer.parseInt(intervaloTiempoAlarma.getText()));
        else if(alarmaDiasAntesAct.isSelected())
            horaAlarma = fechaActividad.minusDays(Integer.parseInt(intervaloTiempoAlarma.getText()));

        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        Alarma alarma = new Alarma(new DisparoAlarmaConIntervalo(horaAlarma), new NotificarMensaje(horaAlarma,
                "Notificacion sobre la actividad: " + tarea.getTitulo() + " el: " + fechaActividad.format(formatoDia)));

        alarmasTarea.add(alarma);
    }

    /*
    Verifica que se haya seleccionado cuando se quiere disparar la alarma
     */
    private boolean seSeleccionoIntervaloAlarma(){
        boolean seSeleccionoIntervalo = true;
        if(!alarmaEnMomentoAct.isSelected() && !alarmaMinAntesAct.isSelected() &&
                !alarmaHrsAntesAct.isSelected() && !alarmaDiasAntesAct.isSelected()){
            seSeleccionoIntervalo = false;
        }
        return seSeleccionoIntervalo;
    }

    /*
    Determina si una alarma es valida para ser guardada o no.
     */
    private boolean seVerificaGuardadoAlarma(){
        boolean seGuardaCorrectamente = true;
        Verificador verificador = new Verificador();
        if(!seSeleccionoIntervaloAlarma()) {
            verificador.mostrarAlerta("Por favor ingrese cuando quiere que suene la alarma");
            seGuardaCorrectamente = false;
        }else if(!alarmaEnMomentoAct.isSelected()){
            if(!(verificador.esIntervaloValido(intervaloTiempoAlarma.getText()))){
                verificador.mostrarAlerta("Por favor ingrese el intervalo de tiempo");
                seGuardaCorrectamente = false;
            }
        }

        return seGuardaCorrectamente;
    }

    /*
    Abre una ventana que permite seleccionar las especificaciones de una nueva alarma
     */
    private void abrirVentanaAlarma(Tarea tarea) throws Exception {
        Stage escenarioHorario = new Stage();
        FXMLLoader loaderHorarioView = new FXMLLoader(getClass().getResource("ventanaConfigAlarma.fxml"));
        loaderHorarioView.setController(this);
        VBox contenedor = loaderHorarioView.load();

        var scene = new Scene(contenedor);

        escenarioHorario.setScene(scene);
        escenarioHorario.show();

        guardarAlarmaButton.setOnAction(actionEvent ->{
            if(seVerificaGuardadoAlarma()){
                guardarAlarma(tarea);
                Stage escenario = (Stage) guardarAlarmaButton.getScene().getWindow();
                escenario.close();
            }
        });
    }

    public void completarTarea(Tarea tarea){
        tarea.completarTarea();
    }

    /*
    Maneja la respuestas del programa cuando se da un evento (el usuario interactuo con la ventana)
     */
    private void accionarBotones(Tarea tarea, Calendario calendario){
        tareaDiaCompleto.setOnAction(actionEvent -> {
            duracionDiaCompleto = true;
        });

        seleccionarHoraVencimiento.setOnAction(actionEvent -> {
            duracionDiaCompleto = false;
            try {
                abrirVentanaDuracionTarea();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        nuevaAlarmaTarea.setOnAction(actionEvent -> {
            try {
                abrirVentanaAlarma(tarea);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        saveNewTaskButton.setOnAction(actionEvent -> {
            guardarCambiosTarea(tarea, calendario);
        });

        eliminarTareaButton.setOnAction(actionEvent ->{
            calendario.eliminarTarea(tarea.getIndice());
            mensajeCambiosGuardados.setText("Se ha eliminado la tarea");
            mensajeCambiosGuardados.setVisible(true);
        });
    }
    /*
    Abre una ventana que detalla las especificaciones de una tarea.
     */
    private void abrirVentanaTarea(Stage stage, Tarea tarea, Calendario calendario) throws Exception{
        FXMLLoader loaderEventoView = new FXMLLoader(getClass().getResource("ventanaTarea.fxml"));
        loaderEventoView.setController(this);
        VBox contenedor = loaderEventoView.load();

        var scene = new Scene(contenedor);

        eliminarTareaButton.setVisible(false);

        accionarBotones(tarea, calendario);

        stage.setScene(scene);
        stage.show();

    }

    /*
    Crea una nueva instancia de tarea y permite que el usuario le asigne sus especificaciones
     */
    public void crearTareaNueva(Calendario calendario){
        int idTarea = calendario.crearTarea(LocalDateTime.now());
        Tarea tareaNueva = calendario.getTareaConId(idTarea);
        try {
            Stage escenarioEvento = new Stage();
            abrirVentanaTarea(escenarioEvento, tareaNueva, calendario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Carga los datos de una tarea en una ventana, para que el usuario pueda editarlos o borrar la misma.
     */
    private void expandirDatosTarea(Stage escenarioTarea, Tarea tarea, Calendario calendario) throws Exception {
        FXMLLoader loaderEventoView = new FXMLLoader(getClass().getResource("ventanaTarea.fxml"));
        loaderEventoView.setController(this);
        VBox contenedor = loaderEventoView.load();

        var scene = new Scene(contenedor);

        eliminarTareaButton.setVisible(true);

        tituloTarea.setText(tarea.getTitulo());
        descripcionTarea.setText(tarea.getDescripcion());
        fechaTarea.setValue(tarea.getFechaInicio().toLocalDate());

        if(tarea.esDeDiaCompleto()){
            tareaDiaCompleto.setSelected(true);
            duracionDiaCompleto = true;
        }else{
            seleccionarHoraVencimiento.setSelected(true);
            abrirVentanaDuracionTarea();
            horaVencimientoTarea.setText(Integer.toString(tarea.getVencimiento().getHour()));
            minutosVencimientoTarea.setText(Integer.toString(tarea.getVencimiento().getMinute()));
        }

        accionarBotones(tarea, calendario);

        escenarioTarea.setScene(scene);
        escenarioTarea.show();
    }
    /*
    Abre una ventana para ver los detalles especificos de una tarea.
     */
    public void verPropiedadesTarea(Tarea tarea, Calendario calendario){
        Stage escenarioTarea = new Stage();
        try {
            expandirDatosTarea(escenarioTarea, tarea, calendario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Metodo que se ejecuta cuando se abre de la ventana e inicializa ciertos campos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mensajeCambiosGuardados.setVisible(false);
        alarmasTarea = new ArrayList<Alarma>();
    }
}
