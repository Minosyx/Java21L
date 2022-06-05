package Fraction;
import ComplexSample.Complex;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.math.BigDecimal;

public class Fraction<F>
{
    private F numerator;
    private F denominator;

    public Fraction()
    {
        this.numerator = (F) Integer.valueOf(0);
        this.denominator = (F) Integer.valueOf(1);
    }

    public Fraction(F num) throws ClassNotFoundException
    {
        this.numerator = num;
        if (!(this.numerator instanceof Complex))
        {
            this.denominator = (F) Integer.valueOf(1);
            if(!Class.forName("java.lang.Integer").isInstance(this.numerator))
                expandDen();
            shorten();
        }
        else {
            this.denominator = (F) new Complex<>(1, 0);
            if(!Class.forName("java.lang.Integer").isInstance(((Complex)this.numerator).getRe()))
                expandComplex();
            simplifyComplex();
            shortenComplex();
        }
    }

    public Fraction(F numerator, F denominator) throws ClassNotFoundException
    {
        this.numerator = numerator;
        this.denominator = denominator;
        if(!(this.denominator instanceof Complex) && ((Number)this.denominator).doubleValue() == 0.0)
        {
            throw new ArithmeticException("Division by zero!!!");
        }
        else if ((this.denominator instanceof Complex) && (((Number)((Complex<?>) this.denominator).getRe()).doubleValue() == 0.0) && (((Number)((Complex<?>) this.denominator).getIm()).doubleValue() == 0.0))
        {
            throw new ArithmeticException("Division by zero!!!");
        }
        if (!(this.numerator instanceof Complex))
        {
            if(!Class.forName("java.lang.Integer").isInstance(this.numerator))
                expandDen();
            shorten();
        }
        else {
            if(!Class.forName("java.lang.Integer").isInstance(((Complex)this.numerator).getRe()))
                expandComplex();
            simplifyComplex();
            shortenComplex();
        }
    }

    public F getNumerator()
    {
        return this.numerator;
    }

    public F getDenominator()
    {
        return this.denominator;
    }

    public void setNumerator(F numerator)
    {
        this.numerator = numerator;
    }

    public void setDenominator(F denominator)
    {
        this.denominator = denominator;
    }

    private void expandDen() throws ClassNotFoundException
    {
        int refscale = 0;
        if (Class.forName("java.lang.Float").isInstance(this.numerator))
            refscale = 6;
        else
            refscale = 14;
        BigDecimal bd = BigDecimal.valueOf(((Number) this.denominator).doubleValue());
        BigDecimal bn = BigDecimal.valueOf(((Number) this.numerator).doubleValue());
        if (refscale == 6) {
            bd = bd.setScale(refscale, RoundingMode.HALF_UP);
            bn = bn.setScale(refscale, RoundingMode.HALF_UP);
        }
        int scale = Math.min(Math.max(bd.scale(), bn.scale()), refscale);
        this.denominator = (F) (Integer) (int) (bd.doubleValue() * Math.pow(10, scale));
        this.numerator = (F) (Integer) (int) (bn.doubleValue() * Math.pow(10, scale));
    }

    private void expandDen(F target)
    {
        F tmp = this.denominator;
        this.denominator = target;
        this.numerator = (F) (Integer)(((Number)target).intValue() / ((Number)tmp).intValue() * ((Number)this.numerator).intValue());
    }

    private void expandComplex() throws ClassNotFoundException
    {
        int refscale;
        if(Class.forName("java.lang.Float").isInstance(((Complex<?>)this.numerator).getRe()))
            refscale = 6;
        else
            refscale = 14;
        BigDecimal bnRe = BigDecimal.valueOf(((Number)((Complex<?>) this.numerator).getRe()).doubleValue());
        BigDecimal bnIm = BigDecimal.valueOf(((Number)((Complex<?>) this.numerator).getIm()).doubleValue());
        BigDecimal bdRe = BigDecimal.valueOf(((Number)((Complex<?>) this.denominator).getRe()).doubleValue());
        BigDecimal bdIm = BigDecimal.valueOf(((Number)((Complex<?>) this.denominator).getIm()).doubleValue());
        if (refscale == 6){
            bnRe = bnRe.setScale(refscale, RoundingMode.HALF_UP);
            bnIm = bnIm.setScale(refscale, RoundingMode.HALF_UP);
            bdRe = bdRe.setScale(refscale, RoundingMode.HALF_UP);
            bdIm = bdIm.setScale(refscale, RoundingMode.HALF_UP);
        }
        int max = Math.max(Math.max(Math.max(bdRe.scale(), bdIm.scale()), bnIm.scale()), bnRe.scale());
        int scale = Math.min(max, refscale);
        Integer newNRE = (Integer) (int) (bnRe.doubleValue() * Math.pow(10, scale));
        Integer newNIM = (Integer) (int) (bnIm.doubleValue() * Math.pow(10, scale));
        Integer newDRE = (Integer) (int) (bdRe.doubleValue() * Math.pow(10, scale));
        Integer newDIM = (Integer) (int) (bdIm.doubleValue() * Math.pow(10, scale));
        Complex<Integer> nnum = new Complex<>(newNRE, newNIM);
        Complex<Integer> nden = new Complex<>(newDRE, newDIM);
        this.numerator = (F) nnum;
        this.denominator = (F) nden;
    }

    private void expandComplex(Complex<Integer> target)
    {
        Complex<Integer> tmp = (Complex<Integer>) this.denominator;
        this.denominator = (F) target;
        ((Complex<Integer>)this.numerator).multiply(target);
        ((Complex)this.numerator).setRe(((Number)((Complex)this.numerator).getRe()).intValue() / tmp.getRe());
        ((Complex)this.numerator).setIm(((Number)((Complex)this.numerator).getIm()).intValue() / tmp.getRe());
    }

    private F gcd(F a, F b)
    {
        if (((Number)b).intValue() == 0)
        {
            return a;
        }
        else
        {
            F tmp = (F) (Number)(((Number)a).intValue() % ((Number)b).intValue());
            return gcd(b, tmp);
        }
    }

    private void shorten()
    {
        F gcden = gcd(this.numerator, this.denominator);
        this.numerator = (F) (Integer)(((Number)this.numerator).intValue() / ((Number)gcden).intValue());
        this.denominator = (F) (Integer)(((Number)this.denominator).intValue() / ((Number)gcden).intValue());
    }

    private void shortenComplex()
    {
        F tmp = gcd((F)((Complex<Integer>) this.numerator).getRe(), (F)((Complex<Integer>) this.numerator).getIm());
        F gcden = gcd(tmp, (F) ((Complex<Integer>) this.denominator).getRe());
        ((Complex<Integer>) this.numerator).setRe(((Number)((Complex<Integer>) this.numerator).getRe()).intValue() / ((Number)gcden).intValue());
        ((Complex<Integer>) this.numerator).setIm(((Number)((Complex<Integer>) this.numerator).getIm()).intValue() / ((Number)gcden).intValue());
        ((Complex<Integer>) this.denominator).setRe(((Number)((Complex<Integer>) this.denominator).getRe()).intValue() / ((Number)gcden).intValue());
    }

    public void simplifyComplex()
    {
        Object a = ((Complex<?>) this.denominator).getRe();
        Object b = ((Complex<?>) this.denominator).getIm();
        if (((Number)b).intValue() == 0)
            return;
        Complex<Integer> tmp = new Complex<>(((Number) a).intValue(), -(Integer) ((Number) b).intValue());
        Integer nden = (Integer) (int) (Math.pow(((Number) a).intValue(), 2) + Math.pow(((Number) b).intValue(), 2));
        ((Complex)this.numerator).multiply(tmp);
        this.denominator = (F) new Complex<>(nden, 0);
    }

    public void add(Fraction fract) throws ClassNotFoundException
    {
        Integer a, b;
        if (this.numerator instanceof Complex && fract.numerator instanceof Complex)
        {
            a = (Integer) ((Complex)this.denominator).getRe();
            b = (Integer) ((Complex)fract.denominator).getRe();
            Fraction fractmp = new Fraction(fract.numerator, fract.denominator);
            if(!a.equals(b)){
                Integer target = a * b / ((Number)gcd((F) a, (F) b)).intValue();
                this.expandComplex(new Complex<>(target, 0));
                fractmp.expandComplex(new Complex<>(target, 0));
            }
            ((Complex)this.numerator).add((Complex)fractmp.numerator);
            shortenComplex();
        }
        else if (this.numerator instanceof Complex || fract.numerator instanceof Complex)
        {
            Integer target = 0;
            Fraction comp = (this.numerator instanceof Complex) ? this : fract;
            Fraction num = (this.numerator instanceof Complex) ? fract : this;
            a = (Integer) ((Complex)comp.denominator).getRe();
            b = ((Number)num.denominator).intValue();
            Fraction fractmp;
            if(this == comp)
                fractmp = new Fraction(num.numerator, num.denominator);
            else
                fractmp = new Fraction(fract.numerator, fract.denominator);
            if(!a.equals(b)){
                target = a * b / ((Number)gcd((F) a, (F) b)).intValue();
                if(this == comp) {
                    comp.expandComplex(new Complex<>(target, 0));
                    fractmp.expandDen(target);
                }
                else {
                    num.expandDen(target);
                    fractmp.expandComplex(new Complex<>(target, 0));
                }
            }
            if (this == comp)
                this.numerator = (F) new Complex<>(((Number) ((Complex) comp.numerator).getRe()).intValue() + ((Number) fractmp.numerator).intValue(), ((Number) ((Complex) comp.numerator).getIm()).intValue());
            else
                this.numerator = (F) new Complex<>(((Number) ((Complex) fractmp.numerator).getRe()).intValue() + ((Number) num.numerator).intValue(), ((Number) ((Complex) fractmp.numerator).getIm()).intValue());
            this.denominator = (F) new Complex<>(target, 0);
            shortenComplex();
        }
        else
        {
            Fraction fractmp = new Fraction(fract.numerator, fract.denominator);
            if (((Number) this.denominator).intValue() != ((Number) fract.denominator).intValue()){
                a = ((Number)this.denominator).intValue();
                b = ((Number)fract.denominator).intValue();
                Integer target =  (a * b / ((Number)gcd((F) a, (F) b)).intValue());
                fractmp.expandDen(target);
                this.expandDen((F) target);
            }
            this.numerator = (F) (Integer) (((Number) this.numerator).intValue() + ((Number) fractmp.numerator).intValue());
            shorten();
        }
    }

    public void subtract(Fraction fract) throws ClassNotFoundException
    {
        Fraction tmp;
        if (fract.numerator instanceof Complex)
        {
            Complex cmplx = new Complex<>(-((Number) ((Complex) fract.numerator).getRe()).intValue(), -((Number) ((Complex) fract.numerator).getIm()).intValue());
            tmp = new Fraction<Complex<Integer>>(cmplx, (Complex)fract.denominator);
        }
        else
        {
            tmp = new Fraction(-((Number)fract.numerator).intValue(), ((Number)fract.denominator).intValue());
        }
        this.add(tmp);
    }

    public void multiply(Fraction fract)
    {
        if (this.numerator instanceof Complex && fract.numerator instanceof Complex)
        {
            ((Complex)this.numerator).multiply((Complex)fract.numerator);
            ((Complex)this.denominator).multiply((Complex)fract.denominator);
            simplifyComplex();
            shortenComplex();
        }
        else if (this.numerator instanceof Complex || fract.numerator instanceof Complex)
        {
            Fraction comp = (this.numerator instanceof Complex) ? this : fract;
            Fraction num = (this.numerator instanceof Complex) ? fract : this;
            if (this == comp) {
                ((Complex) this.numerator).multiply(new Complex<>(((Number) num.numerator).intValue(), 0));
                ((Complex) this.denominator).multiply(new Complex<>(((Number) num.denominator).intValue(), 0));
            }
            else{
                this.numerator = (F) new Complex<>(((Number) this.numerator).intValue(), 0);
                ((Complex)this.numerator).multiply((Complex)comp.numerator);
                this.denominator = (F) new Complex<>(((Number) this.denominator).intValue(), 0);
                ((Complex)this.denominator).multiply((Complex)comp.denominator);
            }
            simplifyComplex();
            shortenComplex();
        }
        else {
            this.numerator = (F) (Integer) (((Number) this.numerator).intValue() * ((Number) fract.numerator).intValue());
            this.denominator = (F) (Integer) (((Number) this.denominator).intValue() * ((Number) fract.denominator).intValue());
            shorten();
        }
    }

    public void divide(Fraction fract) throws ClassNotFoundException
    {
        Fraction tmp;
        tmp = new Fraction<>(fract.denominator, fract.numerator);
        this.multiply(tmp);
    }

    @Override
    public String toString()
    {
        Object[] params;
        if (this.numerator instanceof Complex) {
            params = new Object[]{this.numerator, ((Complex) this.denominator).getRe()};
            return MessageFormat.format("({0}) / {1}", params);
        }
        else {
            params = new Object[]{this.numerator, this.denominator};
            return MessageFormat.format("{0} / {1}", params);
        }
    }
}