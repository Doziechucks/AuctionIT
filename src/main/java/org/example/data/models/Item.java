package org.example.data.models;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "items")
@Getter
public class Item {
    @Id
    private String itemId;
    @Setter
    private String itemName;
    @Setter
    private String itemDescription;
    @Setter
    private BigDecimal itemCurrentPrice;
    @Setter
    private ItemCategory itemCategory;

    public Item(String itemId, String itemName, String itemDescription, BigDecimal itemPrice, ItemCategory itemCategory) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCurrentPrice = itemPrice;
        this.itemCategory = itemCategory;
    }

    public Item(String itemId, String itemName, String itemDescription, BigDecimal itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCurrentPrice = itemPrice;
    }

}

