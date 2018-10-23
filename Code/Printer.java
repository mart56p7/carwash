public class Printer
{
   public Printer()
   {
   
   }
   
   public void printReceipt(Purchase purchase)
   {
      System.out.println("Printing sale timestamp: " + purchase.getWashTimeStamp() + " WashType: " + purchase.getWashType() + " Price: " + purchase.getWashPrice() + " AccountId: " + purchase.getAccountId());
   }
}