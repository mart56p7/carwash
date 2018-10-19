public class DBConnector
{
   public DBConnector()
   {
   
   }
   
      
   public double getBalance(int accountId)
   {
      return(500);
   }
   
   public void setBalance(int accountId, double value)
   {
      //Do something
      System.out.println("DBConnector setBalance accountId: " + accountId + " value: " + value);
   }  
   
   public void registerSale(Sale sale)
   {
      //Do something
      System.out.println("DBConnector registerSale WashType: " + sale.getWashType().getName() + " Price: " + sale.getPrice());
   }
}