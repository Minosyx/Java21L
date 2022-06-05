package ComplexSample;

public class Complex<T>
{
    private T re;
    private T im;

    public Complex()
    {
        this.re = (T) Integer.valueOf(0);
        this.im = (T) Integer.valueOf(0);
    }

    public Complex(T re, T im)
    {
        this.im = im;
        this.re = re;
    }

    /* settery i gettery */

    public void setRe(T re)
    {
        this.re = re;
    }

    public T getRe()
    {
        return this.re;
    }

    public void setIm(T im)
    {
        this.im = im;
    }

    public T getIm()
    {
        return this.im;
    }

    /* metody publiczne */

    public double module()
    {
        Double re = (Double)this.re;
        Double im = (Double)this.im;

        return Math.sqrt( re*re + im*im );
    }

    public void add( Complex complex )
    {
        this.re = (T) (Number)(((Number)complex.getRe()).doubleValue() + ((Number)this.re).doubleValue());
        this.im = (T) (Number)(((Number)complex.getIm()).doubleValue() + ((Number)this.im).doubleValue());
    }

    public void multiply( Complex complex )
    {
        Double x1 = ((Number)this.getRe()).doubleValue();
        Double y1 = ((Number)this.getIm()).doubleValue();
        Double x2 = ((Number)complex.getRe()).doubleValue();
        Double y2 = ((Number)complex.getIm()).doubleValue();

        this.re = (T) (Number)(x1 * x2 - y1 * y2);
        this.im = (T) (Number)(x1 * y2 + x2 * y1);
    }

    @Override
    public String toString()
    {
        return this.re + " + " + this.im + "i";
    }

    public String printTypes()
    {
        return "real: " + this.re.getClass().getName() + ", imaginary: " + this.im.getClass().getName();
    }
}
