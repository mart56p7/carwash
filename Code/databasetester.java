import java.util.ArrayList;

public class databasetester
{
    public static void main(String argv[]) {

	WashHallDatabaseInterface wdb = new WashHallDatabaseInterface();
	ArrayList<Integer> accounts = wdb.testConnection();

	for(Integer i : accounts) {
	    System.out.println("it is = " + i.toString());
	}
    }
}
