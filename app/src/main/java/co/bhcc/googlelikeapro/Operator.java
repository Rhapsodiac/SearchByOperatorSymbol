package co.bhcc.googlelikeapro;

/**
 * Created by Taylor on 12/21/2015.
 */
public class Operator {
    int numID;
    String symbol;
    String note;

    public Operator(int numID, String symbol, String note){
        this.numID = numID;
        this.symbol = symbol;
        this.note = note;
    }

    public int getNumID() {
        return numID;
    }

    public String getSymbol(){
        return symbol;
    }

    public String getNote(){
        return note;
    }



}
