package com.lmt.ecommerce.laptophub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {
    @NotBlank(message = "Email can't be empty")
    @Schema(
            description = "Email address of the user",
            example = "lmt@kanban.com"
    )
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Schema(
            description = "The password of the user",
            example = "StrongP@ss123!"
    )
    private String password;
}
