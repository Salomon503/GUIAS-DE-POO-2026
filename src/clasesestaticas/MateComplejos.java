package clasesestaticas;

public class MateComplejos {

    public static NumComplejo Suma(NumComplejo n1, NumComplejo n2) {
        NumComplejo res = new NumComplejo();
        res.setReal(n1.getReal() + n2.getReal());
        res.setIma(n1.getIma() + n2.getIma());
        return res;
    }

    public static NumComplejo Resta(NumComplejo n1, NumComplejo n2) {
        NumComplejo res = new NumComplejo();
        res.setReal(n1.getReal() - n2.getReal());
        res.setIma(n1.getIma() - n2.getIma());
        return res;
    }

    public static NumComplejo Multiplicar(NumComplejo n1, NumComplejo n2) {
        // (a+bi)(c+di) = (ac - bd) + (ad + bc)i
        double a = n1.getReal(), b = n1.getIma();
        double c = n2.getReal(), d = n2.getIma();
        NumComplejo res = new NumComplejo();
        res.setReal(a * c - b * d);
        res.setIma(a * d + b * c);
        return res;
    }

    /* ---------------- EJERCICIO 2 ---------------- */

    public static NumComplejo Dividir(NumComplejo n1, NumComplejo n2) {
        // (a+bi)/(c+di) = [(ac+bd) + (bc-ad)i] / (c^2 + d^2)
        double a = n1.getReal(), b = n1.getIma();
        double c = n2.getReal(), d = n2.getIma();
        double denominador = (c * c) + (d * d);
        NumComplejo res = new NumComplejo();
        if (denominador != 0) {
            res.setReal((a * c + b * d) / denominador);
            res.setIma((b * c - a * d) / denominador);
        }
        return res;
    }

    public static NumComplejo Potencia(NumComplejo n1, int exponente) {
        // eleva un numero complejo a una potencia entera >= 0
        // (multiplicacion repetida partiendo del valor neutro 1+0i)
        NumComplejo res = new NumComplejo(1, 0);
        for (int i = 0; i < exponente; i++) {
            res = Multiplicar(res, n1);
        }
        return res;
    }
}
