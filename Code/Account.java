import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Account
{
   LocalDateTime timestamp;
   double balance;
   int accountId;

   public Account(int _accountId, double _balance)
   {
      accountId = _accountId;
      balance = _balance;
      setSessionTimeStamp();
   }

   public void setSessionTimeStamp()
   {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
      LocalDateTime timestamp = LocalDateTime.now();  
      //System.out.println(dtf.format(now));
   }
   
   public double getBalance()
   {
      return(balance);
   }
   
   public int getId()
   {
      return(accountId);
   }
   
   public void setBalanace(double _balance)
   {
      balance = _balance;
   }
}