package Ejercicio2;

public class EcuacionCuadratica {

    public static void main(String[] args) {
        // Prueba con las 3 ecuaciones propuestas en la guia
        resolverEcuacion(16, 0, 9);   // 16x^2 + 0x + 9 = 0
        resolverEcuacion(1, -2, 5);   // 1x^2 - 2x + 5 = 0
        resolverEcuacion(4, -4, 7);   // 4x^2 - 4x + 7 = 0
    }

    // Metodo complementario: calcula el determinante (discriminante) b^2 - 4ac
    static double determinante(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    // Metodo principal: recibe los coeficientes e imprime las raices
    static void resolverEcuacion(double a, double b, double c) {
        double det = determinante(a, b, c);

        System.out.println("\nEcuacion: " + a + "x^2 + " + b + "x + " + c + " = 0");
        System.out.println("Determinante: " + det);

        if (det > 0) {
            // Raices reales distintas
            double x1 = (-b + Math.sqrt(det)) / (2 * a);
            double x2 = (-b - Math.sqrt(det)) / (2 * a);
            System.out.println("Solucion: Raices reales y distintas");
            System.out.println("x1 = " + x1);
            System.out.println("x2 = " + x2);

        } else if (det == 0) {
            // Raiz real doble
            double x = -b / (2 * a);
            System.out.println("Solucion: Raiz real doble");
            System.out.println("x = " + x);

        } else {
            // Raices complejas conjugadas (det < 0)
            double parteReal = -b / (2 * a);
            double parteImag = Math.sqrt(-det) / (2 * a);
            System.out.println("Solucion: Raices complejas conjugadas");
            System.out.println("x1 = " + parteReal + " + " + parteImag + "i");
            System.out.println("x2 = " + parteReal + " - " + parteImag + "i");
        }
    }
}