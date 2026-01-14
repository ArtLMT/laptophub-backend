package com.lmt.ecommerce.laptophub.dto.request;

import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopVariantAttribute;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LaptopRequest extends ProductRequest {

    @Valid
    @NotNull
    private LaptopCommonAttribute commonAttributes;

    @Valid
    @NotEmpty(message = "Must have at least one variant")
    private List<ProductVariantRequest<LaptopVariantAttribute>> variants;
}