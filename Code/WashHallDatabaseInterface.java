import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class WashHallDatabaseInterface
{
    
    Connection conn = null;
      
    public ArrayList<Purchase> getPurchases()
    {
	ResultSet rs = querySql("select * from Purchases");
	ArrayList<Purchase> purchases = new ArrayList<Purchase>();
   
	try {
	    while(rs.next()) {
		DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
		LocalDateTime d = LocalDateTime.parse(rs.getString("washtimestamp"), formatterNew);
		System.out.println(d.toString());
         
		Purchase p = new Purchase(rs.getInt("id"),
					  rs.getInt("accountId"),
					  rs.getString("washType"),
					  rs.getDouble("washPrice"),
					  d);
		purchases.add(p);
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
      
	try {
	    if(conn != null) {
		rs.close();
		conn.close();
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
   
	return purchases;
    }

    public ArrayList<Purchase> getPurchasesDayMonthYear(int day, int month, int year)
    {
	ResultSet rs = querySql("select * from purchases where strftime('%d', washtimestamp) = '" + day + "' and strftime('%m', washtimestamp) = '" + month + "' and strftime('%Y', washtimestamp) = '" + year + "'");
	ArrayList<Purchase> purchases = new ArrayList<Purchase>();
   
	try {
	    while(rs.next()) {
		DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
		LocalDateTime d = LocalDateTime.parse(rs.getString("washtimestamp"), formatterNew);
		System.out.println(d.toString());
         
		Purchase p = new Purchase(rs.getInt("id"),
					  rs.getInt("accountId"),
					  rs.getString("washType"),
					  rs.getDouble("washPrice"),
					  d);
		purchases.add(p);
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
   
	return purchases;
    }
    
    public ArrayList<Purchase> getPurchasesMonthYear(int month, int year)
    {
	ResultSet rs = querySql("select * from Purchases where strftime('%m', `washtimestamp`) = " + month + " and strftime('%Y', `washtimestamp`) = " + year);
	ArrayList<Purchase> purchases = new ArrayList<Purchase>();
   
	try {
	    while(rs.next()) {
		DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
		LocalDateTime d = LocalDateTime.parse(rs.getString("washtimestamp"), formatterNew);
		System.out.println(d.toString());
         
		Purchase p = new Purchase(rs.getInt("id"),
					  rs.getInt("accountId"),
					  rs.getString("washType"),
					  rs.getDouble("washPrice"),
					  d);
		purchases.add(p);
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
      
	return purchases;
    }
    
    public Account getAccount(int id)
    {
	ResultSet rs = querySql("select * from accounts where id = " + Integer.toString(id));
	Account loadedAccount = null;
	try {
	    loadedAccount = new Account(rs.getInt("Id"), rs.getDouble("Balance"));
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}

	try {
	    if(conn != null) {
		rs.close();
		conn.close();
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}

	return loadedAccount;
    }

    public void setBalance(int accountId, double value)
    {
	String sql = "update accounts set balance = " + value + " where id = " + accountId;
	executeSql(sql);
    }  
    
    public void addPurchase(Purchase p)
    {
	DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
	String dateTime = p.getWashTimeStamp().format(formatterNew).toString();
    	String purchaseString = 
	    Integer.toString(p.getAccountId()) + ", " + 
	    "\"" + p.getWashType() + "\"" + ", " + 
	    Double.toString(p.getWashPrice()) + ", " + 
	    "\"" + dateTime + "\"";
    	String sql = "insert into purchases (accountId, washtype, washprice, washtimestamp) values  (" + purchaseString + ");";
	executeSql(sql);

	try {
	    if(conn != null) {
		conn.close();
	    }
	} catch(Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }
    
    void executeSql(String sql)
    {
	PreparedStatement pstmt = null;
	
	try {
	    conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
	    System.out.println("succesfully opened database");

	    pstmt = conn.prepareStatement(sql);
	    pstmt.executeUpdate();
	} 
	catch (Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	} 
    }

    ResultSet querySql(String sql)
    {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
	    conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
	    System.out.println("succesfully opened database");

	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();

	    return rs;
	} 
	catch (Exception e) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	} 
	
	return rs;
    }

}
    
    

