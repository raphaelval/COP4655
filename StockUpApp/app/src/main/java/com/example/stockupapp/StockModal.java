package com.example.stockupapp;

public class StockModal {

    // variables
    private String symbol;
    private String symbolDesc;

    // creating constructor for our variables.
    public StockModal(String symbolName, String symbolDesc) {
        this.symbol = symbolName;
        this.symbolDesc = symbolDesc;
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
}
