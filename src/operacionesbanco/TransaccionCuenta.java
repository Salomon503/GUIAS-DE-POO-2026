package operacionesbanco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransaccionCuenta {

    //Campos encapsulados
    private int idtransac;
    private double montotransac;
    private LocalDateTime fechatransac;
    private double saldoinic;
    private double nuevosaldo;
    private char tipotransaccion;
    private String descripcion;

    //estado de transaccion realizada.
    // 0: exitosa, 1: saldo insuficiente, 2: monto negativo, -1: operacion no reconocida
    private int estado;

    //metodo constructor
    public TransaccionCuenta(int numoperac, double saldoinicial) {
        idtransac = numoperac;
        this.saldoinic = saldoinicial;
        this.nuevosaldo = saldoinicial;
    }

    public void Ejecutar(char tipooperac, double monto, String descripcion) {
        estado = 0; //asume que transaccion se ejecutara con exito
        fechatransac = LocalDateTime.now();
        this.tipotransaccion = tipooperac;
        this.descripcion = descripcion;

        if (monto < 0) {
            estado = 2;
            return;
        }

        switch (tipooperac) {
            case 'r':
            case 'R':
                if (monto > saldoinic) {
                    estado = 1;
                    return;
                }
                this.montotransac = monto;
                nuevosaldo = saldoinic - monto;
                break;
            case 'd':
            case 'D':
                this.montotransac = monto;
                nuevosaldo = saldoinic + monto;
                break;
            default:
                estado = -1;
        }
    }

    public String getRegistro() {
        String registro, titulooperac = "";
        DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String fecha = fechatransac.format(formatofecha);

        switch (this.tipotransaccion) {
            case 'd':
            case 'D':
                titulooperac = "Deposito";
                break;
            case 'r':
            case 'R':
                titulooperac = "Retiro";
                break;
        }

        registro = String.format(
                "%4d.|%20s| %-16s |%-25s |$%10.2f |$%10.2f|",
                idtransac, fecha, titulooperac, descripcion, montotransac, nuevosaldo);
        return registro;
    }

    //Metodos de propiedad (get/set) usados por CuentaBancaria
    public int getIdtransac() {
        return idtransac;
    }

    public double getMontotransac() {
        return montotransac;
    }

    public double getNuevosaldo() {
        return nuevosaldo;
    }

    public double getSaldoinic() {
        return saldoinic;
    }

    public char getTipotransaccion() {
        return tipotransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getEstado() {
        return estado;
    }
}