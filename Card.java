/*
 * Card.java
 *
 * Computer Science S-111, Harvard University
 * 
 * A blueprint class for objects that represent a single playing card for Huno.
 */

public class Card {
    /* The smallest possible value that a Card can have. */
    public static final int MIN_VALUE = 0;
    
    /* The largest possible value that a Card can have. */
    public static final int MAX_VALUE = 12;
    
    /* The possible colors that a Card can have. */
    public static final String[] COLORS = {"blue", "green", "red", "yellow"};
    
    /* The possible effects a Card can have */
    public static final String[] EFFECTS = {"draw 2","skip","wild"};

    /*
     * isValidColor - should return true if the specified color
     * parameter is one of the colors listed in the COLORS array above,
     * and false otherwise.
     */
    private static boolean isValidColor(String color) {        
        for (int i =0; i<COLORS.length;i++){
            if(color.equals(COLORS[i])){
                return true;
            } else if (color==null){
                return false;
            }
        }
        return false;
    }
    
    /* Put the rest of your class definition below. */
    private String color;
    private int value;
    private String effect; // this is if the card is special
    
    /*
     * setColor - takes a string representing a color and sets
     * value of the Card object to that color. To check if the color
     * is valid, the isValidColor method is used as a boolean "Flag" and
     * an exception is thrown if the color isn't valid (e.g. if it is "orange")
     */
    public void setColor(String newColor){
        if (isValidColor(newColor)==false){
            throw new IllegalArgumentException();
        }
        this.color = newColor;
    }
    
    /*
     * setValue - takes an integer and sets the value of the Card's object
     * value field to the specified number. If the value is invalid (i.e. if
     * it is outside the "min/max" range for values) an IllegalArgumentException
     * is thrown
     */
    public void setValue(int newValue){
        if (newValue>12||newValue<0){
            throw new IllegalArgumentException();
        }
        this.value = newValue;
    }
    
    /*
     * setEffect - takes an integer between 0-2 and sets the value of the Card object's
     * effect field to the specified effect. If the value is invalid (i.e. if it is >2 or <0)
     * an IllegalArgException is thrown.
     * Also, the Card's value is set to "10","11", or "12" respectively. You know, since that would make a bit of sense
    */
    public void setEffect(){
        if (getValue()<13&&getValue()>9)
            this.effect = EFFECTS[getValue()-10];
    }
    
    /*
     * This is the constructor for the "Card" class. This specific constructor
     * takes two initial values (a string for the color and an integer for
     * the number value), and sets the new Card object's color and value
     * to the given string and integer. It uses mutator methods with built-in
     * error checking (i.e. if the integer value is greater than 9 or less than
     * 0, or if the color doesn't belong in the COLORS array, an exception is
     * thrown).
     */
    public Card(String initColor, int initValue){
        setColor(initColor);
        setValue(initValue);
    }
    
    /*
     * getColor - returns the String representing the Card object's color
     */
    public String getColor(){
        return this.color;
    }
    
    /*
     * getValue - returns the integer representing the Card object's value
     */
    public int getValue(){
        return this.value;
    }
    
    /*
     * getEffect - return string specifiying the card's effect
    */
    public String getEffect(){
        return this.effect;
    }
    
    /*
     * toString - returns a String representation of the Card object
     * that can be used when printing this or concatenating it
    */
    public String toString(){
        if (getValue()>9)
            return ""+getColor()+" "+getEffect();
        
        
        return ""+getColor()+" "+getValue();
    }
    
    /*
     * matches - takes another Card object as a parameter & returns true if
     * the called Card object matches the color and/or value of the Card object
     * (returning false if there is no match with either color or value).
     */
    public boolean matches(Card other){
        if(other==null){
            return false;
        } else if (this.getValue()==other.getValue()
                     ||this.getColor().equals(other.getColor())){
            return true;
        }
        return false;
    }
    
    /*
     * equals - takes another Card object as paramter and returns true
     * if both this object and the called object have equal color and value
     */
    public boolean equals(Card other){
        if (other==null){
            return false;
        } else if (this.getValue()==other.getValue()
                     &&this.getColor().equals(other.getColor())){
            return true;
        }
        return false;
    }
}    