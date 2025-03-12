package Utils;

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

        if (nombre.length() > 30) {
            return "";
        }

        if (!nombre.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            return "";
        }

        StringBuilder nombreFormateado = new StringBuilder();
        for (String palabra : nombre.split(" ")) {
            if (!palabra.isEmpty()) {
                nombreFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return nombreFormateado.toString().trim();
    }

    public static String checkContrasena(String contrasena) {

        if (contrasena.length() > 18) {
            return "";
        }
        if (contrasena.contains(" ")) {
            return "";
        }

        boolean letr = contrasena.matches(".*[a-zA-Z].*");
        boolean nume = contrasena.matches(".*\\d.*");
        boolean tn8Cara = contrasena.length() > 8;

        if (letr && nume && tn8Cara) {
            return "La contraseña es muy segura.";
        } else if (nume) {
            return "La contraseña es medianamente segura.";
        } else if (letr) {
            return "La contraseña es poco segura.";
        } else {
            return "La contraseña no cumple con los requisitos minimos.";
        }
    }
}
