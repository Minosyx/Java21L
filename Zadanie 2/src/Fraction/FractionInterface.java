package Fraction;

public interface FractionInterface<T>
{
    T getNominator();
    T getDenominator();
    void setNominator(T nominator);
    void setDenominator(T denominator);
}
