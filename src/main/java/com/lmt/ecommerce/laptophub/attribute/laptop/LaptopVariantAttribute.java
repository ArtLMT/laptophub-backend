package com.lmt.ecommerce.laptophub.attribute.laptop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaptopVariantAttribute {
    private Integer ram;            // 16GB
    private String RAMType;        // DDR4
    private Integer storageCapacity;// 512GB
    private String storageType;     // SSD
    private String color;           // Black
}
