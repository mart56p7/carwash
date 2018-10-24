import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SQLTest
{
    public static void main(String args[])
    {
        System.out.println("hello there");
        // Class.forName("sqlite.JDBC");
        Connection conn = null;
        PreparedStatement pstmt = null;

        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
            String sql = "SELECT * FROM tb;";
            pstmt  = conn.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    ResultSet rs  = pstmt.executeQuery();
                    // loop through the result set
                    while (rs.next())
                    {
                        System.out.println(rs.getInt("Id") + "\t" + rs.getString("Name"));
                    }
                    conn.close();
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
