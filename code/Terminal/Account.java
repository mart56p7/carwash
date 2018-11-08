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
        timestamp = LocalDateTime.now();
    }

    public double getBalance()
    {
        return(balance);
    }

    public int getId()
    {
        return(accountId);
    }

    public void setBalance(double _balance)
    {
        balance = _balance;
    }

    public LocalDateTime getTimeStamp () 
    {
        return (timestamp);
    }

    public void addBalance (double amount) 
    {
        balance += amount;
    }

    public void removeBalance (double amount) 
    {
        balance -= amount;
    }
}
