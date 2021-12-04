package com.stockbroker.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Order {
    private String id;
    private OrderType type;
    private DematAccount dematAccount;
    private StockLot stockLot;
}
