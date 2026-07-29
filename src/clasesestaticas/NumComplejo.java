package clasesestaticas;

public class NumComplejo {
    private double real;
    private double ima;

    private void asignarvalor(double r, double i) {
        this.real = r;
        this.ima = i;
    }

    public NumComplejo() {
        asignarvalor(0, 0);
    }

    public NumComplejo(double r, double i) {
        asignarvalor(0, 0);
        this.real = r;
        this.ima = i;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getIma() {
        return ima;
    }

    public void setIma(double ima) {
        this.ima = ima;
    }

    public String vervalor() {
        String res = "";
        res = String.format("%.2f", real);
        if (ima >= 0) res += "+";
        res += String.format("%.2fi", ima);
        return res;
    }

    public NumComplejo getConjugada() {
        // por ej, la Conjugada del Complejo -4+3i es -4-3i
        NumComplejo temp = new NumComplejo();
        temp.setReal(getReal());
        temp.setIma(-1 * getIma());
        return (temp);
    }
}

