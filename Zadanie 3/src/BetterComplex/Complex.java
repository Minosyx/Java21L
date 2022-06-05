package BetterComplex;

public class Complex<T extends Number> extends Number
{
    private T real;
    private T imaginary;

    public Complex()
    {
        this.real = (T) Integer.valueOf(0);
        this.imaginary = (T) Integer.valueOf(0);
    }

    public Complex(T real, T imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    // SETTERY I GETTERY
    public void setImaginary(T imaginary)
    {
        this.imaginary = imaginary;
    }

    public Number getImaginary()
    {
        return imaginary;
    }

    public void setReal(T real)
    {
        this.real = real;
    }

    public Number getReal()
    {
        return real;
    }

    // Nadpisane METODY z klasy bazowej
    @Override
    public int intValue()
    {
        return this.real.intValue();
    }

    @Override
    public long longValue()
    {
        return this.real.longValue();
    }

    @Override
    public float floatValue()
    {
        return this.real.floatValue();
    }

    @Override
    public double doubleValue()
    {
        return this.real.doubleValue();
    }

    public String toString()
    {
        return this.real.toString() + "+" + this.imaginary.toString() + "i";
    }

    public T module()
    {
        var mod = (Number)(Math.sqrt(Math.pow(this.real.doubleValue(), 2) + Math.pow(this.imaginary.doubleValue(), 2)));
        if (this.real instanceof Double)
            return (T) mod;
        else if (this.real instanceof Float)
            return (T) (Number) mod.floatValue();
        else if (this.real instanceof Long)
            return (T) (Number) mod.longValue();
        else
            return (T) (Number) mod.intValue();
    }

    public void add(Complex<? extends Number> c)
    {

        if (this.real instanceof Double)
        {
            this.real = (T) (Number) (this.real.doubleValue() + c.real.doubleValue());
            this.imaginary = (T) (Number) (this.imaginary.doubleValue() + c.imaginary.doubleValue());
        }
        else if (this.real instanceof Float)
        {
            this.real = (T) (Number) (this.real.floatValue() + c.real.floatValue());
            this.imaginary = (T) (Number) (this.imaginary.floatValue() + c.imaginary.floatValue());
        }
        else if (this.real instanceof Long)
        {

            if (c.real instanceof Double || c.real instanceof Float)
            {
                this.real = (T) (Number) (this.real.longValue() + Math.round(c.real.doubleValue())) ;
                this.imaginary = (T) (Number) (this.imaginary.longValue() + Math.round(c.imaginary.doubleValue()));
            }
            else
            {
                this.real = (T) (Number) (this.real.longValue() + this.real.longValue());
                this.imaginary = (T) (Number) (this.imaginary.longValue() + c.imaginary.longValue());
            }
        }
        else
        {
            if (c.real instanceof Double || c.real instanceof Float)
            {
                this.real = (T) (Number) (this.real.intValue() + ((Number) Math.round(c.real.doubleValue())).intValue());
                this.imaginary = (T) (Number) (this.imaginary.intValue() + ((Number) Math.round(c.imaginary.doubleValue())).intValue());
            }
            else
            {
                this.real = (T) (Number) (this.real.intValue() + c.real.intValue());
                this.imaginary = (T) (Number) (this.imaginary.intValue() + c.imaginary.intValue());
            }
        }

    }

    public void add(Number val)
    {
        if (this.real instanceof Double)
            this.real = (T) (Number) (this.real.doubleValue() + val.doubleValue());
        else if (this.real instanceof Float)
            this.real = (T) (Number) (this.real.floatValue() + val.floatValue());
        else if (this.real instanceof Long)
        {
            if (val instanceof Double || val instanceof Float)
                this.real = (T) (Number) (this.real.longValue() + Math.round(val.doubleValue()));
            else
                this.real = (T) (Number) (this.real.longValue() + val.longValue());
        }
        else
        {
            if (val instanceof Double || val instanceof Float)
                this.real = (T) (Number) (this.real.intValue() + ((Number) Math.round(val.doubleValue())).intValue());
            else
                this.real = (T) (Number) (this.real.intValue() + val.intValue());
        }
    }

    public void multiply(Complex<? extends Number> c)
    {
        T tmp;
        tmp = this.real;
        if (this.real instanceof Double)
        {
            this.real = (T) (Number) (this.real.doubleValue() * c.real.doubleValue() - this.imaginary.doubleValue() + c.imaginary.doubleValue());
            this.imaginary = (T) (Number) (tmp.doubleValue() * c.imaginary.doubleValue() + c.real.doubleValue() * this.imaginary.doubleValue());
        }
        else if (this.real instanceof Float)
        {
            this.real = (T) (Number) (this.real.floatValue() * c.real.floatValue() - this.imaginary.floatValue() + c.imaginary.floatValue());
            this.imaginary = (T) (Number) (tmp.floatValue() * c.imaginary.floatValue() + c.real.floatValue() * this.imaginary.floatValue());
        }
        else if (this.real instanceof Long)
        {
            if (c.real instanceof Double || c.real instanceof Float)
            {
                this.real = (T) (Number) (this.real.longValue() * Math.round(c.real.doubleValue()) - this.imaginary.longValue() * Math.round(c.imaginary.doubleValue()));
                this.imaginary = (T) (Number) (tmp.longValue() * Math.round(c.imaginary.doubleValue()) + this.imaginary.longValue() * Math.round(c.real.doubleValue()));
            }
            else
            {
                this.real = (T) (Number) (this.real.longValue() * c.real.longValue() - this.imaginary.longValue() * c.imaginary.longValue());
                this.imaginary = (T) (Number) (tmp.longValue() * c.imaginary.longValue() + this.imaginary.longValue() * c.real.longValue());
            }
        }
        else
        {
            if (c.real instanceof Double || c.real instanceof Float)
            {
                this.real = (T) (Number) (this.real.intValue() * ((Number) Math.round(c.real.doubleValue())).intValue() - this.imaginary.intValue() * ((Number) Math.round(c.imaginary.doubleValue())).intValue());
                this.imaginary = (T) (Number) (tmp.intValue() * ((Number) Math.round(c.imaginary.doubleValue())).intValue() + this.imaginary.intValue() * ((Number) Math.round(c.real.doubleValue())).intValue());
            }
            else
            {
                this.real = (T) (Number) (this.real.intValue() * c.real.intValue() - this.imaginary.intValue() * c.imaginary.intValue());
                this.imaginary = (T) (Number) (tmp.intValue() * c.imaginary.intValue() + this.imaginary.intValue() * c.real.intValue());
            }
        }
    }

    public void multiply(Number val)
    {
        if (this.real instanceof Double)
        {
            this.real = (T) (Number) (this.real.doubleValue() * val.doubleValue());
            this.imaginary = (T) (Number) (this.imaginary.doubleValue() * val.doubleValue());
        }
        else if (this.real instanceof Float)
        {
            this.real = (T) (Number) (this.real.floatValue() * val.floatValue());
            this.imaginary = (T) (Number) (this.imaginary.floatValue() * val.floatValue());
        }
        else if (this.real instanceof Long)
        {
            if (val instanceof Double || val instanceof Float)
            {
                this.real = (T) (Number) (this.real.longValue() * Math.round(val.doubleValue()));
                this.imaginary = (T) (Number) (this.imaginary.longValue() * Math.round(val.doubleValue()));
            }
            else
            {
                this.real = (T) (Number) (this.real.longValue() * val.longValue());
                this.imaginary = (T) (Number) (this.imaginary.longValue() * val.longValue());
            }
        }
        else
        {
            if (val instanceof Double || val instanceof Float)
            {
                this.real = (T) (Number) (this.real.intValue() * ((Number) Math.round(val.doubleValue())).intValue());
                this.imaginary = (T) (Number) (this.imaginary.intValue() * ((Number) Math.round(val.doubleValue())).intValue());
            }
            else
            {
                this.real = (T) (Number) (this.real.intValue() * val.intValue());
                this.imaginary = (T) (Number) (this.imaginary.intValue() * val.intValue());
            }
        }
    }
}