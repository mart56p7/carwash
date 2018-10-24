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
    WashType earlyStandardWash = new WashType("earlyStandardWash", 64);
    WashType earlyEconomyWash = new WashType("earlyEconomyWash", 40);

    Account currentAccount = null;
    double selectedRechargeAmount = 0;
    Printer printer = new Printer();
    WashHallDatabaseInterface DBInterface = new WashHallDatabaseInterface();
    Purchase newReceipt = null;

    public WashHallManager(WashHallGUI _gui)
    {
        gui = _gui;
    }

    public void loadAccount ()
    {
        Account newAccount = DBInterface.getAccount(WashCardReader.getCardId());
        currentAccount = newAccount;
    }

    public DisplayType reCharge ()
    {
        currentAccount.addBalance(selectedRechargeAmount);
        DBInterface.setBalance(currentAccount.getId(),currentAccount.getBalance());
        return getNormalOrEarlyMenu();
    }

    public DisplayType buyWash(WashType wash)
    {
        if (currentAccount.getBalance() < wash.getPrice())
        {
            return DisplayType.RECHARGE_MENU;
        }
        else
        {
            currentAccount.removeBalance(wash.getPrice());
            newReceipt = new Purchase(currentAccount.getId(),wash.getName(),wash.getPrice());
            DBInterface.addPurchase(newReceipt);
            DBInterface.setBalance(currentAccount.getId(),currentAccount.getBalance());
            return DisplayType.RECEIPT_MENU;
        }

    }
    public boolean isEarlyBirdDay()
    {
        DayOfWeek day = currentAccount.getTimeStamp().getDayOfWeek();

        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isEarlyBirdTime()
    {
        LocalTime now = currentAccount.getTimeStamp().toLocalTime();
        LocalTime startTime = LocalTime.of(0,0);
        LocalTime endTime = LocalTime.of(13,59);

        if (now.isAfter(startTime) && now.isBefore(endTime))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public DisplayType getNormalOrEarlyMenu()
    {
        if (isEarlyBirdDay() && isEarlyBirdTime())
        {
            return DisplayType.EARLYBIRD_MENU;
        }
        else
        {
            return DisplayType.NORMAL_MENU;
        }
    }

    public DisplayType runCommand(Operation o)
    {
        DisplayType returnDisplay = DisplayType.INSERT_CARD_SCREEN;
        switch(o)
        {
            case CARD_INSERTED:
                loadAccount();
                System.out.println(currentAccount.getBalance());
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
                System.out.println("RECHARGE");
                returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
                selectedRechargeAmount = 200;
                break;
            case RECHARGE_500:
                System.out.println("RECHARGE");
                returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
                selectedRechargeAmount = 500;
                break;
            case RECHARGE_1000:
                System.out.println("RECHARGE");
                returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
                selectedRechargeAmount = 1000;
                break;
            case INSERT_CREDITCARD_SCREEN:
                System.out.println("INSERT_CREDITCARD_SCREEN");
                returnDisplay = DisplayType.INSERT_CREDITCARD_SCREEN;
                break;
            case CREDITCARD_INSERTED:
                System.out.println("CREDITCARD_INSERTED");
                returnDisplay = reCharge();
                break;
            case START_WASH:
                System.out.println("START WASH");
                returnDisplay = DisplayType.WAIT_SCREEN;
                break;
            case RECEIPT:
                System.out.println("RECEIPT");
                returnDisplay = DisplayType.INSTRUCTION_SCREEN;
                printer.printReceipt(newReceipt);
                break;
            case NO_RECEIPT:
                System.out.println("NO RECEIPT");
                returnDisplay = DisplayType.INSTRUCTION_SCREEN;
                break;
            case FINISHED:
                // TODO
                // selfdiagnistics
                // reset all data
                returnDisplay = DisplayType.INSERT_CARD_SCREEN;
                break;
        }
        return(returnDisplay);
    }

    public Account getAccount()
    {
        // System.out.println("returning account");
        return currentAccount;
    }

    public String getWashDoneTime()
    {
        // Set real time (time of wash start + 15min)
        return("15:30");
    }
}
