public class WashHallDatabaseInterface
{
   public WashHallDatabaseInterface()
   {
   
   }
   
      
   public double getBalance(int accountId)
   {
      return(500);
   }
   
   public void setBalance(int accountId, double value)
   {
      //Do something
      System.out.println("WashHallDatabaseInterface setBalance accountId: " + accountId + " value: " + value);
   }  
   
   public void registerSale(Sale sale)
   {
      //Do something
      System.out.println("WashHallDatabaseInterface registerSale WashType: " + sale.getWashType().getName() + " Price: " + sale.getPrice());
   }
}