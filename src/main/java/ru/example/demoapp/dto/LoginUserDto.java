package ru.example.demoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema(description = "DTO for login data")
public class LoginUserDto {
    @NotEmpty
    @Size(min = 1, max = 30, message = "Username size should be greater than 1 and less than 30 characters")
    @Schema(description = "username")
    private String username;
    @Schema(description = "password")
    private String password;
}
