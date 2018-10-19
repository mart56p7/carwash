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
   
   public DisplayType runCommand(Operation o)
   {
      DisplayType returnDisplay = DisplayType.INSERT_CARD_SCREEN;
      switch(o) {
         case CARD_INSERTED:
	     // TODO 
	     /*
	       verify card
	       create account and set current
	       return correct menu depening on timestamp
	       use getMenu function
	      */
            System.out.println("INSERT CARD");
            returnDisplay = DisplayType.NORMAL_MENU;
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
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case RECHARGE_500:
	     // TODO
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
            break;
         case RECHARGE_1000:
	     // TODO
            System.out.println("RECHARGE");
            returnDisplay = DisplayType.INSERT_CARD_SCREEN;
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
    
    public void getMenu()
    {
	// hvis timestamp er inde for ...
	// returner earlybird menu
	// ellsers returner normal menu
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
