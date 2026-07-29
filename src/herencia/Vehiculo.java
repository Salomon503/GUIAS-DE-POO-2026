package herencia;

public class Vehiculo {
    protected String tipovehiculo; // solo lectura (sin setter publico)
    private String nombre;
    private String marca;

    public Vehiculo() {
        tipovehiculo = "ninguno";
        nombre = "desconocido";
        marca = "pendiente";
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void verdatos() {
        System.out.println("Este es un vehiculo genérico, sin nombre ni marca");
    }
}
