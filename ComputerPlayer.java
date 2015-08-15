import java.util.*;
public class ComputerPlayer extends Player{

    /*
     * Simpler constructor - invokes superclass constructor to add the proper name 
    */
    public ComputerPlayer(String name){
        super(name);
    }

    /*
     * This method allows the computer player to select the color for a wildcard. It loops through the hand to
     * check and see the # of cards for each color, then picks the color with the most cards & returns that color.
    */
    public String selectColor(Scanner console){
        Card[] hand = this.getHand();
        int[] numOfEach  = {0,0,0,0};
        for (int i = 0;i<this.getNumCards();i++){
            
            Card checkedCard = getCardFromHand(i);
            if (checkedCard!=null){
            if (checkedCard.getColor().equals("blue"))
                numOfEach[0]++; // increment for blue
            else if (checkedCard.getColor().equals("green"))
                numOfEach[1]++; // increment for green
            else if (checkedCard.getColor().equals("red"))
                numOfEach[2]++; // increment for red
            else
                numOfEach[3]++; // increment for yellow
            }
        }
        int greatestNum = -1;
        int bestIndex = 0;
        for (int i =0;i<numOfEach.length;i++){
            if (numOfEach[i]>greatestNum){
                greatestNum = numOfEach[i];
                bestIndex = i;
            }
        }
        Card checkedCard = getCardFromHand(0); // all it needs is the COLORS constant, so it will get a default card
        // and go from there. 
        return checkedCard.COLORS[bestIndex]; // so it returns the most frequent color
    }
    
    /*
     * Overrides "Player"'s printHand() method: this makes it so the computer's hand isn't immediately revealed,
     * and only the # of cards the computer has is specified.
    */
    public void printHand(){
        String endWord;
        if (this.getNumCards()==1){
            endWord = "card";
        }else{
              endWord = "cards";
        }
        System.out.println(this.getName()+"'s hand: "+this.getNumCards()
                               +" "+endWord);
    }
    
    
    /*
     * getPlay - modified somewhat from original version of getPlay to account for wild card (other card values,
     * such as the skip and draw two, can be handled much like other cards)
     *
     * Computer will use array to keep track of usable cards (adding any cards which match the top card in color/value
     * or wild cards). Then this array will be looped through again to check for the best possible card (with the
     * greatest possible value). If the computer encounters a wild card, it will store the reference for future use
     * (this AI will conserve wilds as much as possible), and not consider it for the best index. If the best index
     * is valid, it will return this. If it isn't, it will return the index of the first wild card (in the case with
     * no wild cards, the return value is still -1).
    */
    public int getPlay(Scanner console, Card topCard){
        Card[] usableCards = new Card[getNumCards()]; 
        for (int i = 0; i<getNumCards();i++){ 
            Card examiningCard = getCardFromHand(i);
            if (examiningCard.getValue()==12||examiningCard.getValue()==topCard.getValue()
                    ||examiningCard.getColor().equals(topCard.getColor())){ 
                //see if the card is valid
                usableCards[i] = getCardFromHand(i); 
            }
        }
        int greatestValue = -1; 
        int bestIndex = -1; // index of best card to fit (skip prioritized over draw 2).
        int wildIndex = -1; // index of 1st wild card.
        
        for (int i =0; i<usableCards.length;i++){
            if (usableCards[i]!=null&&usableCards[i].getValue()>greatestValue&&usableCards[i].getValue()!=12){
                bestIndex = i;
                greatestValue = usableCards[i].getValue();
            } else if (usableCards[i]!=null&&usableCards[i].getValue()==12){
                wildIndex = i; // this prioritizes non-wild indices over wild indices. So only if no other usable card
                // can be found will the computer decide on the wild card
            }
        }
        
        if (bestIndex==-1){ // no non-wild cards can be found, but there is a wild card OR NOT (this means that it'll
            // still stay at -1
            bestIndex=wildIndex;
        }
        
        return bestIndex;
        
    }
    
    
}