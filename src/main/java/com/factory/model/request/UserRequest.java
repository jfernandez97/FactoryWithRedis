package com.factory.model.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "El campo type no puede estar vacio")
    @Pattern(regexp = "^(ADMIN|CLIENT|EDITOR)$", message = "El campo type solo acepta: ADMIN, CLIENT o EDITOR")
    private String type;
    @NotBlank(message = "El campo nombre no puede estar vacio")
    private String name;
    @Email
    private String email;
}
