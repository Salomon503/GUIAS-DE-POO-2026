package Ejercicio8;


public class CapacitorEquivalente {

    public static void main(String[] args) {


        double c1 = CapacitorSolucion.leerCapacitor("Digite el valor del primer capacitor (uF):");
        double c2 = CapacitorSolucion.leerCapacitor("Digite el valor del segundo capacitor (uF):");
        double c3 = CapacitorSolucion.leerCapacitor("Digite el valor del tercer capacitor (uF):");

        if (!CapacitorSolucion.sonValidos(c1, c2, c3)) {
            System.out.println("Dato de entrada invalido: los 3 capacitores deben");
            System.out.println("ser valores numericos mayores que cero.");
            System.out.println("El programa finalizara sin realizar calculos.");
            System.exit(0);
        }


        double capacitorSerie = CapacitorSolucion.calcularCapacitorSerie(c1, c2, c3);
        double capacitorParalelo = CapacitorSolucion.calcularCapacitorParalelo(c1, c2, c3);

        CapacitorSolucion.mostrarResultados(c1, c2, c3, capacitorSerie, capacitorParalelo);

        System.exit(0);
    }
}
