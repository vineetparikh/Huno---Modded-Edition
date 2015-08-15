import java.util.*;
public class Player {
    private String playerName;
    private Card[] hand = new Card[Huno.MAX_CARDS];

    
    /*
     * This method returns the name of the player
     */
    public String getName(){
        return this.playerName;
    }

    /*
     * This method returns the player hand (only for use in ComputerPlayer)
     */
    public Card[] getHand(){
        return hand;
    }
    
    /*
     * Allows player to select new color for wildcard
     */
    public String selectColor(Scanner console){
        String newColor;
        System.out.println("Wildcard! Set a new color for it!");
        newColor = console.next();
        while (!selectionIsValid(newColor)){
            System.out.println("Not a valid color. Plz pick a valid color (blue, green, red, or yellow)");
            newColor = console.next();
        }
        
        return newColor;
    }
    
    /*
     * selectionIsValid - error checking for selecting a proper color (for a wildcard)
     */
    public boolean selectionIsValid(String selection){
        String[] validColors = Card.COLORS;
        for (int i = 0;i<validColors.length;i++){
            if (selection.equals(validColors[i]))
                return true;
        }
        return false;
    }
    
    
    /*
     * This method returns the number of cards in the hand
     * after looping through the hand and using a "count"
     * variable to keep track of the number of cards.
     */
    public int getNumCards(){
        int numCards = 0;
        for (int i=0;i<this.hand.length;i++){
            if (this.hand[i]!=null&&!(this.hand[i].equals(""))){
                numCards++;
            }
        }
        return numCards;
    }
    
    /*
     * This toString() method returns the player's name
     */
    public String toString(){
        return this.playerName;
    }
    
    /*
     * This addCardToHand() method takes in a card, then looks for the
     * lowest array value available, sets the card to that value, and
     * breaks out of the loop
     */
    public void addCardToHand(Card card){
        if (card==null|handIsNotFull()==false){
            throw new IllegalArgumentException();
        }
        
        hand[getNumCards()]=card;
    }

    /*
     * This handIsNotFull() method tests to see if the hand is full
     * It returns true if an element doesn't have a card and
     * false otherwise
     */
    private boolean handIsNotFull(){
        for (int i = 0; i<this.hand.length;i++){
            if (this.hand[i]==null||this.hand[i].equals("")){
                return true;
            }
        }
        return false;
    }
    
    /*
     * getCardFromHand - takes an integer index as a parameter and returns
     * the card at the specified position in the player's hand, without actually
     * removing the card from the hand.
     */
    public Card getCardFromHand(int index){
        if (hand[index].equals("")||hand[index]==null){
            throw new IllegalArgumentException(); 
        // checks if the index has a value
        }
        
        return hand[index];
    }
    
    /*
     * getHandValue - loops through hand, gets card value from each index,
     * and uses class constants to determine if there are the max number
     * of cards 
     */
    public int getHandValue(){
        int score = 0;
        
        for (int i=0;i<getNumCards();i++){
            if (getCardFromHand(i)==null){
                break;
            } else if (getNumCards()==0){
                return 0; // there are no cards, so there are no values
            }
            score += hand[i].getValue();
        }
        if (getNumCards()==Huno.MAX_CARDS){
            score+=Huno.MAX_CARDS_PENALTY; //adds 25 if there are 10 cards
        } 
        return score;
    }
    
    /*
     * printHand - prints current contents of the player's hand, preceded
     * by a heading that includes player name: each card is printed on a 
     * separate line, preceded by index of its position in the hand.
     */
    public void printHand(){
        System.out.println(playerName+"'s hand:");
        for (int i =0; i<getNumCards();i++){
            System.out.println("  "+i+": "+hand[i].toString());
        }
    }
    
    /*
     * removeCardFromHand - receives an index to remove, calls the
     * "getCardFromHand" method to find the removed card, and getNumCards-1
     * to find the last card. Then, this method removes the card (and stores
     * it in a variable removedCard), replaces it with lastCard, sets lastCard's
     * original slot to null, and returns the removedCard
     */
    public Card removeCardFromHand(int index){
        Card lastCard = hand[getNumCards()-1];
        Card removedCard = getCardFromHand(index);
        
        hand[index]=lastCard;
        hand[getNumCards()-1]=null;
        
        return removedCard;
    }
    
    /*
     * getPlay - uses user input to figure out the card to play (or -1 if we want to draw), and
     * uses error checking to make sure the user enters a valid input (i.e. there's a card in the
     * desired slot).
    */
    public int getPlay(Scanner console, Card topCard){
        int desiredIndex;
        while (true){
            System.out.print(getName()
                                   +": number of card to play (-1 to draw)?");
            desiredIndex = console.nextInt();
            if((desiredIndex>=0&&desiredIndex<=getNumCards()-1)||desiredIndex==-1){
                break;
            }
        }
        return desiredIndex;
    }
    
    
    /*
     * Simple constructor - only initializes the "name" field since all other fields don't need default values 
    */
    public Player(String playerName){
        this.playerName = playerName;
    }
}