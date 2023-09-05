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
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorEvento implements Initializable {
    private boolean duracionDiaCompleto;
    private Repeticion repeticionEvento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private List<Alarma> alarmasEvento;

    @FXML
    private Button nuevaAlarmaEvento;
    @FXML
    private Button saveNewEventButton;
    @FXML
    private Button eliminarEventoButton;
    @FXML
    private Label mensajeCambiosGuardados;
    @FXML
    private DatePicker fechaInicioEvento;
    @FXML
    private DatePicker fechaFinEvento;
    @FXML
    private TextField tituloEvento;
    @FXML
    private TextArea descripcionEvento;
    @FXML
    private ToggleGroup duracionEvento;
    @FXML
    private RadioButton eventoDiaCompleto;
    @FXML
    private RadioButton seleccionarHorario;
    @FXML
    private TextField horaInicio;
    @FXML
    private TextField horaFin;
    @FXML
    private TextField minutosInicio;
    @FXML
    private TextField minutosFin;
    @FXML
    private Button closeGuardarHorario;
    @FXML
    private RadioButton repeticionNula;
    @FXML
    private RadioButton repeticionDiaria;
    @FXML
    private TextField intervaloRepeticion;
    @FXML
    private Label label1IntervaloRepe;
    @FXML
    private Label label2IntervaloRepe;

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
    Verifica que se hayan ingresado valores validos y guarda el evento en el calendario
     */
    private void guardarCambiosEvento(Evento evento, Calendario calendario){

        evento.setTitulo(tituloEvento.getText());
        evento.setDescripcion(descripcionEvento.getText());

        if(guardarDuracionEvento(evento) && guardarRepeticionEvento(evento)){
            for(Alarma alarma : alarmasEvento){
                evento.setAlarma(alarma);
            }
            calendario.guardarCambiosEvento(evento);
            mensajeCambiosGuardados.setVisible(true);
        }
    }

    /*
    Abre una ventana ventanaHorarioEvento.fxml y permite que el usuario ingrese el horario de su evento.
    se cierra unicamente cuando los datos ingresados sean validos.
     */
    private void abrirVentanaDuracionEvento() throws Exception {
        Stage escenarioHorario = new Stage();
        FXMLLoader loaderHorarioView = new FXMLLoader(getClass().getResource("ventanaHorarioEvento.fxml"));
        loaderHorarioView.setController(this);
        VBox contenedor = loaderHorarioView.load();

        var scene = new Scene(contenedor);

        escenarioHorario.setScene(scene);
        escenarioHorario.show();

        closeGuardarHorario.setOnAction(actionEvent -> {
            Verificador verificador = new Verificador();
            if(verificador.esHorarioValido(horaInicio.getText(), horaFin.getText(),
                                            minutosInicio.getText(), minutosFin.getText())){
                Stage escenario = (Stage) closeGuardarHorario.getScene().getWindow();
                escenario.close();
            } else {
                Alert alerta = verificador.mostrarAlerta("Por favor ingrese un horario valido");
            }
        });
    }

    /*
    Le indica al modelo que guarde la duracion del evento como de dia completo
     */
    private void guardarDuracionDiaCompleto(Evento evento){
        fechaInicio = fechaInicioEvento.getValue().atStartOfDay();
        fechaFin = fechaFinEvento.getValue().atTime(23, 59, 00);
        evento.establecerHorarioEvento(fechaInicio, fechaFin);
    }

    /*
    Le indica al modelo que guarde la duracion del evento en un horario determinado
     */
    private void guardarDuracionTemporizada(Evento evento){
        int hInicio = Integer.parseInt(horaInicio.getText());
        int minInicio = Integer.parseInt(minutosInicio.getText());
        int hFin = Integer.parseInt(horaFin.getText());
        int minFin = Integer.parseInt(minutosFin.getText());
        fechaInicio = fechaInicioEvento.getValue().atTime(hInicio, minInicio, 0);
        fechaFin = fechaFinEvento.getValue().atTime(hFin, minFin, 0);
        evento.establecerHorarioEvento(fechaInicio, fechaFin);
    }

    /*
    Guarda las fecha de inicio y fin del evento, junto con su horario.
    Se verifica que se hayan ingresado fechas y que se haya seleccionado un horario antes de
    guardar el evento. Caso contrario, se lanza una alerta.
     */
    private boolean guardarDuracionEvento(Evento evento){
        boolean sePudoGuardarElHorario = false;
        Verificador verificador = new Verificador();

        if(!(verificador.esFechaValida(fechaInicioEvento, fechaFinEvento))){
             verificador.mostrarAlerta("Por favor ingrese fechas Validas");
        } else{
            if(!(eventoDiaCompleto.isSelected()) && !(seleccionarHorario.isSelected())){
                verificador.mostrarAlerta("Por favor seleccione un horario para el evento.");
            } else if(eventoDiaCompleto.isSelected()) {
                guardarDuracionDiaCompleto(evento);
                sePudoGuardarElHorario = true;
            }
            else if(seleccionarHorario.isSelected()){
                guardarDuracionTemporizada(evento);
                sePudoGuardarElHorario = true;
            }
        }

        return sePudoGuardarElHorario;
    }

    /*
    Segun lo ingresado por el usuario le inidca al controlador el tipo de
    repeticion a  asignarle a un evento
     */
    private boolean guardarRepeticionEvento(Evento evento){
        boolean seGuardaronLasRepeticiones = false;
        Verificador verificador = new Verificador();

        if(!(repeticionNula.isSelected()) && !(repeticionDiaria.isSelected())) {
            verificador.mostrarAlerta("Por favor indique si el evento se repite o no.");

        } else if(repeticionNula.isSelected()){
            evento.setRepeticion(null);
            seGuardaronLasRepeticiones = true;
        } else if(repeticionDiaria.isSelected()) {
            if (verificador.esIntervaloValido(intervaloRepeticion.getText())) {
                int intervaloRepe = Integer.parseInt(intervaloRepeticion.getText());
                Repeticion repeticionDiaria = new Repeticion(new FrecuenciaDiaria(intervaloRepe), TipoFinFrecuencia.EN_FECHA,
                        0, fechaFin);
                evento.setRepeticion(repeticionDiaria);
                seGuardaronLasRepeticiones = true;
            } else {
                verificador.mostrarAlerta("Por favor ingrese un intervalo de repeticion valido " +
                        "(valores enteros mayores a 0)");
            }
        }
        return seGuardaronLasRepeticiones;
    }

    /*
    Segun lo ingresado por el usuario guarda una alarma para el evento
     */
    private void guardarAlarma(Evento evento){
        LocalDateTime fechaActividad = evento.getFechaInicio();
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
                "Notificacion sobre la actividad: " + evento.getTitulo() + " el: " + fechaActividad.format(formatoDia)));

        alarmasEvento.add(alarma);
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
    private void abrirVentanaAlarma(Evento evento) throws Exception {
        Stage escenarioHorario = new Stage();
        FXMLLoader loaderHorarioView = new FXMLLoader(getClass().getResource("ventanaConfigAlarma.fxml"));
        loaderHorarioView.setController(this);
        VBox contenedor = loaderHorarioView.load();

        var scene = new Scene(contenedor);

        escenarioHorario.setScene(scene);
        escenarioHorario.show();

        guardarAlarmaButton.setOnAction(actionEvent ->{
            if(seVerificaGuardadoAlarma()){
                guardarAlarma(evento);
                Stage escenario = (Stage) guardarAlarmaButton.getScene().getWindow();
                escenario.close();
            }
        });

    }

    /*
    contiene las respuestas de la ventanaEvento que se dan cuando el usuario ingresa un dato/aprieta un boton
     */
    public void accionarBotones(Evento evento, Calendario calendario){
        eventoDiaCompleto.setOnAction(actionEvent ->{
            duracionDiaCompleto = true;
        });

        seleccionarHorario.setOnAction(actionEvent ->{
            duracionDiaCompleto = false;
            try {
                abrirVentanaDuracionEvento();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        repeticionNula.setOnAction(actionEvent -> {
            repeticionEvento = null;
        });

        repeticionNula.setOnAction(actionEvent ->{
            repeticionEvento = null;
            intervaloRepeticion.setVisible(false); //si se mete en un Toggle Group puedo usar isSelected
            label1IntervaloRepe.setVisible(false);
            label2IntervaloRepe.setVisible(false);
            repeticionNula.isSelected();
        });

        repeticionDiaria.setOnAction(actionEvent -> {
            intervaloRepeticion.setVisible(true);
            label1IntervaloRepe.setVisible(true);
            label2IntervaloRepe.setVisible(true);
            repeticionDiaria.isSelected();
        });

        nuevaAlarmaEvento.setOnAction(actionEvent -> {
            try {
                abrirVentanaAlarma(evento);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        saveNewEventButton.setOnAction(actionEvent -> {
            guardarCambiosEvento(evento, calendario);
        });

        eliminarEventoButton.setOnAction(actionEvent ->{
            calendario.eliminarEvento(evento.getIndice());
            mensajeCambiosGuardados.setText("Se ha eliminado el evento");
            mensajeCambiosGuardados.setVisible(true);
        });
    }

    /*
    Se abre una ventana con los datos del evento para que el usuario complete.
     */
    private void abrirVentanaEvento(Stage stage, Evento evento, Calendario calendario) throws Exception{
        FXMLLoader loaderEventoView = new FXMLLoader(getClass().getResource("ventanaEvento.fxml"));
        loaderEventoView.setController(this);
        VBox contenedor = loaderEventoView.load();

        var scene = new Scene(contenedor);

        eliminarEventoButton.setVisible(false);
        mensajeCambiosGuardados.setVisible(false);

        accionarBotones(evento, calendario);

        stage.setScene(scene);
        stage.show();


    }
    /*
    Se crea un nuevo evento y se permite que el usuario elija sus caracteristicas
     */
    public void crearEventoNuevo(Calendario calendario){
        int idEvento = calendario.crearEvento(LocalDateTime.now());
        Evento eventoNuevo = calendario.getEventoConId(idEvento);
        try {
            Stage escenarioEvento = new Stage();
            abrirVentanaEvento(escenarioEvento, eventoNuevo, calendario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*
    Carga los datos del Evento en una ventana. Permite que el usuario haga
    modificaciones y elimine el evento si lo desea.
     */
    private void expandirDatosEvento(Stage escenarioEvento, Evento evento, Calendario calendario) throws Exception{
        FXMLLoader loaderEventoView = new FXMLLoader(getClass().getResource("ventanaEvento.fxml"));
        loaderEventoView.setController(this);
        VBox contenedor = loaderEventoView.load();

        var scene = new Scene(contenedor);

        mensajeCambiosGuardados.setVisible(false);
        eliminarEventoButton.setVisible(true);

        tituloEvento.setText(evento.getTitulo());
        descripcionEvento.setText(evento.getDescripcion());
        fechaInicioEvento.setValue(evento.getFechaInicio().toLocalDate());
        fechaFinEvento.setValue(evento.getFechaFin().toLocalDate());

        if(!evento.seReptie()){
            repeticionNula.setSelected(true);
        } else {
            repeticionDiaria.setSelected(true);
            label1IntervaloRepe.setVisible(true);
            intervaloRepeticion.setVisible(true);
            intervaloRepeticion.setText(Integer.toString(evento.getIntervaloRepeticion()));
            label2IntervaloRepe.setVisible(true);
        }

        if(evento.duraDiaCompleto()){
            eventoDiaCompleto.setSelected(true);
        }
        else{
            seleccionarHorario.setSelected(true);
            abrirVentanaDuracionEvento();
            horaInicio.setText(Integer.toString(evento.getFechaInicio().getHour()));
            minutosInicio.setText(Integer.toString(evento.getFechaInicio().getMinute()));
            horaFin.setText(Integer.toString(evento.getFechaFin().getHour()));
            minutosFin.setText(Integer.toString(evento.getFechaFin().getMinute()));
        }

        accionarBotones(evento, calendario);

        escenarioEvento.setScene(scene);
        escenarioEvento.show();
    }

    /*
    Abre una ventana con los datos del evento para que el usuario vea los mismos en detalle.
     */
    public void verPropiedadesEvento(Evento evento, Calendario calendario){
        Stage escenarioEvento = new Stage();
        try {
            expandirDatosEvento(escenarioEvento, evento, calendario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intervaloRepeticion.setVisible(false);
        label1IntervaloRepe.setVisible(false);
        label2IntervaloRepe.setVisible(false);
        alarmasEvento = new ArrayList<Alarma>();
    }
}
