package com.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    private String id;
    private String type; // FLAT or PERCENT
    private Double value;
    private String appliesTo; // PRODUCT, CATEGORY, GLOBAL
    private String targetId; // productId or categoryId
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
}
