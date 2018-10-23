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

   public ArrayList<Integer> testConnection()
   {
      ResultSet rs = querySql("select * from accounts");
      ArrayList<Integer> accounts = new ArrayList<Integer>();
   
      try {
         while(rs.next()) {
            accounts.add(rs.getInt("Balance"));
         }
      } catch(SQLException ex) {
         System.out.println(ex.getMessage());
      }
   
      return accounts;
   }
    
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
    
    public void AddPurchase(Purchase p)
    {
	DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
	String dateTime = p.getWashTimeStamp().format(formatterNew).toString();
	// p.getWashTimeStamp().toString();

    	String purchaseString = 
	    Integer.toString(p.getAccountId()) + ", " + 
	    "\"" + p.getWashType() + "\"" + ", " + 
	    Double.toString(p.getWashPrice()) + ", " + 
	    "\"" + dateTime + "\"";
    	String sql = "insert into purchases (accountId, washtype, washprice, washtimestamp) values  (" + purchaseString + ");";
	// System.out.println(sql);

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
	// Connection conn = null;
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
	// Connection conn = null;
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
	
	// TODO find out how to pass the data 
	//so we can close the connection
	// finally {
	//     try {
	// 	conn.close();
	//     } catch (Exception e) {
	// 	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	// 	System.exit(0);
	//     }
	// }

	return rs;
    }

}

