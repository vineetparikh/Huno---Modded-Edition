/*
 * Huno.java
 * 
 * Computer Science S-111, Harvard University
 * 
 * The main class for a program that plays a card game called Huno.
 * It also serves as a blueprint class for a Huno object, which maintains
 * the state of the game.
 * 
 * Constituent files:
 * Huno.java - maintains entire game and game flow
 * Deck.java - stores random selection of cards
 * Card.java - represents a single card with color and value
 * Player.java - represents a human player
 * ComputerPlayer.java - represents a cpu (extended from Player.java)
 *
 * modified by: Vineet Parikh (vince.parikh@gmail.com)
 * 
 * Mod Docs:
 * - Added special cards: draw 2, skip, and wild card
 * - Modified game flow and computer AI to account for new cards: computer now prioritizes
 * other cards over wild cards, and uses wild cards as a last resort.
 * 
 * Ways to extend this:
 * - Create tutorial section
 * - Improve AI to read player strategy and adapt by abruptly changing colors or using wilds.
 * (this can probably be done with color "priorities" which are constantly updated)
 * - Create a setup to choose btw. player vs. player, or player v.s cpu
 * - Update this to work with more than 2 players (cpu or human)
 * - Add more special cards ("Reverse","Draw 4")
 * - Add taunts by computer (i.e. "Is that your BEST?")
 * - Possibly use a linked list over an array to make # of cards limitless (however, this means that there's now
 *   a slight efficiency problem involved: do this at your own risk).
 * etc. etc. etc.
 */

import java.util.*;

public class Huno {
    /* the number of players in the game */
    public static final int NUM_PLAYERS = 2;
    
    /* cards per player at start of game */
    public static final int NUM_INIT_CARDS = 5;
    
    /* the game ends if a player ends up with this many cards */
    public static final int MAX_CARDS = 20; 
    
    /* the penalty for ending up with MAX_CARDS cards */
    public static final int MAX_CARDS_PENALTY = 25;
    
    /* fields of a Huno object */
    private Scanner console;    // used to read from the console
    private Deck deck;          // the deck of cards used for the game
    private Player[] players;   // the players of the game
    private Card topDiscard;    // card at the top of the discard pile
    
    /*
     * constructor - takes the Scanner to be used to read from the
     * console, a randomSeed for the Deck's random-number generator,
     * and the name of the player.
     */
    public Huno(Scanner console, int randomSeed, String playerName) {
        this.console = console;

        // Create and shuffle the deck.
        deck = new Deck(randomSeed);
        deck.shuffle();
        
        // Create the players.
        players = new Player[NUM_PLAYERS];
        players[0] = new Player(playerName);
        players[1] = new ComputerPlayer("the computer");
        
        // Deal the cards.
        dealHands();
        topDiscard = deck.drawCard();   
    }
    
    /*
     * dealHands - deals the initial hands of the players.
     * Each player gets NUM_INIT_CARDS cards.
     */
    public void dealHands() {
        for (int i = 0; i < NUM_INIT_CARDS; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                players[j].addCardToHand(deck.drawCard());
            }
        }
    }
    
    /*
     * play - plays an entire game of Huno
     */
    public void play() {
        printGameState();
        
        while (true) {
            // Each player makes one play.
            for (int i = 0; i < players.length; i++) {
                executeOnePlay(players[i],players[players.length-i-1]); // there's only 2 players here, so this
                // is pretty solid.
            }
            
            printGameState();
            if (gameOver()) {
                return;
            }
        }
    }
    
    /*
     * printGameState - prints the players' hands and the card at the
     * top of the discard pile
     */
    public void printGameState() {
        System.out.println();
        for (int i = 0; i < players.length; i++) {
            players[i].printHand();
        }
        System.out.println("discard pile: ");
        System.out.println("  " + topDiscard);
        //System.out.println();
    }
    
    /*
     * executeOnePlay - carries out a single play by the specified player
     */
    public void executeOnePlay(Player player, Player other) {
        // Keep looping until a valid play is obtained.
        // We break out of the loop using a return statement.
        while (true) {
            int cardNum = player.getPlay(console, topDiscard);
            
            if (cardNum == -1) {
                System.out.println(player + " draws a card.");
                player.addCardToHand(deck.drawCard());
                return;
            } else {
                Card cardToPlay = player.getCardFromHand(cardNum);
                
                if (cardToPlay.getValue()==12){ // this is a wildcard
                    player.removeCardFromHand(cardNum);
                    String newColor = player.selectColor(console);
                    
                    cardToPlay.setColor(newColor);
                    System.out.println(player + " discards " + cardToPlay + "."); 
                    topDiscard = cardToPlay;
                    return;
                }else if (cardToPlay.matches(topDiscard)) { 
                    System.out.println(player + " discards " + cardToPlay + "."); 
                    player.removeCardFromHand(cardNum);
                    if (cardToPlay.getValue()==10){ // this is a draw 2
                        other.addCardToHand(deck.drawCard());
                        other.addCardToHand(deck.drawCard());
                    }
    
                    // The card played is now at the top of the discard pile.
                    topDiscard = cardToPlay;
                    
                    if (cardToPlay.getValue()!=11)
                        return; // a skip wasn't used, so stop the turn
                    else
                        printGameState(); // a skip was used, so show the new hand here
                } else {
                    System.out.println("invalid play -- " + cardToPlay + 
                                       " doesn't match top of discard pile");
                }
            }
        }
    }
    
    /*
     * gameOver - returns true if the game is over -- either because
     * a player has no cards or because a player has the maximum
     * number of cards -- and false otherwise.
     */
    public boolean gameOver() {  
        for (int i = 0; i < players.length; i++) {
            if (players[i].getNumCards() == 0
              || players[i].getNumCards() == MAX_CARDS) {
                    return true;
            }
        }
        
        return false;
    }
    
    /*
     * reportResults - determines and prints the results of the game.
     */
    public void reportResults() {
        int totalValue = 0;
     
        int winnerIndex = 0;
        int winnerValue = players[0].getHandValue();
        totalValue += winnerValue;
        boolean isTie = false;
        
        for (int i = 1; i < players.length; i++) {
            int playerValue = players[i].getHandValue();
            totalValue += playerValue;
            
            if (playerValue < winnerValue) {
                winnerValue = playerValue;
                winnerIndex = i;
                isTie = false;
            } else if (playerValue == winnerValue) {
                isTie = true;
            }
        }
        
        if (isTie) {
            System.out.println("The game is a tie; no one earns any points.");
        } else {
            System.out.print("The winner is " + players[winnerIndex]);
            System.out.print(", who earns " + (totalValue - winnerValue));
            System.out.println(" points.");
        }
    }
    

    
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        int seed = -1;
        if (args.length > 0) {
            seed = Integer.parseInt(args[0]);
        }
        
        System.out.println("Welcome to the game of Huno!");
        //System.out.println();
        System.out.print("What's your first name? ");
        String name = console.next();
               
        Huno game = new Huno(console, seed, name);

        game.play();
        game.reportResults();
    }
}