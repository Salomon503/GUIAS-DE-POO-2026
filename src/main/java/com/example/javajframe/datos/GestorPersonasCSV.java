package com.example.javajframe.datos;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase de apoyo que resuelve los ejercicios complementarios de la Guía #6:
 *
 * a) cargarDesdeCSV   -> lee el archivo CSV (si existe) y llena el modelo de tabla.
 * b) guardarEnCSV      -> recorre el modelo de tabla y escribe su contenido en el CSV.
 * c) insertarOActualizar -> si el Id ya existe en la tabla, actualiza esa fila;
 *                            si no existe, la agrega como registro nuevo.
 *
 * Se implementó como clase independiente (en el paquete "datos") para poder
 * reutilizarla desde frmPersona y también probarla sola, sin necesidad de
 * abrir el formulario gráfico (ver PruebaGestorCSV en el paquete aplicacion).
 */
public class GestorPersonasCSV {

    private static final String SEPARADOR = ",";

    /**
     * a) Carga los registros del archivo CSV indicado hacia el modelo de tabla recibido.
     * Solo carga los datos si el archivo existe; si no existe, no hace nada.
     */
    public static void cargarDesdeCSV(DefaultTableModel modelo, String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            System.out.println("[CSV] No existe el archivo, no se cargan datos: " + rutaArchivo);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] valores = linea.split(SEPARADOR, -1);
                Object[] fila = new Object[valores.length];
                for (int i = 0; i < valores.length; i++) {
                    fila[i] = valores[i].trim();
                }
                modelo.addRow(fila);
            }
            System.out.println("[CSV] Datos cargados correctamente desde: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("[CSV] Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * b) Recorre todas las filas del modelo de tabla recibido y las escribe
     * hacia el archivo CSV indicado (sobrescribe el contenido anterior).
     */
    public static void guardarEnCSV(DefaultTableModel modelo, String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        File carpeta = archivo.getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs(); // asegura que exista el paquete/carpeta "datos"
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            int filas = modelo.getRowCount();
            int columnas = modelo.getColumnCount();

            for (int f = 0; f < filas; f++) {
                StringBuilder linea = new StringBuilder();
                for (int c = 0; c < columnas; c++) {
                    Object valor = modelo.getValueAt(f, c);
                    linea.append(valor == null ? "" : valor.toString());
                    if (c < columnas - 1) {
                        linea.append(SEPARADOR);
                    }
                }
                bw.write(linea.toString());
                bw.newLine();
            }
            System.out.println("[CSV] Datos guardados correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("[CSV] Error al escribir el archivo: " + e.getMessage());
        }
    }

    /**
     * c) Busca en el modelo si ya existe una fila cuyo Id (columna 0) coincide
     * con el Id del vector recibido.
     * - Si existe: actualiza esa fila con los nuevos valores.
     * - Si no existe: inserta el vector como una nueva fila.
     */
    public static void insertarOActualizar(DefaultTableModel modelo, Object[] datosPersona) {
        String idBuscado = datosPersona[0] == null ? "" : datosPersona[0].toString();
        int filas = modelo.getRowCount();

        for (int f = 0; f < filas; f++) {
            Object valorId = modelo.getValueAt(f, 0);
            String idActual = valorId == null ? "" : valorId.toString();

            if (idActual.equals(idBuscado)) {
                for (int c = 0; c < datosPersona.length; c++) {
                    modelo.setValueAt(datosPersona[c], f, c);
                }
                System.out.println("[CSV] Registro actualizado. Id: " + idBuscado);
                return;
            }
        }

        modelo.addRow(datosPersona);
        System.out.println("[CSV] Registro nuevo insertado. Id: " + idBuscado);
    }
}
