package com.lmt.ecommerce.laptophub.attribute.phone;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneCommonAttribute {
    @NotBlank(message = "OS is required")
    private String os; // VD: iOS 17, Android 14

    @Min(value = 3, message = "Screen size too small")
    private Double screenSize;

    private String panelType; // VD: AMOLED, Super Retina XDR
    private Integer battery; // mAh

    private String mainCamera; // VD: "48MP + 12MP + 12MP"
    private String selfieCamera; // VD: "12MP"

}