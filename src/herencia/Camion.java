package herencia;

/*
 * EJERCICIO 1
 * Subclase Camion, derivada de Terrestre.
 *
 * Criterios investigados (ajusta si tu UML define otro nombre de campo):
 * - tonelaje (double): carga util permitida. Segun normativa vial estandar,
 *   los camiones de carga van desde 1 ton (camion liviano/pickup de carga)
 *   hasta 40 ton (tracto-camion articulado, limite legal comun en EU/UE/LatAm).
 *   Valor por defecto: 5 toneladas (camion mediano tipico).
 * - totruedas (heredado de Terrestre): las configuraciones estandar de ejes
 *   para camiones son 6, 10, 12, 18 y 22 ruedas (2, 3, 4, 5 y 6 ejes tipicos).
 *   Valor por defecto: 6 ruedas (camion rigido de 2 ejes).
 */
public class Camion extends Terrestre {
    private double tonelaje;

    public double getTonelaje() {
        return tonelaje;
    }

    public void setTonelaje(double tonelaje) {
        // rango permitido: 1 a 40 toneladas
        if (tonelaje >= 1 && tonelaje <= 40)
            this.tonelaje = tonelaje;
    }

    public void setTotruedas(int totruedas) {
        // configuraciones estandar de ruedas para camiones
        switch (totruedas) {
            case 6: case 10: case 12: case 18: case 22:
                super.totruedas = totruedas;
                break;
        }
    }

    public Camion(String nom, String marca, int totruedas, double tonelaje) {
        super.tipovehiculo = "Camion";
        this.totruedas = 6;   // valor por defecto (camion rigido, 2 ejes)
        this.tonelaje = 5;    // valor por defecto (5 toneladas)
        setNombre(nom);
        setMarca(marca);
        setTotruedas(totruedas);
        setTonelaje(tonelaje);
    }

    public void verdatos() {
        // ej: Camion Volvo FH16, Volvo, 12 ruedas, Tonelaje: 25.5 Ton.
        String descrip = String.format(
                "%s %s, %s, %d ruedas, Tonelaje: %.1f Ton.",
                super.getTipovehiculo(), getNombre(), getMarca(),
                getTotruedas(), getTonelaje());
        System.out.println(descrip);
    }
}

