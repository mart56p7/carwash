public class WashCardReader
{

   /**
      Returns the card ID from the card.
   */
   public int getCardId()
   {
      System.out.println("WashCardReader > getCardId()");
      return(100);
   }
   
   
   /**
      Returns if it was possible to eject the card or not.
   */
   public boolean ejectCard()
   {
      System.out.println("WashCardReader > ejectCard()");   
      return(true);
   }
}