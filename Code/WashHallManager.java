import java.time.*;

enum Operation{
   STANDARD_WASH,
   EARLY_STANDARD_WASH,
   ECONOMY_WASH,
   EARLY_ECONOMY_WASH,
   DELUX_WASH,
   ABORT,
   ABORT_RECHARGE,
   RECHARGE_MENU,
   RECHARGE_200,
   RECHARGE_500,
   RECHARGE_1000,
   INSERT_CREDITCARD_SCREEN,
   CREDITCARD_INSERTED,
   CARD_INSERTED,
   RECEIPT,
   NO_RECEIPT,
   START_WASH,
   FINISHED
}

class WashHallManager
{
    WashHallGUI gui;
    WashType economyWash = new WashType("economy", 50);
    WashType standardWash = new WashType("standard", 80);
    WashType deluxWash = new WashType("delux", 120);
    WashType earlyStandardWash = new WashType("earlyStandardWash", 40);
    WashType earlyEconomyWash = new WashType("earlyEconomyWash", 64);
    
    Account currentAccount = null;
   
   public WashHallManager(WashHallGUI _gui)
   {
      gui = _gui;
   }
   
   public void loadAccount () {
   //To do, getId from CardReader.
   //Load account from database.
   Account newAccount = new Account(2, 500);
   currentAccount = newAccount;
     
   }
   public boolean isEarlyBirdDay() {
         DayOfWeek day = currentAccount.getTimeStamp().getDayOfWeek();
         if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            return true;
         }
         else {
            return false;
         }         
   }
   public boolean isEarlyBirdTime() {
         LocalTime now = currentAccount.getTimeStamp().toLocalTime();
         LocalTime startTime = LocalTime.of(0,0);
         LocalTime endTime = LocalTime.of(13,59); 
         
         if (now.isAfter(startTime) && now.isBefore(endTime))
         {
            return true;
         }
         else {
            return false;
         }
   }
   
   public DisplayType getNormalOrEarlyMenu() {         
         if (isEarlyBirdDay() && isEarlyBirdTime()) {
         return DisplayType.EARLYBIRD_MENU;
         }
         else {
         return DisplayType.NORMAL_MENU;
         }
   }
   
   
   public DisplayType runCommand(Operation o)
   {
      DisplayType returnDisplay = DisplayType.INSERT_CARD_SCREEN;
      switch(o) {
         case CARD_INSERTED:
            loadAccount();          
            returnDisplay = getNormalOrEarlyMenu();         
            System.out.println("INSERT CARD");
            break;
         case STANDARD_WASH:
            System.out.println("standard wash");
	    returnDisplay = buyWash(standardWash);
            break;
         case ECONOMY_WASH:
            System.out.println("ECONOMY wash");
            returnDisplay = buyWash(economyWash);
            break;
         case DELUX_WASH:
            System.out.println("DELUX wash");
            returnDisplay = buyWash(deluxWash);
            break;
         case EARLY_STANDARD_WASH:
            System.out.println("EARLYSTANDARD wash");
            returnDisplay = buyWash(earlyStandardWash);
            break;
         case EARLY_ECONOMY_WASH:
            System.out.println("EARLYECONOMY wash");
            returnDisplay = buyWash(earlyEconomyWash);
            break;
         case ABORT:
            System.out.println("ABORT CALLED");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case ABORT_RECHARGE:
	     // TODO
	     // kald samme get menu som card inserted
	     // use getMenu function
            System.out.println("ABORT CALLED");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case RECHARGE_MENU:
            returnDisplay = DisplayType.RECHARGE_MENU;
            break;
         case RECHARGE_200:
	     // TODO
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
            break;
         case RECHARGE_500:
	     // TODO
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
            break;
         case RECHARGE_1000:
	     // TODO
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
            break;
         case INSERT_CREDITCARD_SCREEN:
	     // TODO
            System.out.println("INSERT_CREDITCARD_SCREEN");
            returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
            break;
         case CREDITCARD_INSERTED:
	     // TODO
         //Overfør penge til kort etc. hvis alt går godt, ellers vis en fejl
            System.out.println("CREDITCARD_INSERTED");
            returnDisplay = DisplayType.RECHARGE_FAILED_SCREEN;
            break;      
         case START_WASH:
            System.out.println("START WASH");
            returnDisplay = DisplayType.WAIT_SCREEN;
            break;
         case RECEIPT:
	     // TODO
	     // print salges gennem vores printerclass
            System.out.println("RECEIPT");
            returnDisplay = DisplayType.INSTRUCTION_SCREEN;
            break;
         case NO_RECEIPT:
            System.out.println("NO RECEIPT");
            returnDisplay = DisplayType.INSTRUCTION_SCREEN;
            break;
         case FINISHED:
	     // TODO
	     // selfdiagnistics
	     // reset all data
	     // back to insert wash card screen
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
      }
      return(returnDisplay);
   }
    
    public DisplayType buyWash(WashType wash) {
	// TODO 
	// tjek om der er kredit nok til den valgte vask
	// hvis ikke:
	// send til recharge menuen
	// ellers:
	// send til receipt menuen

	// skal sende den korrekte menu tilbage alt efter udfald
	return DisplayType.RECEIPT_MENU;
    }
       
   public Account getAccount()
   {
      System.out.println("returning account");   
      return(new Account(0, 0));
   }
   
   public String getWashDoneTime()
   {
      return("15:30");
   }
}
