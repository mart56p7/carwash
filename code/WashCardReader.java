public class WashCardReader
{
    public static int cardId = 0;

    // Returns the card ID from the card.
    public static int getCardId()
    {
        System.out.println("WashCardReader > getCardId()");
        return(cardId);
    }

    public static boolean setCardId (String tekst) {
        try
        {
            cardId = Integer.parseInt(tekst);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    // Returns if it was possible to eject the card or not.
    public boolean ejectCard()
    {
        System.out.println("WashCardReader > ejectCard()");
        return(true);
    }
}
