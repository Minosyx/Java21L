package BetterComplex;

public class ComplexMathSimple
{
    private ComplexMathSimple() {}

    public static Complex add(Complex c1, Complex c2)
    {
        return new Complex(c1.getReal().doubleValue()+c2.getReal().doubleValue(),
                c1.getImaginary().doubleValue() + c2.getImaginary().doubleValue());
    }
}