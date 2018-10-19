import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Account
{
   LocalDateTime timestamp;

   public Account()
   {
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
      return(0);
   }

}