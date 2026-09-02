package com.example.javajframe.aplicacion;

import com.example.javajframe.datos.GestorPersonasCSV;
import javax.swing.table.DefaultTableModel;

/**
 * Clase de PRUEBA para verificar, desde consola y sin abrir ningún
 * formulario, que los 3 métodos de los ejercicios complementarios
 * funcionan correctamente:
 *
 *   1) cargarDesdeCSV
 *   2) insertarOActualizar (inserción y actualización)
 *   3) guardarEnCSV
 *
 * Para ejecutarla en NetBeans: clic derecho sobre este archivo ->
 * "Run File" (o Shift+F6). No necesita el pom.xml modificado porque
 * se ejecuta directo desde el editor, no desde el mainClass del proyecto.
 */
public class PruebaGestorCSV {

    public static void main(String[] args) {

        String[] columnas = {"Id", "Nombres", "Edad", "Telefono", "Sexo"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        // Usamos un archivo de prueba separado para no alterar el
        // personas.csv "real" que carga el formulario frmPersona.
        String rutaCsvPrueba =
                "src/main/java/com/example/javajframe/datos/personas_prueba.csv";

        System.out.println("=== PRUEBA 1: cargar desde CSV (aún no existe) ===");
        GestorPersonasCSV.cargarDesdeCSV(modelo, rutaCsvPrueba);
        System.out.println("Filas cargadas: " + modelo.getRowCount());

        System.out.println("\n=== PRUEBA 2: insertar 3 personas nuevas ===");
        GestorPersonasCSV.insertarOActualizar(modelo,
                new Object[]{"1", "Kevin", "24", "7605-5255", "Masculino"});
        GestorPersonasCSV.insertarOActualizar(modelo,
                new Object[]{"2", "Ana Martinez", "30", "7000-1111", "Femenino"});
        GestorPersonasCSV.insertarOActualizar(modelo,
                new Object[]{"3", "Cristiano Ronaldo", "41", "7777-7777", "Masculino"});
        imprimirModelo(modelo);

        System.out.println("\n=== PRUEBA 3: actualizar persona existente (Id 2) ===");
        GestorPersonasCSV.insertarOActualizar(modelo,
                new Object[]{"2", "Ana Martinez Lopez", "31", "7000-2222", "Femenino"});
        imprimirModelo(modelo);

        System.out.println("\n=== PRUEBA 4: guardar en CSV ===");
        GestorPersonasCSV.guardarEnCSV(modelo, rutaCsvPrueba);

        System.out.println("\n=== PRUEBA 5: volver a cargar desde el CSV recién guardado ===");
        DefaultTableModel modeloVerificacion = new DefaultTableModel(null, columnas);
        GestorPersonasCSV.cargarDesdeCSV(modeloVerificacion, rutaCsvPrueba);
        imprimirModelo(modeloVerificacion);

        System.out.println("\nPrueba finalizada. Revisa el archivo generado en:");
        System.out.println(rutaCsvPrueba);
    }

    private static void imprimirModelo(DefaultTableModel modelo) {
        for (int f = 0; f < modelo.getRowCount(); f++) {
            StringBuilder sb = new StringBuilder("Fila " + f + ": ");
            for (int c = 0; c < modelo.getColumnCount(); c++) {
                sb.append(modelo.getValueAt(f, c)).append(" | ");
            }
            System.out.println(sb.toString());
        }
    }
}

