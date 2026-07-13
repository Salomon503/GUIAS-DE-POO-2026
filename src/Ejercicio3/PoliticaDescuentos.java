package Ejercicio3;

import java.util.Random;

public class PoliticaDescuentos {

    public static void main(String[] args) {
        aplicarDescuento("Salomon Polanco", 120.50);
        aplicarDescuento("Maria Lopez", 85.00);
    }

    // Metodo estatico que recibe el nombre del cliente y el monto de la compra
    static void aplicarDescuento(String cliente, double monto) {
        String[] colores = {"cafe", "roja", "azul", "verde"};
        Random rand = new Random();
        String colorBolita = colores[rand.nextInt(colores.length)];

        double porcentaje;

        switch (colorBolita) {
            case "cafe":
                porcentaje = 0.10; // 10%
                break;
            case "roja":
                porcentaje = 0.15; // 15%
                break;
            case "azul":
                porcentaje = 0.25; // 25%
                break;
            case "verde":
                porcentaje = 0.50; // 50%
                break;
            default:
                porcentaje = 0.0;
                break;
        }

        double montoDescuento = monto * porcentaje;
        double totalPagar = monto - montoDescuento;

        System.out.println("\nCliente: " + cliente);
        System.out.println("Monto de la compra: $" + monto);
        System.out.println("Bolita generada: " + colorBolita);
        System.out.println("Descuento aplicado: " + (porcentaje * 100) + "%");
        System.out.println("Monto del descuento: $" + montoDescuento);
        System.out.println("Total a pagar: $" + totalPagar);
    }
}