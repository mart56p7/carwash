import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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
    
    // Connection getConnection()
    // {
    // 	Connection conn = null;
    // 	try {
    // 	    conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
    // 	} catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     } finally {
    // 	    return conn;
    // 	}
    // }
    
    // ResultSet querySql(String sql)
    // {
    // 	Connection conn = null;
    // 	conn = getConnection();
    // 	PreparedStatement pstmt = null;
    // 	ResultSet rs = null;

    // 	try {
    // 	    pstmt = conn.prepareStatement(sql);
    // 	    rs = pstmt.executeQuery();
    // 	} catch (SQLException e) {
    // 	    System.out.println(e.getMessage());
    // 	} 	
	
    // 	return rs;
    // }

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
    
    

    ResultSet querySql(String sql)
    {
	Connection conn = null;
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
