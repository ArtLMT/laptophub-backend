package com.lmt.ecommerce.laptophub.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lmt.ecommerce.laptophub.common.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LaptopRequest.class, name = "LAPTOP"),
        @JsonSubTypes.Type(value = PhoneRequest.class, name = "PHONE")
})
public abstract class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Type is required")
    private ProductType type;

    // Bắt class con phải trả về Attribute Chung
    public abstract Object getCommonAttributes();

    // Bắt class con phải trả về List Variant (đã bọc trong Wrapper)
    public abstract List<? extends ProductVariantRequest<?>> getVariants();
}