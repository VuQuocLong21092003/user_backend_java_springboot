package com.example.user.dto.request;

import com.example.user.untils.Gender;
import com.example.user.untils.Status;
import com.example.user.untils.UserType;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto implements Serializable {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is not valid")
    private String phone;

    @NotNull(message = "Birthday cannot be null")
    private Date birthday;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotEmpty(message = "Addresses cannot be empty")
    private Set<AddressDto> address;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Gender cannot be null")
    private Status status;

    @NotNull(message = "User type cannot be null")
    private UserType userType;


}
