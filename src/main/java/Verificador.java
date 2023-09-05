import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import java.util.Optional;

public class Verificador {


    private boolean horaEsValida(String horaIngresada){
        boolean horaEsValida = true;

        if(horaIngresada.isEmpty() || (Integer.parseInt(horaIngresada)) < 0 ||
                (Integer.parseInt(horaIngresada)) > 23){
            horaEsValida = false;
        }
        return horaEsValida;
    }

    private boolean minutosSonValidos(String minutosIngresados){
        boolean minutosValidos = true;

        if(minutosIngresados.isEmpty() || (Integer.parseInt(minutosIngresados)) < 0 ||
                (Integer.parseInt(minutosIngresados)) > 59){
            minutosValidos = false;
        }
        return minutosValidos;
    }
    public boolean esHorarioValido(String horaInicio, String horaFin, String minutosInicio, String minutosFin){
        boolean horarioValido = false;
        if(horaEsValida(horaInicio) && horaEsValida(horaFin)){
            if(minutosSonValidos(minutosInicio) && minutosSonValidos(minutosFin)){
                horarioValido = true;
            }
        }
        return horarioValido;
    }

    /*
    Determina si fechas ingresadas por el usario, para eventos o tareas, son validas
     */
    public boolean esFechaValida(DatePicker fechaInicioIngresada, DatePicker fechaFinIngresada){
        boolean fechasValidas = false;

        if(fechaInicioIngresada.getValue() != null && fechaFinIngresada.getValue() != null){
            if(!(fechaFinIngresada.getValue().isBefore(fechaInicioIngresada.getValue()))){
                fechasValidas = true;
            }
        }
        return fechasValidas;
    }

    /*
    determina si un valor ingresado como intervalo es valido.
     */
    public boolean esIntervaloValido(String intervaloIngresado){
        boolean numeroEsValido = false;
        if(!(intervaloIngresado.isEmpty()) && (Integer.parseInt(intervaloIngresado)) > 0)
            numeroEsValido = true;
        return numeroEsValido;
    }

    /*
    Muestra una alerta con un mensaje dado
     */
    public Alert mostrarAlerta(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Cuidado!");
        alerta.setContentText(mensaje);
        Optional<ButtonType> result = alerta.showAndWait();

        return alerta;
    }
}
