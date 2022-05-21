package com.patryk.shop.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.patryk.shop.validator.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.history.RevisionMetadata;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PasswordValid
public class UserDto extends AuditableDto{

    private Long id;
    @NotBlank
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    private Integer revisionNumber;
    private RevisionMetadata.RevisionType operationType;
    private Set<String> roles;
}
