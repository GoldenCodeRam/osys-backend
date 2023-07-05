package com.osys.demo.service.error;

public class StockNotAvailableException extends Exception{
    public StockNotAvailableException() {
        super("Stock not available for this transaction");
    }
}
