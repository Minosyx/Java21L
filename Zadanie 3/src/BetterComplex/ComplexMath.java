package BetterComplex;

public class ComplexMath
{
    private ComplexMath() {}

    public static Complex<? extends Number> add(Complex<? extends Number> c1, Complex<? extends Number> c2)
    {
        var real1 = c1.getReal();
        var real2 = c2.getReal();
        var imag1 = c1.getImaginary();
        var imag2 = c2.getImaginary();

        if ( real1 instanceof Double || real2 instanceof Double )
        {
            return new Complex<Double>(real1.doubleValue() + real2.doubleValue(),imag1.doubleValue() + imag2.doubleValue());
        }
        else if ( real1 instanceof Float || real2 instanceof Float )
        {
            return new Complex<Float>(real1.floatValue() + real2.floatValue(),imag1.floatValue() + imag2.floatValue());
        }
        else if ( real1 instanceof Long  || real2 instanceof Long )
        {
            return new Complex<Long>(real1.longValue() + real2.longValue(),imag1.longValue() + imag2.longValue());
        }
        else
        {
            return new Complex<Integer>(real1.intValue() + real2.intValue(), imag1.intValue() + imag2.intValue());
        }

        // A dlaczego bez shorta i byte'a?
        /*
        short x = 1;
        short y = 2;
        short z = x + y;  // błąd! - operator + zwraca int'a
        byte m = 1;
        byte n = 2;
        byte o = m + n;  // błąd! - operator + zwraca int'a
        */
    }

    public static Complex<? extends Number> sub(Complex<? extends Number> a, Complex<? extends Number> b)
    {
        var real1 = a.getReal();
        var real2 = b.getReal();
        var imag1 = a.getImaginary();
        var imag2 = b.getImaginary();

        if ( real1 instanceof Double || real2 instanceof Double )
        {
            return new Complex<Double>(real1.doubleValue() - real2.doubleValue(),imag1.doubleValue() - imag2.doubleValue());
        }
        else if ( real1 instanceof Float || real2 instanceof Float )
        {
            return new Complex<Float>(real1.floatValue() - real2.floatValue(),imag1.floatValue() - imag2.floatValue());
        }
        else if ( real1 instanceof Long  || real2 instanceof Long )
        {
            return new Complex<Long>(real1.longValue() - real2.longValue(),imag1.longValue() - imag2.longValue());
        }
        else
        {
            return new Complex<Integer>(real1.intValue() - real2.intValue(), imag1.intValue() - imag2.intValue());
        }
    }

    public static Complex<? extends Number> multiply(Complex<? extends Number> a, Complex<? extends Number> b)
    {
        var real1 = a.getReal();
        var real2 = b.getReal();
        var imag1 = a.getImaginary();
        var imag2 = b.getImaginary();

        if ( real1 instanceof Double || real2 instanceof Double )
        {
            return new Complex<Double>(real1.doubleValue() * real2.doubleValue() - imag1.doubleValue() * imag2.doubleValue(), real1.doubleValue() * imag2.doubleValue() + real2.doubleValue() * imag1.doubleValue());
        }
        else if ( real1 instanceof Float || real2 instanceof Float )
        {
            return new Complex<Float>(real1.floatValue() * real2.floatValue() - imag1.floatValue() * imag2.floatValue(), real1.floatValue() * imag2.floatValue() + real2.floatValue() * imag1.floatValue());
        }
        else if ( real1 instanceof Long  || real2 instanceof Long )
        {
            return new Complex<Long>(real1.longValue() * real2.longValue() - imag1.longValue() * imag2.longValue(), real1.longValue() * imag2.longValue() + real2.longValue() * imag1.longValue());
        }
        else
        {
            return new Complex<Integer>(real1.intValue() * real2.intValue() - imag1.intValue() * imag2.intValue(), real1.intValue() * imag2.intValue() + real2.intValue() * imag1.intValue());
        }
    }

    public static Complex<? extends Number> add(Complex<? extends Number> a, Number val)
    {
        var real = a.getReal();
        var imag = a.getImaginary();

        if ( real instanceof Double || val instanceof Double )
        {
            return new Complex<Double>( real.doubleValue() + val.doubleValue(), imag.doubleValue());
        }
        else if ( real instanceof Float || val instanceof Float )
        {
            return new Complex<Float>(real.floatValue() + val.floatValue(), imag.floatValue());
        }
        else if ( real instanceof Long || val instanceof Long )
        {
            return new Complex<Long>(real.longValue() + val.longValue(), imag.longValue());
        }
        else
        {
            return new Complex<Integer>(real.intValue() + val.intValue(), imag.intValue());
        }
    }

    public static Complex<? extends Number> multiply(Complex<? extends Number> a, Number val)
    {
        var real = a.getReal();
        var imag = a.getImaginary();

        if ( real instanceof Double || val instanceof Double )
        {
            return new Complex<Double>(real.doubleValue() * val.doubleValue(), imag.doubleValue() * val.doubleValue());
        }
        else if ( real instanceof Float || val instanceof Float )
        {
            return new Complex<Float>(real.floatValue() * val.floatValue(), imag.floatValue() * val.floatValue());
        }
        else if ( real instanceof Long || val instanceof Long )
        {
            return new Complex<Long>(real.longValue() * val.longValue(), imag.longValue() * val.longValue());
        }
        else
        {
            return new Complex<Integer>(real.intValue() * val.intValue(), imag.intValue() * val.intValue());
        }
    }

    public static Complex<? extends Number> changeType(Complex<? extends Number> c, Class<? extends Number> cls)
    {
        var real = c.getReal();
        var imag = c.getImaginary();

        if (cls == Complex.class)
        {
            return c;
        }
        else if (cls == Double.class)
        {
            return new Complex<Double>(real.doubleValue(), imag.doubleValue());
        }
        else if (cls == Float.class)
        {
            return new Complex<Float>(real.floatValue(), imag.floatValue());
        }
        else if (cls == Long.class)
        {
            return new Complex<Long>(real.longValue(), imag.longValue());
        }
        else
        {
            return new Complex<Integer>(real.intValue(), imag.intValue());
        }
    }
}