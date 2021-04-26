package com.cop4655.z23464822;

public class CryptoModal {

    // variables
    private String symbol;
    private String displaySymbol;
    private String symbolDesc;
    private int favStatus;

    // creating constructor for our variables.
    public CryptoModal(String symbolName, String displaySymbol, String symbolDesc, int favStatus) {
        this.symbol = symbolName;
        this.displaySymbol = displaySymbol;
        this.symbolDesc = symbolDesc;
        this.favStatus = favStatus;
    }

    // creating getter and setter methods.
    public String getStockName() {
        return symbol;
    }

    public void setStockName(String symbol) {
        this.symbol = symbol;
    }

    public String getStockDescription() {
        return symbolDesc;
    }

    public void setStockDescription(String symbolDesc) {
        this.symbolDesc = symbolDesc;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getDisplaySymbol(){
        return displaySymbol;
    }

    public int getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(int favStatus) {
        this.favStatus = favStatus;
    }
}