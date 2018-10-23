import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Purchase
{

    int id;
    int accountId;
    String washType;
    double washPrice;
    LocalDateTime washTimeStamp;
    
    public Purchase(int accountId, String washType, double washPrice) {
	this.accountId = accountId;
	this.washType = washType;
	this.washPrice = washPrice;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	washTimeStamp = LocalDateTime.now();  
    }

    public Purchase(int id, int accountId, String washType, double washPrice, LocalDateTime washTimeStamp) {
	this.id = id;
	this.accountId = accountId;
	this.washType = washType;
	this.washPrice = washPrice;
	this.washTimeStamp = washTimeStamp;
    }

    public int getId()
    {
	return id;
    }
    
    public int getAccountId()
    {
	return accountId;
    }
    
    public String getWashType()
    {
	return washType;
    }
    
    public double getWashPrice()
    {
	return washPrice;
    }
    
    public LocalDateTime getWashTimeStamp()
    {
	return washTimeStamp;
    }
}
