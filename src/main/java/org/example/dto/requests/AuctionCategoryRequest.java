package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.data.models.ItemCategory;

@Data @AllArgsConstructor
public class AuctionCategoryRequest {
    private ItemCategory category;
}
