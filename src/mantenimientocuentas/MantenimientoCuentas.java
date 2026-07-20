package mantenimientocuentas;

import operacionesbanco.Cliente;
import operacionesbanco.CuentaBancaria;

import java.util.ArrayList;
import java.util.Scanner;

public class MantenimientoCuentas {

    //Colecciones que actuan como "base de datos" en memoria
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<CuentaBancaria> cuentas = new ArrayList<>();
    private static Scanner lapiz = new Scanner(System.in);

    public static void main(String[] args) {

        //Clientes de prueba (opcional, para no arrancar con la lista vacia)
        clientes.add(new Cliente("Iris", "Bonilla", "0858786-6"));
        clientes.add(new Cliente("Armando", "Nieto", "0158224-4"));
        clientes.add(new Cliente("Gilberto", "Zacarias", "6380044-7"));

        char opcion;

        do {
            mostrarMenu();
            opcion = lapiz.nextLine().toUpperCase().charAt(0);

            switch (opcion) {
                case 'A':
                    registrarCliente();
                    break;
                case 'B':
                    abrirCuentaAhorro();
                    break;
                case 'C':
                    System.out.println("Saliendo de la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo.");
            }

        } while (opcion != 'C');
    }

    //Muestra el menu principal
    private static void mostrarMenu() {
        System.out.println("\n===== MANTENIMIENTO DE CUENTAS DE AHORRO =====");
        System.out.println("A) Registrar un cliente");
        System.out.println("B) Abrir una cuenta de ahorro");
        System.out.println("C) Salir de la aplicacion");
        System.out.print("Seleccione una opcion: ");
    }

    //Opcion A: Registrar un cliente
    private static void registrarCliente() {
        System.out.println("\n--- Registrar Cliente ---");
        System.out.print("Nombre: ");
        String nombre = lapiz.nextLine();
        System.out.print("Apellido: ");
        String apellido = lapiz.nextLine();
        System.out.print("DUI: ");
        String dui = lapiz.nextLine();

        Cliente nuevo = new Cliente(nombre, apellido, dui);
        clientes.add(nuevo);

        mostrarListaClientes();
        System.out.print("-> Presione Enter para retornar al menu");
        lapiz.nextLine();
    }

    //Opcion B: Abrir una cuenta de ahorro
    private static void abrirCuentaAhorro() {
        System.out.println("\n--- Abrir Cuenta de Ahorro ---");

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados. Registre uno primero.");
            System.out.print("-> Presione Enter para retornar al menu");
            lapiz.nextLine();
            return;
        }

        mostrarListaClientes();

        System.out.print("\nDigite el numero del cliente para abrir su cuenta: ");
        int seleccion = Integer.parseInt(lapiz.nextLine());

        if (seleccion < 1 || seleccion > clientes.size()) {
            System.out.println("Numero de cliente invalido.");
            System.out.print("-> Presione Enter para retornar al menu");
            lapiz.nextLine();
            return;
        }

        Cliente clienteSeleccionado = clientes.get(seleccion - 1);

        System.out.print("Digite el monto inicial de apertura: $ ");
        double saldoInicial = Double.parseDouble(lapiz.nextLine());

        CuentaBancaria cuentaNueva = new CuentaBancaria(clienteSeleccionado, saldoInicial);
        cuentas.add(cuentaNueva);

        cuentaNueva.vertransacciones();

        System.out.print("\n-> Presione Enter para retornar al menu");
        lapiz.nextLine();
    }

    //Imprime el listado de clientes registrados
    private static void mostrarListaClientes() {
        System.out.printf("%nClientes registrados (%d):%n", clientes.size());
        System.out.println("# Datos del Cliente");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, clientes.get(i).DatosCliente());
        }
    }
}