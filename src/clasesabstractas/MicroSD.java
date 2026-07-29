package clasesabstractas;

/*
 * EJERCICIO 3
 * Investigacion sobre memorias microSD:
 *
 * - Equivalente de campos ya existentes en UnidAlmac:
 *     capac  -> capacidad de almacenamiento en GB (microSDHC: 4-32GB,
 *               microSDXC: 64GB-2048GB(2TB))
 *     rendim -> velocidad de transferencia del bus, en MB/s
 *
 * - Parametros propios de las microSD, NO cubiertos por UnidAlmac:
 *     1) busInterfaz: tipo de bus UHS ('1'=UHS-I hasta 104MB/s,
 *        '2'=UHS-II hasta 312MB/s, '3'=UHS-III hasta 624MB/s)
 *     2) claseVelocidad (Speed Class): velocidad minima de escritura
 *        sostenida garantizada (2, 4, 6 o 10 MB/s)
 */
public class MicroSD extends UnidAlmac {
    private char busInterfaz;     // '1': UHS-I, '2': UHS-II, '3': UHS-III
    private int claseVelocidad;   // Speed Class: 2, 4, 6 o 10

    public char getBusInterfaz() {
        return busInterfaz;
    }

    public void setBusInterfaz(char busInterfaz) {
        if (busInterfaz != this.busInterfaz) {
            switch (busInterfaz) {
                case '1': // UHS-I
                    this.busInterfaz = busInterfaz;
                    capac = 32;
                    rendim = 50;
                    break;
                case '2': // UHS-II
                    this.busInterfaz = busInterfaz;
                    capac = 128;
                    rendim = 260;
                    break;
                case '3': // UHS-III
                    this.busInterfaz = busInterfaz;
                    capac = 512;
                    rendim = 500;
                    break;
            }
        }
    }

    public int getCapac() {
        return capac;
    }

    public void setCapac(int capac) {
        // rango general admitido en GB (hasta 1TB = 1024GB)
        if (capac >= 1 && capac <= 1024)
            super.capac = capac;
    }

    public String getFormato() {
        // formato fisico derivado de la capacidad (equivalencia de capac)
        if (capac <= 32) return "microSDHC";
        else if (capac <= 2048) return "microSDXC";
        else return "microSDUC";
    }

    public int getRendim() {
        return rendim;
    }

    public void setRendim(int rendim) {
        switch (busInterfaz) {
            case '1':
                if (rendim >= 10 && rendim <= 104)
                    super.rendim = rendim;
                break;
            case '2':
                if (rendim >= 10 && rendim <= 312)
                    super.rendim = rendim;
                break;
            case '3':
                if (rendim >= 10 && rendim <= 624)
                    super.rendim = rendim;
                break;
        }
    }

    public int getClaseVelocidad() {
        return claseVelocidad;
    }

    public void setClaseVelocidad(int claseVelocidad) {
        // Speed Class estandar: 2, 4, 6, 10
        switch (claseVelocidad) {
            case 2: case 4: case 6: case 10:
                this.claseVelocidad = claseVelocidad;
                break;
        }
    }

    public MicroSD() {
        this.busInterfaz = '-';
        setBusInterfaz('1');   // UHS-I predeterminado
        setClaseVelocidad(10);
    }

    public MicroSD(char busInterfaz, int capac, int rendim, int claseVelocidad) {
        setBusInterfaz('1');
        setBusInterfaz(busInterfaz);
        setCapac(capac);
        setRendim(rendim);
        setClaseVelocidad(claseVelocidad);
    }

    public String mostrarparametros() {
        String nombus;
        switch (busInterfaz) {
            case '1': nombus = "UHS-I"; break;
            case '2': nombus = "UHS-II"; break;
            case '3': nombus = "UHS-III"; break;
            default: nombus = "desconocido";
        }
        return String.format(
                "microSD: Formato %s, Capacidad %d GB, Bus %s, Rendimiento(%d MB/s), Class %d",
                getFormato(), getCapac(), nombus, getRendim(), getClaseVelocidad());
    }
}

