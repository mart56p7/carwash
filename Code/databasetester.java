import java.util.ArrayList;

public class databasetester
{
    public static void main(String argv[]) {

	WashHallDatabaseInterface wdb = new WashHallDatabaseInterface();

	// ArrayList<Integer> accounts = wdb.testConnection();

	// for(Integer i : accounts) {
	    // System.out.printf("id = " + i.toString());
	// }
	
	// ArrayList<Purchase> purchases = wdb.getPurchases();
	// for(Purchase p : purchases) {
	//     System.out.printf("id:%d\ttype:%s\tprice:%f\ttime:%s\n", p.getId(), p.getWashType(), p.getWashPrice(), p.getWashTimeStamp().toString());
	// }
	
	// System.out.println("loading account 1");
	// Account loadedAccount = wdb.getAccount(1);
	// System.out.println("balance: " + loadedAccount.getBalance());
	
	// Purchase newPur = new Purchase(1,"standard",80);
	// wdb.AddPurchase(newPur);

	// purchases = wdb.getPurchases();
	// for(Purchase p : purchases) {
	//     System.out.printf("id:%d\ttype:%s\tprice:%f\ttime:%s\n", p.getId(), p.getWashType(), p.getWashPrice(), p.getWashTimeStamp().toString());
	// }
	
	Account loadedAccount = wdb.getAccount(1);
	System.out.println(loadedAccount.balance);
	wdb.setBalance(1, 1000);
	loadedAccount = wdb.getAccount(1);
	System.out.println(loadedAccount.balance);
	

    }
}
