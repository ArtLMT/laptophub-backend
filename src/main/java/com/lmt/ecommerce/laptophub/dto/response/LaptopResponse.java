package com.lmt.ecommerce.laptophub.dto.response;

import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopVariantAttribute;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LaptopResponse extends ProductResponse {
    private LaptopCommonAttribute commonAttributes;
    private List<ProductVariantResponse<LaptopVariantAttribute>> variants;

    // Vì Lombok @Setter không override method abstract của cha nếu tham số khác kiểu (Object vs PhoneCommonAttribute)
    // Nên ta cần viết thủ công hoặc dùng Generics ở cha.
    // Để đơn giản và tương thích Mapper, ta viết tường minh:

    @Override
    public void setCommonAttributes(Object attributes) {
        this.commonAttributes = (LaptopCommonAttribute) attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setVariants(List variants) {
        this.variants = variants;
    }
}
