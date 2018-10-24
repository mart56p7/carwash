public class Printer
{
    public Printer()
    {
    }

    public void printReceipt(Purchase purchase)
    {
        System.out.println("Printing sale\n Time of purchase: " + purchase.getWashTimeStamp() + "\n Washtype: " + purchase.getWashType() + "\n Price: " + purchase.getWashPrice() + "\n AccountId: " + purchase.getAccountId());
    }
}
