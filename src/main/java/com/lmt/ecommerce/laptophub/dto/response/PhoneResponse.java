package com.lmt.ecommerce.laptophub.dto.response;

import com.lmt.ecommerce.laptophub.attribute.phone.PhoneCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.phone.PhoneVariantAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneResponse extends ProductResponse {

    private PhoneCommonAttribute commonAttributes;
    private List<ProductVariantResponse<PhoneVariantAttribute>> variants;

    @Override
    public void setCommonAttributes(Object attributes) {
        this.commonAttributes = (PhoneCommonAttribute) attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setVariants(List variants) {
        this.variants = variants;
    }
}