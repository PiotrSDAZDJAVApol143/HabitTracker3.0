package com.example.authorizationapi.dto;


import com.example.authorizationapi.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class UserRegisterDto {

    @Length(min=6,max = 25, message = "Login should have between 6 and 25 characters")
    private String login;

    @Email
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(min=6,max = 25, message = "Password should have between 6 and 25 characters")
    private String password;

    private Role role;
}
