package Utils;

import PageModelNew.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsCheck {
    // CHECK
    public static String check200Caracteres(String text) {
        int total = 0;
        for (char e : text.toCharArray()) {
            if (!Character.isWhitespace(e)) total++;
        }
        if (total >= 200) return "\u001B[31mError: Número máximo excedido.\u001B[0m";
        return "";
    }

    public static String checkIsHashTag(String hashtag) {
        if (hashtag.contains(" ")) return "";
        if (!hashtag.startsWith("#")) hashtag = "#"+hashtag;
        return hashtag;
    }

    public static String checkHashtagText(String text) {
        for (String e : text.split(" ")) {
            if (e.startsWith("#")) {
                return e;
            }
        }
        return "";
    }


    public static String checkInt(String num) {
        for (char e : num.toCharArray()) {
            if (!Character.isDigit(e)) {
                return "\u001B[31mError: Caracter invalido.\u001B[0m";
            }
        }
        return "";
    }

    public static String checkEmail(String email) {
        if (email.length() > 50) {
            return "";
        }

        String[] dominiosPermitidos = {"@gmail.com", "@hotmail.com", "@yahoo.com", "@outlook.com", "@protonmail.com", "@icloud.com"};

        boolean esValido = false;
        for (String dominio : dominiosPermitidos) {
            if (email.endsWith(dominio)) {esValido = true; break;}
        }

        return esValido ? email : "";
    }

    public static String checkLink(String link) {
        String regex = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/[a-zA-Z0-9#?&%._=-]*)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);

        return matcher.matches() ? link : "";
    }

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

    public static String inspectContrasena(Usuario usuario, String tempCont) {
        if(!tempCont.equals(usuario.getContrasena())) {
            return "";
        }
        return tempCont;
    }
}
