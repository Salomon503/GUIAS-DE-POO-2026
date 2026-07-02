package Ejercicio9;

import javax.swing.JOptionPane;


public class TanqueSolucion {


    public static final double DENSIDAD_GASOLINA = 0.750;


    public static double leerRadio() {
        String entrada = JOptionPane.showInputDialog("Digite el radio del tanque (en metros):");
        return Double.parseDouble(entrada);
    }

    public static double leerAltura() {
        String entrada = JOptionPane.showInputDialog("Digite la altura del tanque (en metros):");
        return Double.parseDouble(entrada);
    }


    public static double calcularVolumenTanqueLitros(double radioMetros, double alturaMetros) {
        double volumenMetrosCubicos = Math.PI * Math.pow(radioMetros, 2) * alturaMetros;
        return volumenMetrosCubicos * 1000;
    }


    public static double calcularMasaGasolinaKg(double volumenLitros) {
        return volumenLitros * DENSIDAD_GASOLINA;
    }


    public static void mostrarResultados(double radio, double altura,
                                         double volumenDieselLitros, double masaGasolinaKg) {
        System.out.println("Medidas del tanque (identicas para ambos):");
        System.out.println("Radio:  " + radio + " m");
        System.out.println("Altura: " + altura + " m");
        System.out.println();
        System.out.println("Volumen maximo de Diesel en el 1er tanque:   " + volumenDieselLitros + " Litros");
        System.out.println("Masa maxima de Gasolina en el 2do tanque:    " + masaGasolinaKg + " Kg");
    }
}