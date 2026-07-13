package Ejercicio1;

import java.util.HashMap;
import javax.swing.JOptionPane;

public class BibliotecaCRUD {

    // Campo estatico: coleccion HashMap compartida por todos los metodos
    static HashMap<String, String> libros = new HashMap<>();

    public static void main(String[] args) {
        int opcion;

        do {
            String menu = "Administracion de Biblioteca\n"
                    + "1. Agregar libro\n"
                    + "2. Consultar libro\n"
                    + "3. Actualizar libro\n"
                    + "4. Eliminar libro\n"
                    + "5. Finalizar";

            String entrada = JOptionPane.showInputDialog(menu);

            if (entrada == null) break; // el usuario presiono Cancelar

            try {
                opcion = Integer.parseInt(entrada);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Opcion invalida, ingrese un numero del 1 al 5");
                continue;
            }

            switch (opcion) {
                case 1: agregarLibro(); break;
                case 2: consultarLibro(); break;
                case 3: actualizarLibro(); break;
                case 4: eliminarLibro(); break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Fin del programa");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion no valida");
            }

        } while (true);
    }

    // Solicita un ISBN valido (formato 999-9-999-99999-9) hasta 3 intentos
    static String solicitarISBN() {
        int intentos = 0;

        while (intentos < 3) {
            String isbn = JOptionPane.showInputDialog(
                    "Ingrese el ISBN (formato: 999-9-999-99999-9):");

            if (isbn != null && isbn.matches("\\d{3}-\\d-\\d{3}-\\d{5}-\\d")) {
                return isbn;
            }

            intentos++;
            JOptionPane.showMessageDialog(null,
                    "ISBN invalido. Intento " + intentos + " de 3");
        }

        JOptionPane.showMessageDialog(null,
                "Se supero el numero de intentos. Operacion cancelada");
        return null;
    }

    // CREATE
    static void agregarLibro() {
        String isbn = solicitarISBN();
        if (isbn == null) return;

        if (libros.containsKey(isbn)) {
            JOptionPane.showMessageDialog(null, "Ya existe un libro con ese ISBN");
            return;
        }

        String titulo = JOptionPane.showInputDialog("Ingrese el titulo del libro:");
        libros.put(isbn, titulo);
        JOptionPane.showMessageDialog(null, "Libro agregado correctamente");
    }

    // READ
    static void consultarLibro() {
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN a consultar:");

        if (libros.containsKey(isbn)) {
            JOptionPane.showMessageDialog(null, "Titulo: " + libros.get(isbn));
        } else {
            JOptionPane.showMessageDialog(null, "ISBN no encontrado");
        }
    }

    // UPDATE
    static void actualizarLibro() {
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN a actualizar:");

        if (libros.containsKey(isbn)) {
            String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo titulo:");
            libros.put(isbn, nuevoTitulo);
            JOptionPane.showMessageDialog(null, "Libro actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "ISBN no encontrado");
        }
    }

    // DELETE
    static void eliminarLibro() {
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN a eliminar:");

        if (libros.containsKey(isbn)) {
            libros.remove(isbn);
            JOptionPane.showMessageDialog(null, "Libro eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "ISBN no encontrado");
        }
    }
}