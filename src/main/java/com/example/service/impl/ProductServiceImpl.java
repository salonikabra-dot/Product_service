//package com.example.service.impl;
//
//import com.example.DTO.ProductResponseDTO;
//import com.example.model.Product;
//import com.example.repository.ProductRepository;
//import com.example.service.ProductService;
//import com.example.util.DateTimeUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    private static final ZoneId IST = ZoneId.of("Asia/Kolkata");
//
//
//
//public Product create(Product product, MultipartFile imageFile) {
//    if (imageFile != null && !imageFile.isEmpty()) {
//        try {
//            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
//            Path filePath = Paths.get("uploads/products/", fileName);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, imageFile.getBytes());
//            product.setImageUrl("/uploads/products/" + fileName);
//        } catch (IOException e) {
//            throw new RuntimeException("Image upload failed");
//        }
//    }
//
//    product.setCreatedAt(ZonedDateTime.now(IST).toInstant());
//    product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());
//
//    return productRepository.save(product);
//}
//
//
//
//
//    @Override
//    public List<Product> getAllByTenant(String tenantId) {
//        return productRepository.findByTenantIdAndIsDeletedFalse(tenantId);
//    }
//
//
//
//
//
//    @Override
//    public Product getById(String id, String tenantId) {
//        return productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//
//    @Override
//    public List<Product> getAllPublic() {
//        return productRepository.findByIsDeletedFalse();
//    }
//
//    @Override
//    public Product getByIdPublic(String id) {
//        return productRepository.findByIdAndIsDeletedFalse(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//
//    @Override
//    public List<Product> getByCategoryPublic(String categoryId) {
//        return productRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
//    }
//
//
//
//@Override
//public Product update(String id, Product updatedProduct, String tenantId, MultipartFile imageFile) {
//    Product existing = productRepository.findByIdAndTenantId(id, tenantId)
//            .orElseThrow(() -> new RuntimeException("Product not found"));
//
//    existing.setName(updatedProduct.getName());
//    existing.setSlug(updatedProduct.getSlug());
//    existing.setDescription(updatedProduct.getDescription());
//    existing.setBrand(updatedProduct.getBrand());
//    existing.setCategoryId(updatedProduct.getCategoryId());
//    existing.setSubCategoryId(updatedProduct.getSubCategoryId());
//    existing.setPrice(updatedProduct.getPrice());
//    //existing.setFinalPrice(updatedProduct.getFinalPrice());
//    existing.setDiscount(updatedProduct.getDiscount());
//    existing.setStock(updatedProduct.getStock());
//    existing.setSku(updatedProduct.getSku());
//    existing.setAttributes(updatedProduct.getAttributes());
//    existing.setTags(updatedProduct.getTags());
//    existing.setIsActive(updatedProduct.getIsActive());
//    existing.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
//
//    if (imageFile != null && !imageFile.isEmpty()) {
//        try {
//            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
//            Path filePath = Paths.get("uploads/products/", fileName);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, imageFile.getBytes());
//            existing.setImageUrl("/uploads/products/" + fileName);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload image", e);
//        }
//    }
//
//    return productRepository.save(existing);
//}
//
//
//
//    @Override
//public void delete(String id, String tenantId) {
//    Product product = productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
//            .orElseThrow(() -> new RuntimeException("Product not found"));
//
//    product.setIsDeleted(true);
//    product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());
//    productRepository.save(product);
//}
//
//
//
//public ProductResponseDTO convertToDto(Product product) {
//    Double discount = (product.getDiscount() != null) ? product.getDiscount().getValue() : null;
//
//    BigDecimal discountPrice = (discount != null && product.getPrice() != null)
//            ? product.getPrice().subtract(
//            product.getPrice().multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100))
//    )
//            : product.getPrice();
//
//    return ProductResponseDTO.builder()
//            .id(product.getId())
//            .tenantId(product.getTenantId())
//            .name(product.getName())
//            .slug(product.getSlug())
//            .description(product.getDescription())
//            .brand(product.getBrand())
//            .categoryId(product.getCategoryId())
//            .subCategoryId(product.getSubCategoryId())
//            .price(product.getPrice())
//            .stock(product.getStock())
//            .sku(product.getSku())
//            .images(product.getImages())
//            .attributes(product.getAttributes())
//            .tags(product.getTags())
//            .isActive(product.getIsActive())
//            .createdAt(DateTimeUtils.toISTString(product.getCreatedAt()))
//            .updatedAt(DateTimeUtils.toISTString(product.getUpdatedAt()))
//            .imageUrl(product.getImageUrl())
//            .discount(discount)
//            .discountPrice(discountPrice)
//            .build();
//}
//
//    public List<Product> getByCategory(String categoryId, String tenantId) {
//        return productRepository.findByCategoryIdAndTenantId(categoryId, tenantId);
//    }
//
//
//
//
//
//
//}
package com.example.service.impl;

import com.example.DTO.ProductResponseDTO;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final ZoneId IST = ZoneId.of("Asia/Kolkata");

    @Override
    public Product create(Product product, MultipartFile imageFile) {
        System.out.println("[ProductService] Creating product: " + product.getName());
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("uploads/products/", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                product.setImageUrl("/uploads/products/" + fileName);
                System.out.println("[ProductService] Image uploaded: " + product.getImageUrl());
            } catch (IOException e) {
                System.out.println("[ProductService] Failed to upload image: " + e.getMessage());
                throw new RuntimeException("Image upload failed");
            }
        }

        product.setCreatedAt(ZonedDateTime.now(IST).toInstant());
        product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());

        Product saved = productRepository.save(product);
        System.out.println("[ProductService] Product saved: " + saved.getId());
        return saved;
    }

    @Override
    public Product update(String id, Product updatedProduct, String tenantId, MultipartFile imageFile) {
        System.out.println("[ProductService] Updating product: " + id + " for tenant: " + tenantId);
        Product existing = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setSlug(updatedProduct.getSlug());
        existing.setDescription(updatedProduct.getDescription());
        existing.setBrand(updatedProduct.getBrand());
        existing.setCategoryId(updatedProduct.getCategoryId());
        existing.setSubCategoryId(updatedProduct.getSubCategoryId());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDiscount(updatedProduct.getDiscount());
        existing.setStock(updatedProduct.getStock());
        existing.setSku(updatedProduct.getSku());
        existing.setAttributes(updatedProduct.getAttributes());
        existing.setTags(updatedProduct.getTags());
        existing.setIsActive(updatedProduct.getIsActive());
        existing.setUpdatedAt(ZonedDateTime.now(IST).toInstant());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("uploads/products/", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                existing.setImageUrl("/uploads/products/" + fileName);
                System.out.println("[ProductService] Image updated: " + existing.getImageUrl());
            } catch (IOException e) {
                System.out.println("[ProductService] Failed to upload image: " + e.getMessage());
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        Product saved = productRepository.save(existing);
        System.out.println("[ProductService] Product updated: " + saved.getId());
        return saved;
    }

    @Override
    public void delete(String id, String tenantId) {
        System.out.println("[ProductService] Deleting product: " + id + " for tenant: " + tenantId);
        Product product = productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setIsDeleted(true);
        product.setUpdatedAt(ZonedDateTime.now(IST).toInstant());
        productRepository.save(product);
        System.out.println("[ProductService] Product marked as deleted: " + id);
    }

    @Override
    public Product getById(String id, String tenantId) {
        System.out.println("[ProductService] Fetching product by ID: " + id + " for tenant: " + tenantId);
        return productRepository.findByIdAndTenantIdAndIsDeletedFalse(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllByTenant(String tenantId) {
        System.out.println("[ProductService] Fetching all products for tenant: " + tenantId);
        return productRepository.findByTenantIdAndIsDeletedFalse(tenantId);
    }

    @Override
    public List<Product> getAllPublic() {
        System.out.println("[ProductService] Fetching all public products");
        return productRepository.findByIsDeletedFalse();
    }

    @Override
    public Product getByIdPublic(String id) {
        System.out.println("[ProductService] Fetching public product by ID: " + id);
        return productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getByCategoryPublic(String categoryId) {
        System.out.println("[ProductService] Fetching public products by category: " + categoryId);
        return productRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
    }

    @Override
    public ProductResponseDTO convertToDto(Product product) {
        System.out.println("[ProductService] Converting product to DTO: " + product.getId());
        Double discount = (product.getDiscount() != null) ? product.getDiscount().getValue() : null;

        BigDecimal discountPrice = (discount != null && product.getPrice() != null)
                ? product.getPrice().subtract(
                product.getPrice().multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100))
        )
                : product.getPrice();

        return ProductResponseDTO.builder()
                .id(product.getId())
                .tenantId(product.getTenantId())
                .name(product.getName())
                .slug(product.getSlug())
                .description(product.getDescription())
                .brand(product.getBrand())
                .categoryId(product.getCategoryId())
                .subCategoryId(product.getSubCategoryId())
                .price(product.getPrice())
                .stock(product.getStock())
                .sku(product.getSku())
                .images(product.getImages())
                .attributes(product.getAttributes())
                .tags(product.getTags())
                .isActive(product.getIsActive())
                .createdAt(DateTimeUtils.toISTString(product.getCreatedAt()))
                .updatedAt(DateTimeUtils.toISTString(product.getUpdatedAt()))
                .imageUrl(product.getImageUrl())
                .discount(discount)
                .discountPrice(discountPrice)
                .build();
    }

    @Override
    public List<Product> getByCategory(String categoryId, String tenantId) {
        System.out.println("[ProductService] Fetching products by category: " + categoryId + " for tenant: " + tenantId);
        return productRepository.findByCategoryIdAndTenantId(categoryId, tenantId);
    }
}

