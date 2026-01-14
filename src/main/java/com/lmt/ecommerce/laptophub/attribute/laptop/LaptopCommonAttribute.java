package com.lmt.ecommerce.laptophub.attribute.laptop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopCommonAttribute {
    private String cpu;
    private String gpu;
    private Double screenSize;
    private String resolution;
    private Double weight;
}