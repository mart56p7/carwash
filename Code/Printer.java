public class Printer
{
   public Printer()
   {
   
   }
   
   public void print(Sale sale)
   {
      System.out.println("Printing sale timestamp: " + sale.getTime() + " WashType: " + sale.getWashType() + " Price: " + sale.getPrice() + " AccountId: " + sale.getAccountId());
   }
}