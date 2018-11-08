public class WashType
{
    private String name;
    private double price;

    public WashType(String _name, double _price)
    {
        name = _name;
        price = _price;
    }

    public String getName()
    {
        return(name);
    }

    public double getPrice()
    {
        return(price);
    }
}
