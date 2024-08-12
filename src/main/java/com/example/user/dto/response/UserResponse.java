package com.example.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse implements Serializable {

    Long id;

    @NotBlank(message = "username must not blank")
    private String username;

    @NotBlank(message = "firstName must not blank ")
    private String firstName;

    @NotBlank(message = "lastName must not blank")
    private String lastName;

    @NotBlank(message = "email must not blank")
    private String email;

    @NotBlank(message = "phone must not blank")
    private String phone;



}
