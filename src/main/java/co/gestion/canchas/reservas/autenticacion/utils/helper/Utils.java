package co.gestion.canchas.reservas.autenticacion.utils.helper;

public class Utils {

    public static String cleanString(String text){
        return text == null || text.isEmpty() ? "" : text;
    }

}
