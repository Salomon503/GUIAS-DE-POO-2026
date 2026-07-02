package Ejercicio8;

import javax.swing.JOptionPane;


public class CapacitorSolucion {


    public static double leerCapacitor(String mensaje) {
        String entrada = JOptionPane.showInputDialog(mensaje);
        double valor;
        try {
            valor = Double.parseDouble(entrada);
        } catch (Exception e) {
            valor = -1;
        }
        return valor;
    }


    public static boolean sonValidos(double c1, double c2, double c3) {
        return (c1 > 0) && (c2 > 0) && (c3 > 0);
    }


    public static double calcularCapacitorSerie(double c1, double c2, double c3) {
        double inversoTotal = (1 / c1) + (1 / c2) + (1 / c3);
        return 1 / inversoTotal;
    }


    public static double calcularCapacitorParalelo(double c1, double c2, double c3) {
        return c1 + c2 + c3;
    }


    public static void mostrarResultados(double c1, double c2, double c3,
                                         double serie, double paralelo) {
        System.out.println("Valores de capacitores ingresados:");
        System.out.println("C1 = " + c1 + " uF");
        System.out.println("C2 = " + c2 + " uF");
        System.out.println("C3 = " + c3 + " uF");
        System.out.println();
        System.out.println("Capacitor equivalente en serie:    " + serie + " uF");
        System.out.println("Capacitor equivalente en paralelo: " + paralelo + " uF");
    }
}
