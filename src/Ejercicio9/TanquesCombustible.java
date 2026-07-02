package Ejercicio9;


public class TanquesCombustible {

    public static void main(String[] args) {

        double radioMetros = TanqueSolucion.leerRadio();
        double alturaMetros = TanqueSolucion.leerAltura();


        double volumenDieselLitros = TanqueSolucion.calcularVolumenTanqueLitros(radioMetros, alturaMetros);
        double masaGasolinaKg = TanqueSolucion.calcularMasaGasolinaKg(volumenDieselLitros);

        TanqueSolucion.mostrarResultados(radioMetros, alturaMetros, volumenDieselLitros, masaGasolinaKg);

        System.exit(0);
    }
}