import java.io.*;

public class Persistencia implements Serializable {

    /*deserializacion*/
    public Calendario cargarEstado(Calendario calendario, String archivo){
        try{
            File file = new File(archivo);
            if(file.exists()){
                FileInputStream fileInputStream = new FileInputStream(file);
                if(fileInputStream.available() != 0) {
                    ObjectInputStream objetoSerializado = new ObjectInputStream(fileInputStream);
                    Object calendarioSerializado = objetoSerializado.readObject();
                    calendario = (Calendario) calendarioSerializado;
                    objetoSerializado.close();
                    return calendario;
                }else{
                    fileInputStream.close();
                    return calendario;
                }
            }
            else {
                System.out.println("Archivo inexistente");
                return null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    /*serializacion*/
    public void guardarEstado(Calendario calendario, String archivo){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objetoASerializar = new ObjectOutputStream(fileOutputStream);
            objetoASerializar.writeObject(calendario);
            objetoASerializar.flush();
            objetoASerializar.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
