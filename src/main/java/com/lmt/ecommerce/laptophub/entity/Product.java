package com.lmt.ecommerce.laptophub.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE product_id = ?")
@Where(clause = "is_deleted = false")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "sku_code", nullable = false, unique = true)
    private String skuCode;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "is_flash_sale")
    private Boolean isFlashSale;
}
