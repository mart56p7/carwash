enum Operation{
   STANDARD_WASH,
   EARLY_STANDARD_WASH,
   ECONOMY_WASH,
   EARLY_ECONOMY_WASH,
   DELUX_WASH,
   ABORT,
   RECHARGE,
   CARD_INSERTED,
   RECEIPT,
   NO_RECEIPT,
   START_WASH
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
   
   public DisplayType runCommand(Operation o)
   {
      DisplayType returnDisplay = DisplayType.INSERT_CARD_SCREEN;
      switch(o) {
         case STANDARD_WASH:
            System.out.println("standard wash");
	    buyWash(standardWash);
            returnDisplay = DisplayType.RECEIPT_MENU;
            break;
         case ECONOMY_WASH:
            System.out.println("ECONOMY wash");
	    buyWash(economyWash);
            returnDisplay = DisplayType.RECEIPT_MENU;
            break;
         case DELUX_WASH:
            System.out.println("DELUX wash");
	    buyWash(deluxWash);
            returnDisplay = DisplayType.RECEIPT_MENU;
            break;
         case EARLY_STANDARD_WASH:
            System.out.println("EARLYSTANDARD wash");
	    buyWash(earlyStandardWash);
            returnDisplay = DisplayType.RECEIPT_MENU;
            break;
         case EARLY_ECONOMY_WASH:
            System.out.println("EARLYECONOMY wash");
	    buyWash(earlyEconomyWash);
            returnDisplay = DisplayType.RECEIPT_MENU;
            break;
         case ABORT:
            System.out.println("ABORT CALLED");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case RECHARGE:
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case CARD_INSERTED:
            System.out.println("INSERT CARD");
	    loadAccount(1);
            returnDisplay = DisplayType.NORMAL_MENU;
            break;
         case START_WASH:
            System.out.println("START WASH");
            returnDisplay = DisplayType.WAIT_SCREEN;
            break;
         case RECEIPT:
            System.out.println("RECEIPT");
            returnDisplay = DisplayType.INSTRUCTION_SCREEN;
            break;
         case NO_RECEIPT:
            System.out.println("NO RECEIPT");
            returnDisplay = DisplayType.INSTRUCTION_SCREEN;
            break;
      }
      return(returnDisplay);
   }
    
    public void buyWash(WashType wash) {
	// if(wash.price > credit) {
	    // success
	// }
	// else {
	    // ikke nok penge
	// }
    }
    
    public void getMenu()
    {
	// hvis timestamp er inde for ...
	// returner earlybird menu
	// ellsers returner normal menu
    }
    
    public void loadAccount(int id)
    {
	// currentAccount = new Account(id, 300);
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
