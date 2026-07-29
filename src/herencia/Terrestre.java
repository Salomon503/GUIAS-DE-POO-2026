package herencia;

public class Terrestre extends Vehiculo {
    protected int totruedas; // solo lectura desde afuera (solo getter)

    public int getTotruedas() {
        return totruedas;
    }
}