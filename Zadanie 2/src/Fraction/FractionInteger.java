package Fraction;

public class FractionInteger implements FractionInterface<Integer>
{
    private Integer nominator;
    private Integer denominator;
    public FractionInteger()
    {
        this.nominator = 0;
        this.denominator = 1;
    }
    public FractionInteger(Integer nominator, Integer denominator)
    {
        this.nominator = nominator;;
        this.denominator = denominator;
    }
    @Override
    public Integer getNominator(){ return nominator; }

    @Override
    public Integer getDenominator(){ return denominator; }

    @Override
    public void setNominator( Integer nominator )
    {
        this.nominator = nominator;
    }

    @Override
    public void setDenominator( Integer denominator )
    {
        this.denominator = denominator;
    }

    public void add(){}
}
