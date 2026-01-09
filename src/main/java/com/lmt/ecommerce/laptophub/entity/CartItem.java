package com.lmt.ecommerce.laptophub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
@AttributeOverride(name = "id", column = @Column(name = "cart_item_id"))
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE cart_item_id = ?")
@Where(clause = "is_deleted = false")
public class CartItem extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    private Integer quantity;
}
