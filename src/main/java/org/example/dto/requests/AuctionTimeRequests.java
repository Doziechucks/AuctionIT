package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuctionTimeRequests {
    private LocalDate date;
}
