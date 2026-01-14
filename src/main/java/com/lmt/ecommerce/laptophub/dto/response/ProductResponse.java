package com.lmt.ecommerce.laptophub.dto.response;

import com.lmt.ecommerce.laptophub.common.ProductType;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductResponse {

    private Long id;
    private String name;
    private String brand;
    private ProductType type;

    // Abstract methods để ép class con phải khai báo
    public abstract Object getCommonAttributes();
    public abstract List<? extends ProductVariantResponse<?>> getVariants();

    // Setter abstract để Mapper gọi
    public abstract void setCommonAttributes(Object attributes);
    public abstract void setVariants(List variants);
}