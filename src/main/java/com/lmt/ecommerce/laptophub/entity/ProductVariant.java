package com.lmt.ecommerce.laptophub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variants")
@AttributeOverride(name = "id", column = @Column(name = "product_variant_id"))
@SQLDelete(sql = "UPDATE product_variants SET is_deleted = true WHERE product_variant_id = ?")
@Where(clause = "is_deleted = false")
public class ProductVariant extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "sku_code", nullable = false, unique = true)
    private String skuCode;

    @Column(name = "is_flash_sale")
    private Boolean isFlashSale;

    @Column(nullable = false)
    private BigDecimal price;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "json")
    private Map<String, Object> variantAttributes;
}