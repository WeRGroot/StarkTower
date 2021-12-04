package com.stockbroker.model;

import lombok.Getter;

@Getter
public class StockLot {
    private final Stock stock;
    private int quantity;
    private double avgBuyPrice;

    public StockLot(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
        this.avgBuyPrice = quantity > 0 ? stock.getCurrPrice() : 0;
    }

    public void addQuantity(int quantity) {
        double oldPrice = this.avgBuyPrice;
        int oldQuantity = this.quantity;
        double newPrice = stock.getCurrPrice();
        int newQuantity = this.quantity + quantity;
        this.avgBuyPrice = getAveragePrice(oldPrice, oldQuantity, newPrice, newQuantity);
        this.quantity = newQuantity;
    }

    public void removeQuantity(int quantity) {
        if (this.quantity < quantity)
            throw new RuntimeException("invalid operation due to insufficient quantity");
        this.quantity -= quantity;
    }

    private double getAveragePrice(double oldPrice, int oldQuantity, double newPrice, int newQuantity) {
        if (newQuantity == 0)
            return 0;
        double oldAmount = oldPrice * oldQuantity;
        double newAmount = newPrice * newQuantity;
        int totalQuantity = newQuantity + oldQuantity;
        double totalAmount = newAmount + oldAmount;
        return (totalAmount / totalQuantity);

    }
}
