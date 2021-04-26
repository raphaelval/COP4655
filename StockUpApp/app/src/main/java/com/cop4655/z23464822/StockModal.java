package com.cop4655.z23464822;

public class StockModal {

    // variables
    private String symbol;
    private String symbolDesc;
    private int favStatus;

    // creating constructor for our variables.
    public StockModal(String symbolName, String symbolDesc, int favStatus) {
        this.symbol = symbolName;
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

    public int getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(int favStatus) {
        this.favStatus = favStatus;
    }
}
