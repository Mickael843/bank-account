package com.mikkaeru.bankaccount.dto.owner;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class OwnerDTO {

    @NotBlank(message = "FullName não pode estar em branco!")
    private String fullName;

    @Email
    @NotBlank(message = "Email não pode estar em branco!")
    private String email;

    @CPF
    @NotBlank(message = "CPF não pode estar em branco!")
    private String cpf;

    @NotNull(message = "Birth não pode ser nulo!")
    private String birth;
}
