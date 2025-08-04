package com.example.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductResponseDTO {
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
    private Integer stock;
    private String sku;
    private List<String> images;
    private Map<String, String> attributes;
    private List<String> tags;
    private Boolean isActive;
    private String createdAt;  // formatted as IST string
    private String updatedAt;
}
