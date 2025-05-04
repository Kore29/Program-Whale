package Utils;

import PageModelNew.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsCheck {
    /**
     * Comprueba que un texto no exceda los 200 caracteres (sin contar espacios).
     * @param text el texto a evaluar.
     * @return mensaje de error si se superan los 200 caracteres, o cadena vacía si está permitido.
     */
    public static String check200Caracteres(String text) {
        int total = 0;
        for (char e : text.toCharArray()) {
            if (!Character.isWhitespace(e)) total++;
        }
        if (total >= 200) return "\u001B[31mError: Número máximo excedido.\u001B[0m";
        return "";
    }

    /**
     * Verifica que el texto sea un hashtag válido (sin espacios y con '#').
     * @param hashtag el texto a validar.
     * @return hashtag con '#' al principio si es válido, o cadena vacía si contiene espacios.
     */
    public static String checkIsHashTag(String hashtag) {
        if (hashtag.contains(" ")) return "";
        if (!hashtag.startsWith("#")) hashtag = "#"+hashtag;
        return hashtag;
    }

    /**
     * Extrae el primer hashtag de un texto.
     * @param text el texto que puede contener hashtags.
     * @return el primer hashtag encontrado, o cadena vacía si no hay ninguno.
     */
    public static String checkHashtagText(String text) {
        for (String e : text.split(" ")) {
            if (e.startsWith("#")) {
                return e;
            }
        }
        return "";
    }

    /**
     * Verifica que una cadena solo contenga números.
     * @param num la cadena a validar.
     * @return mensaje de error si hay caracteres no numéricos, o cadena vacía si es un número válido.
     */
    public static String checkInt(String num) {
        for (char e : num.toCharArray()) {
            if (!Character.isDigit(e)) {
                return "\u001B[31mError: Caracter invalido.\u001B[0m";
            }
        }
        return "";
    }


    /**
     * Verifica si un email es válido según ciertos dominios y longitud.
     * @param email el email a comprobar.
     * @return el email si es válido, o cadena vacía si no cumple los criterios.
     */
    public static String checkEmail(String email) {
        if (email.length() > 50) {
            return "";
        }

        String[] dominiosPermitidos = {"@gmail.com", "@hotmail.com", "@yahoo.com", "@outlook.com", "@protonmail.com", "@icloud.com", "@example.com"};

        boolean esValido = false;
        for (String dominio : dominiosPermitidos) {
            if (email.endsWith(dominio)) {esValido = true; break;}
        }

        return esValido ? email : "";
    }

    /**
     * Comprueba si un enlace tiene un formato válido mediante expresión regular.
     * @param link el enlace a validar.
     * @return el enlace si es válido, o cadena vacía si no lo es.
     */
    public static String checkLink(String link) {
        String regex = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/[a-zA-Z0-9#?&%._=-]*)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);

        return matcher.matches() ? link : "";
    }

    /**
     * Valida el nombre del usuario según longitud, contenido y formato.
     * @param nombre el nombre a comprobar.
     * @return el nombre si es válido (letras minúsculas y sin símbolos), o cadena vacía si no cumple los criterios.
     */
    public static String checkNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "";
        }

        if (nombre.length() > 30) {
            return "";
        }

        if (!nombre.matches("^[a-z0-9áéíóúüñ]+$")) {
            return "";
        }

        return nombre;
    }

    /**
     * Verifica si la contraseña cumple con criterios de seguridad (mínimo 8 caracteres, mayúsculas, minúsculas, números y símbolos).
     * @param tempCont la contraseña a comprobar.
     * @return la contraseña si es segura, o cadena vacía si no cumple los criterios.
     */
    public static String checkContrasena(String tempCont) {
        int critCump = 0;

        if (tempCont.length() >= 8) critCump++;

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : tempCont.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            else if (Character.isLowerCase(c)) tieneMinuscula = true;
            else if (Character.isDigit(c)) tieneNumero = true;
            else if ("!@#$%^&*()-_=+[]{};:'\",.<>?/\\|`~".contains(String.valueOf(c))) tieneEspecial = true;
        }

        if (tieneMayuscula) critCump++;
        if (tieneMinuscula) critCump++;
        if (tieneNumero) critCump++;
        if (tieneEspecial) critCump++;

        return (critCump >= 4) ? tempCont : "";
    }

    /**
     * Verifica si la contraseña introducida coincide con la del usuario.
     * @param usuario el usuario actual.
     * @param tempCont la contraseña proporcionada.
     * @return la contraseña si coincide, o cadena vacía si es incorrecta.
     */
    public static String inspectContrasena(Usuario usuario, String tempCont) {
        if(!tempCont.equals(usuario.getContrasena())) {
            return "";
        }
        return tempCont;
    }
}
