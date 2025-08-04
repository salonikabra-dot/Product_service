package com.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;
    private String tenantId;
    private String name;
    private String slug;
    private String description;
    private String brand;
    private String categoryId;
    private String subCategoryId;
    private BigDecimal price;
    private BigDecimal finalPrice;
    private DiscountInfo discount;
    private Integer stock;
    private String sku;
    private List<String> images;
    private Map<String, String> attributes;
    private List<String> tags;
    private Boolean isActive;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
private Instant createdAt;
    private Instant updatedAt;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DiscountInfo {
        private String type;
        private Double value;
    }
}
