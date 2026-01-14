package com.lmt.ecommerce.laptophub.entity;

import com.lmt.ecommerce.laptophub.common.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Column(name = "brand", nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "common_attributes", columnDefinition = "json") // Thêm tên cột cho rõ ràng
    private Map<String, Object> commonAttributes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    // Helper method: Giúp code trong Service gọn hơn khi add variant
    public void addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProduct(this); // Set luôn cha cho nó
    }
}