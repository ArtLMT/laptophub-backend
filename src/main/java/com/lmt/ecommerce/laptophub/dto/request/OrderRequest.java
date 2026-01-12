package com.lmt.ecommerce.laptophub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank(message = "User ID can't be null")
    private Long userId;

    private BigDecimal totalAmount;

    // tạo trước thôi
    private String shippingAddress;
    @NotBlank(message = "Payment method can't be null")
    private String paymentMethod;
    // Cái này có thể khác với phoneNumber của thằng tạo đơn nên cho vô
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Invalid Phone Number")
    @Schema(
            description = "Phone number of the user",
            example = "0356897896"
    )
    private String phoneNumber;
    private String note;
}
