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
    private String type;
    private Double value;
    private String appliesTo;
    private String targetId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
}
