package Fraction;
import ComplexSample.Complex;

public class Main
{
        public static void main(String[] args) throws ClassNotFoundException
        {
            Fraction<Integer> a = new Fraction<Integer>(2,4);
            Fraction<Integer> b = new Fraction<Integer>(1,3);
            Fraction<Float> c = new Fraction<Float>(1f, 5.1f);
            Fraction<Double> d = new Fraction<Double>(1.205d, 5.1d);
            Fraction<Integer> e = new Fraction<Integer>(3,7);
            Fraction<Float> f = new Fraction<Float>(5.26f, 11.4f);
            Fraction<Double> g = new Fraction<Double>(14.21d, 9.33d);
            Complex<Integer> comp = new Complex<Integer>(5,2);
            Complex<Integer> comp2 = new Complex<Integer>(6,-1);
            Fraction<Complex<Integer>> h = new Fraction<Complex<Integer>>(comp, comp2);
            Complex<Float> cc1 = new Complex<>(1.2f, 0.6f);
            Complex<Float> cc2 = new Complex<Float>(9f, 0f);
            Fraction<Complex<Float>> i = new Fraction<Complex<Float>>(cc1, cc2);
            Fraction<Integer> j = new Fraction<Integer>(13,70);
            Complex<Float> k = new Complex<>(1.2f, 4.5f);
            Fraction<Complex<Float>> l = new Fraction<>(k);
            System.out.println(l.toString());
//            System.out.println(a.toString());
//            System.out.println(b.toString());
//            a.add(b);
//            System.out.println(a.toString());
//            a.subtract(b);
//            System.out.println(a.toString());
//            a.multiply(b);
//            System.out.println(a.toString());
//            d.divide(b);
//            c.divide(d);
//            d.add(b);
//            e.divide(g);
//            c.multiply(e);
//            c.divide(f);
//            c.divide(g);
//            d.divide(e);
//            d.divide(f);
//            d.divide(g);
//            e.divide(b);
//            e.divide(f);
//            e.divide1(g);
            System.out.println(c.toString());
            System.out.println(d.toString());
            System.out.println(e.toString());
            System.out.println(f.toString());
            System.out.println(g.toString());
            System.out.println(h.toString());
            System.out.println(i.toString());
            System.out.println(j.toString());
//            d.multiply(g);
//            h.divide(g);
            d.divide(g);
//            i.subtract(h);
//            j.subtract(h);
//            h.divide(i);
//            j.multiply(h);
//            j.divide(h);
//            h.divide(i);
//            i.multiply(h);
//            i.divide(h);
//            System.out.println(i.toString());
//            i.multiply(h);
//            System.out.println(i.toString());
            System.out.println(d.toString());
        }
}