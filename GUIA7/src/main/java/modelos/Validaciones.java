package modelos;

import java.util.regex.*;

/**
 * Clase dedicada EXCLUSIVAMENTE a validar los datos que ingresa el
 * usuario (tal como lo pide la nota final de la guia). Todos sus
 * metodos son estaticos, igual que la clase Testing de la guia.
 */
public class Validaciones {

    /** Verifica que el texto sea un numero entero positivo. */
    public static boolean esEntero(String valor) {
        if (valor == null) return false;
        String expresion = "\\d+";
        Pattern pat = Pattern.compile(expresion);
        Matcher mat = pat.matcher(valor.trim());
        return mat.matches();
    }

    /** Verifica que el texto no sea nulo ni este vacio. */
    public static boolean noVacio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    /** Verifica que el texto contenga solo letras y espacios (nombres, apellidos, etc). */
    public static boolean esTextoValido(String valor) {
        if (valor == null) return false;
        String expresion = "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
        Pattern pat = Pattern.compile(expresion);
        Matcher mat = pat.matcher(valor.trim());
        return mat.matches() && !valor.trim().isEmpty();
    }
}
