package com.stockbrocker;

import com.stockbroker.model.CardPayment;
import com.stockbroker.model.DematAccount;
import com.stockbroker.model.Order;
import com.stockbroker.model.OrderType;
import com.stockbroker.model.Payment;
import com.stockbroker.model.PaymentType;
import com.stockbroker.model.Stock;
import com.stockbroker.model.StockLot;
import com.stockbroker.model.User;
import com.stockbroker.model.stockexchange.ExchangeOrder;
import com.stockbroker.model.stockexchange.IStockExchange;
import com.stockbroker.model.stockexchange.IStockGateway;
import com.stockbroker.model.stockexchange.StockExchange;
import com.stockbroker.repository.DematAccountRepository;
import com.stockbroker.repository.StockInventoryRepository;
import com.stockbroker.repository.UserRepository;
import com.stockbroker.service.StockGateway;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class TestMain {

    private DematAccountRepository dematAccountRepository  =  DematAccountRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();
    private StockInventoryRepository stockInventoryRepository = StockInventoryRepository.getInstance();

    @Test
    void completeTest() {
        IStockExchange stockExchange = new StockExchange();

        StockGateway zerodha = new StockGateway("ZERODHA", stockExchange);

        stockExchange.register(zerodha);

        DematAccount appleDematAccount = new DematAccount(zerodha);
        DematAccount googleDematAccount = new DematAccount(zerodha);
        DematAccount teslaDematAccount = new DematAccount(zerodha);

        dematAccountRepository.addDematAccount(appleDematAccount);
        dematAccountRepository.addDematAccount(googleDematAccount);
        dematAccountRepository.addDematAccount(teslaDematAccount);

        System.out.println("APPLE: "+appleDematAccount.getId());
        System.out.println("GOOGLE: "+googleDematAccount.getId());
        System.out.println("TESLA: "+teslaDematAccount.getId());

        ExchangeOrder appleInitialSellOrder = new ExchangeOrder(100, appleDematAccount.getId(), "APPLE",
            zerodha.getName(), OrderType.SELL);
        appleInitialSellOrder.setInitialOrder(true);

        ExchangeOrder googleInitialSellOrder = new ExchangeOrder(100, googleDematAccount.getId(), "GOOGLE",
            zerodha.getName(), OrderType.SELL);
        googleInitialSellOrder.setInitialOrder(true);

        ExchangeOrder teslaInitialSellOrder = new ExchangeOrder(100, teslaDematAccount.getId(), "TESLA",
            zerodha.getName(), OrderType.SELL);
        teslaInitialSellOrder.setInitialOrder(true);

        Stock appleStock = new Stock("APPLE", 150.00);
        Stock googleStock = new Stock("GOOGLE", 3000.00);
        Stock teslaStock = new Stock("TESLA", 900.00);

        appleDematAccount.addStock(new StockLot(appleStock, 100));
        googleDematAccount.addStock(new StockLot(googleStock, 100));
        teslaDematAccount.addStock(new StockLot(teslaStock, 100));

        stockExchange.addStock(appleStock.getSymbol(), appleStock.getCurrPrice());
        stockExchange.addStock(googleStock.getSymbol(), googleStock.getCurrPrice());
        stockExchange.addStock(teslaStock.getSymbol(), teslaStock.getCurrPrice());

        stockInventoryRepository.addStock(appleStock);
        stockInventoryRepository.addStock(googleStock);
        stockInventoryRepository.addStock(teslaStock);

        stockExchange.sellStock(appleInitialSellOrder);
        stockExchange.sellStock(googleInitialSellOrder);
        stockExchange.sellStock(teslaInitialSellOrder);

        // StockExchange initialization complete

        DematAccount ranjeetDematAccount = new DematAccount(zerodha);
        DematAccount shivamDematAccount = new DematAccount(zerodha);

        dematAccountRepository.addDematAccount(ranjeetDematAccount);
        dematAccountRepository.addDematAccount(shivamDematAccount);

        User ranjeet = new User();
        ranjeet.addDematAccount(ranjeetDematAccount);

        User shivam = new User();
        shivam.addDematAccount(shivamDematAccount);

        userRepository.addUser(ranjeet);
        userRepository.addUser(shivam);

        Payment cardPayment = new CardPayment(PaymentType.DEPOSIT);
        cardPayment.depositMoney(100000, ranjeetDematAccount.getWallet());
        cardPayment.depositMoney(100000, shivamDematAccount.getWallet());

        StockLot stockLot1 = new StockLot(appleStock, 50);

        Order order1 = Order.builder()
            .id(UUID.randomUUID().toString())
            .dematAccount(ranjeetDematAccount)
            .stockLot(stockLot1)
            .type(OrderType.BUY)
            .build();
        zerodha.buyStock(order1);

        System.out.println("Ranjeet Wallet: "+ranjeetDematAccount.getWallet().getBalance());
        System.out.println("Apple Wallet: "+appleDematAccount.getWallet().getBalance());

        stockExchange.updateStockPrice("APPLE", 200.00);

        System.out.println("Ranjeet PL: "+ranjeetDematAccount.getCurrentPL());

        order1.setDematAccount(shivamDematAccount);
        zerodha.buyStock(order1);

        System.out.println("Shivam Wallet: "+shivamDematAccount.getWallet().getBalance());
        System.out.println("Apple Wallet: "+appleDematAccount.getWallet().getBalance());

        stockExchange.updateStockPrice("APPLE", 300.00);

        System.out.println("Ranjeet PL: "+ranjeetDematAccount.getCurrentPL());
        System.out.println("Shivam PL: "+shivamDematAccount.getCurrentPL());

        System.out.println("APPLE stock buy count: "+stockExchange.getBuyCount("APPLE"));

        order1.setType(OrderType.SELL);
        order1.setStockLot(new StockLot(appleStock, 10));
        zerodha.sellStock(order1);

        System.out.println("APPLE stock buy count: "+stockExchange.getBuyCount("APPLE"));

        order1.setDematAccount(ranjeetDematAccount);
        order1.setType(OrderType.BUY);
        zerodha.buyStock(order1);

        System.out.println("APPLE stock buy count: "+stockExchange.getBuyCount("APPLE"));

        System.out.println("Shivam Wallet: "+shivamDematAccount.getWallet().getBalance());
        System.out.println("Ranjeet Wallet: "+ranjeetDematAccount.getWallet().getBalance());

    }
}
