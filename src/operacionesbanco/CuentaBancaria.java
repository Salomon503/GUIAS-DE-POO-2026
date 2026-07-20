package operacionesbanco;

import java.util.ArrayList;

public class CuentaBancaria {

    //Campos encapsulados
    private Cliente titularcuenta;
    private double saldoactual;
    private int numtransac;
    private ArrayList<TransaccionCuenta> transacciones;

    private void inicializarcampos() {
        titularcuenta = null;
        numtransac = 0;
        saldoactual = 0;
        transacciones = new ArrayList<>();
    }

    //metodo constructor
    public CuentaBancaria(Cliente propietario, double saldoinicial) {
        inicializarcampos();
        this.titularcuenta = propietario;

        TransaccionCuenta transacTemp = new TransaccionCuenta(++numtransac, 0);
        //Para abrir la cuenta de banco, ejecuta transaccion (deposito) con el saldo inicial
        transacTemp.Ejecutar('d', saldoinicial, "apertura de cuenta");
        this.saldoactual = transacTemp.getNuevosaldo();
        transacciones.add(transacTemp);
    }

    public void realizaroperacion(char tipooperac, double monto, String descrip) {
        TransaccionCuenta transacTemp = new TransaccionCuenta(numtransac + 1, saldoactual);
        transacTemp.Ejecutar(tipooperac, monto, descrip);

        switch (transacTemp.getEstado()) {
            case 1:
                System.out.printf(
                        "ERROR: Saldo de cuenta insuficiente para retirar $ %.2f%n", monto);
                break;
            case 2:
                System.out.printf(
                        "ERROR: Monto $ %.2f requerido no puede ser negativo%n", monto);
                break;
            default:
                numtransac++;
                this.saldoactual = transacTemp.getNuevosaldo();
                transacciones.add(transacTemp);
                System.out.println("Transaccion ejecutada con exito ");
                break;
        }
    }

    public void vertransacciones() {
        String titulos;
        System.out.printf("%n* Titular de Cuenta: %s%n", titularcuenta.DatosCliente());
        System.out.printf("* Saldo actual: $ %.2f%n", saldoactual);

        if (transacciones.size() == 0) {
            System.out.println("Aun sin transacciones realizadas");
        } else {
            titulos = String.format(
                    "%4s.|%-20s| %-16s |%-25s |%-10s |%-11s|",
                    "id", "fecha", "Tipo transaccion", "Descripcion", "Monto", "Saldo");
            System.out.println(titulos);
            for (TransaccionCuenta cuenta : transacciones) {
                System.out.println(cuenta.getRegistro());
            }
        }
    }

    //Metodos de propiedad (get/set)
    public double getSaldoactual() {
        return saldoactual;
    }

    public Cliente getTitularcuenta() {
        return titularcuenta;
    }

    public int getNumtransac() {
        return numtransac;
    }

    public ArrayList<TransaccionCuenta> getTransacciones() {
        return transacciones;
    }
}