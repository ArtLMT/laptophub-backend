package com.lmt.ecommerce.laptophub.dto.request;

import com.lmt.ecommerce.laptophub.attribute.phone.PhoneCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.phone.PhoneVariantAttribute;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneRequest extends ProductRequest {

    @Valid
    @NotNull
    private PhoneCommonAttribute commonAttributes;

    @Valid
    @NotEmpty(message = "Must have at least one variant")
    private List<ProductVariantRequest<PhoneVariantAttribute>> variants;

}