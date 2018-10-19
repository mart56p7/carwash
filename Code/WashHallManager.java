class WashHallManager
{
   WashHallGUI gui;
   
   public WashHallManager(WashHallGUI _gui)
   {
      gui = _gui;
   }
   
   public int runCommand(Operation o)
   {
       int returnDisplay = 0;
       switch(o) {
       case STANDARD_WASH:
	   System.out.println("standard wash");
	   returnDisplay = 3;
	   break;
       case ECONOMY_WASH:
	   System.out.println("ECONOMY wash");
	   returnDisplay = 3;
	   break;
       case DELUX_WASH:
	   System.out.println("DELUX wash");
	   returnDisplay = 3;
	   break;
       case EARLY_STANDARD_WASH:
	   System.out.println("EARLYSTANDARD wash");
	   returnDisplay = 3;
	   break;
       case EARLY_ECONOMY_WASH:
	   System.out.println("EARLYECONOMY wash");
	   returnDisplay = 3;
	   break;
       case ABORT:
	   System.out.println("ABORT CALLED");
	   returnDisplay = 0;
	   break;
       case RECHARGE:
	   System.out.println("RECHARGE");
	   returnDisplay = 0;
	   break;
       case INSERT_CARD:
	   System.out.println("INSERT CARD");
	   returnDisplay = 1;
	   break;
       case START_WASH:
	   System.out.println("START WASH");
	   returnDisplay = 6;
	   break;
       case RECEIPT:
	   System.out.println("RECEIPT");
	   returnDisplay = 5;
	   break;
       case NO_RECEIPT:
	   System.out.println("NO RECEIPT");
	   returnDisplay = 5;
	   break;
       }
      return(returnDisplay);
   }
    
    // public void buyWash(WashType wash) {
	
    // }
   
   public Account getAccount()
   {
      System.out.println("returning account");   
      return(new Account());
   }
   
   public String getWashDoneTime()
   {
      return("15:30");
   }
}
