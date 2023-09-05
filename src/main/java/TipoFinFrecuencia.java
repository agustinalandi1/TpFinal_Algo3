import java.io.Serializable;

public enum TipoFinFrecuencia implements Serializable {
    INFINITA,
    POST_OCURRRENCIAS, // Termina cuando terminan la cant de ocurrencias instanciadas
    EN_FECHA
}
