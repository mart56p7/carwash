public class Sale
{
    private int time;
    private WashType washtype;
    private int accountId;

    public Sale(int _time, WashType _washtype, int _accountId)
    {
        time = _time;
        washtype = _washtype;
        accountId = _accountId;
    }

    public int getTime()
    {
        return(time);
    }

    public WashType getWashType()
    {
        return(washtype);
    }

    public double getPrice()
    {
        return(washtype.getPrice());
    }

    public int getAccountId()
    {
        return(accountId);
    }
}
