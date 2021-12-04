package com.stockbroker.model;

import lombok.Getter;

@Getter
public class StockLot {
    private final Stock stock;
    private long quantity;
    private double avgBuyPrice;

    public StockLot(Stock stock, long quantity) {
        this.stock = stock;
        this.quantity = quantity;
        this.avgBuyPrice = quantity > 0 ? stock.getCurrPrice() : 0;
    }

    public void addQuantity(long quantity) {
        double oldPrice = this.avgBuyPrice;
        long oldQuantity = this.quantity;
        double newPrice = stock.getCurrPrice();
        long newQuantity = this.quantity + quantity;
        this.avgBuyPrice = getAveragePrice(oldPrice, oldQuantity, newPrice, newQuantity);
        this.quantity = newQuantity;
    }

    public void removeQuantity(long quantity) {
        if (this.quantity < quantity)
            throw new RuntimeException("invalid operation due to insufficient quantity");
        this.quantity -= quantity;
    }

    private double getAveragePrice(double oldPrice, long oldQuantity, double newPrice, long newQuantity) {
        if (newQuantity == 0)
            return 0;
        double oldAmount = oldPrice * oldQuantity;
        double newAmount = newPrice * newQuantity;
        long totalQuantity = newQuantity + oldQuantity;
        double totalAmount = newAmount + oldAmount;
        return (totalAmount / totalQuantity);

    }
}
