import java.util.ArrayList;

public class databasetester
{
    public static void main(String argv[]) {

	WashHallDatabaseInterface wdb = new WashHallDatabaseInterface();
	ArrayList<Integer> accounts = wdb.testConnection();

	for(Integer i : accounts) {
	    System.out.println("it is = " + i.toString());
	}
	
	ArrayList<Purchase> purchases = wdb.getPurchases();
	for(Purchase p : purchases) {
	    System.out.println("id " + p.getId());
	}
	
	System.out.println("loading account 1");
	Account loadedAccount = wdb.getAccount(1);
	System.out.println("balance: " + loadedAccount.getBalance());
	
	Purchase p = new Purchase(1,"standard",80);
	wdb.AddPurchase(p);

    }
}
