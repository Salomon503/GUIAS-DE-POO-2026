package herencia;

public class Moto extends Terrestre {
    private double cilindraje;

    public double getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(double cilindraje) {
        this.cilindraje = cilindraje;
    }

    public void setTotruedas(int totruedas) {
        switch (totruedas) {
            case 2: case 3: case 4:
                super.totruedas = totruedas;
                break;
        }
    }

    public Moto(String nom, String marca, int totruedas, double cilindraje) {
        super.tipovehiculo = "Moto";
        this.totruedas = 2; // por defecto, moto de 2 ruedas
        setNombre(nom);
        setMarca(marca);
        setTotruedas(totruedas);
        setCilindraje(cilindraje);
    }

    public void verdatos() {
        // ej: Moto Honda ATV, Modelo 2024, 4 ruedas, Cilindrada: 420 CC.
        String descrip = String.format(
                "%s %s, %s, %d ruedas, Cilindrada: %.1f CC.",
                super.getTipovehiculo(), getNombre(), getMarca(),
                getTotruedas(), getCilindraje());
        System.out.println(descrip);
    }
}
