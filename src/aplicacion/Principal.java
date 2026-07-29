package aplicacion;

import herencia.*;
import clasesestaticas.*;
import clasesabstractas.*;
import java.util.Scanner;

public class Principal {

    public static void demoherencia() {
        Barco velero, ronald;
        System.out.println("Demostracion del principio de herencia de clases\n");

        velero = new Barco("Paraiso", "Marca Leonard", Propulsion.VELAS);
        velero.verdatos();

        ronald = new Barco("Titanic", "Clase Olympic", Propulsion.MOTOR);
        ronald.setNombre("Portaaviones Ronald Reagan");
        ronald.setMarca("Clase Nimitz");
        ronald.verdatos();

        Moto honda = new Moto("Honda ATV", "Modelo 2024", 4, 420);
        honda.verdatos();

        // ---------------- EJERCICIO 1: demostracion de Camion ----------------
        System.out.println("\nDemostracion de la subclase Camion:");

        // Objeto 1: datos apropiados
        Camion volvo = new Camion("Volvo FH16", "Volvo", 12, 25.5);
        volvo.verdatos();

        // Objeto 2: cantidad de ruedas invalida (no es config. valida -> conserva 6 por defecto)
        Camion scania = new Camion("Scania R450", "Scania", 7, 18);
        scania.verdatos();

        // Objeto 3: se crea y luego se modifican sus campos de manera individual
        Camion kenworth = new Camion("Kenworth T800", "Kenworth", 6, 10);
        kenworth.setNombre("Kenworth T880");
        kenworth.setMarca("Kenworth Trucks");
        kenworth.setTotruedas(18);
        kenworth.setTonelaje(30);
        kenworth.verdatos();
    }

    public static void democlaseestatica() {
        NumComplejo a, b, c, d, e;
        a = new NumComplejo();       // 0+0i
        b = new NumComplejo(3, 8);   // 3+8i
        c = new NumComplejo(-2, -5); // -2-5i
        d = new NumComplejo(0, -4);  // 0-4i

        System.out.printf("a = %s \nb = %s \nc = %s \nd = %s",
                a.vervalor(), b.vervalor(), c.vervalor(), d.vervalor());

        e = b.getConjugada();
        System.out.printf("\nla conjugada de complejo b es %s\n", e.vervalor());

        NumComplejo suma, resta, prod, div, pot;

        suma = MateComplejos.Suma(b, c);
        System.out.printf("La suma de %s con %s es %s\n",
                b.vervalor(), c.vervalor(), suma.vervalor());

        resta = MateComplejos.Resta(b, d);
        System.out.printf("La resta de %s con %s es %s\n",
                b.vervalor(), d.vervalor(), resta.vervalor());

        NumComplejo m1 = new NumComplejo(4, -6);
        NumComplejo m2 = new NumComplejo(-3, 2);
        prod = MateComplejos.Multiplicar(m1, m2);
        System.out.printf("La multiplicacion de %s con %s es %s\n",
                m1.vervalor(), m2.vervalor(), prod.vervalor());

        // ---------------- EJERCICIO 2: Dividir y Potencia ----------------
        div = MateComplejos.Dividir(b, c);
        System.out.printf("La division de %s entre %s es %s\n",
                b.vervalor(), c.vervalor(), div.vervalor());

        pot = MateComplejos.Potencia(d, 3);
        System.out.printf("La potencia de %s elevado a 3 es %s\n",
                d.vervalor(), pot.vervalor());
    }

    public static void demoabstraccion1() {
        Scanner teclado = new Scanner(System.in);
        HDD Kington, Seagate;
        Kington = new HDD();
        Seagate = new HDD(2048, 175, 5400, 3);

        System.out.println("Estado inicial de par de discos duros (HDD):");
        System.out.println("* Kington: " + Kington.mostrarparametros());
        System.out.println("* Seagate: " + Seagate.mostrarparametros());

        System.out.print("\nIngrese la nueva capacidad (en GB) de Kington ? ");
        Kington.setCapac(teclado.nextInt());
        System.out.println("Nuevo estado del HDD Kington:");
        System.out.println("* Kington: " + Kington.mostrarparametros());

        System.out.print("\nAhora ingrese el nuevo rendimiento (en MB/s) de Seagate ? ");
        Seagate.setRendim(teclado.nextInt());
        System.out.println("Nuevo estado del HDD Seagate:");
        System.out.println("* Seagate: " + Seagate.mostrarparametros());
    }

    public static void demoabstraccion2() {
        Scanner teclado = new Scanner(System.in);
        SSD disco1, disco2;
        disco1 = new SSD();
        disco2 = new SSD('P', 3, 5000);

        System.out.println("Estado inicial de par de medios (SSD):");
        System.out.println("* SSD 1: " + disco1.mostrarparametros());
        System.out.println("* SSD 2: " + disco2.mostrarparametros());

        System.out.print("\nIngrese la nueva capacidad (256 o 512) GB de SSD 1 ? ");
        disco1.setCapac(teclado.nextInt());
        System.out.println("Nuevo estado del SSD 1:");
        System.out.println("* SSD 1: " + disco1.mostrarparametros());

        System.out.print("\nIngrese letra (s: SATA o p: PCIe) para cambiar tipo Interfaz del SSD 2? ");
        disco2.setInterfaz(teclado.next().charAt(0));
        System.out.println("Nuevo estado del SSD 2:");
        System.out.println("* SSD 2: " + disco2.mostrarparametros());
    }

    // ---------------- EJERCICIO 3: demostracion de MicroSD ----------------
    public static void demoabstraccion3() {
        Scanner teclado = new Scanner(System.in);
        MicroSD sd1, sd2, sd3;
        sd1 = new MicroSD();                   // UHS-I predeterminada
        sd2 = new MicroSD('2', 128, 260, 10);  // UHS-II
        sd3 = new MicroSD('3', 512, 500, 6);   // UHS-III

        System.out.println("Estado inicial de las memorias microSD:");
        System.out.println("* SD 1: " + sd1.mostrarparametros());
        System.out.println("* SD 2: " + sd2.mostrarparametros());
        System.out.println("* SD 3: " + sd3.mostrarparametros());

        System.out.print("\nIngrese nueva capacidad (en GB) para SD 1 ? ");
        sd1.setCapac(teclado.nextInt());
        System.out.println("* SD 1: " + sd1.mostrarparametros());

        System.out.print("\nIngrese nuevo rendimiento (en MB/s) para SD 2 ? ");
        sd2.setRendim(teclado.nextInt());
        System.out.println("* SD 2: " + sd2.mostrarparametros());

        System.out.print("\nIngrese nueva clase de velocidad (2,4,6,10) para SD 3 ? ");
        sd3.setClaseVelocidad(teclado.nextInt());
        System.out.println("* SD 3: " + sd3.mostrarparametros());
    }

    public static void main(String[] args) {
        demoherencia();
        // democlaseestatica();
        // demoabstraccion1();
        // demoabstraccion2();
        // demoabstraccion3();
    }
}
