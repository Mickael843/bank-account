package com.mikkaeru.bankaccount.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDTO {

    @NotNull(
            message = "ExternalId não pode ser nulo!",
            groups = {AccountValidate.createAccount.class})
    private UUID externalId;

    @NotBlank(
            message = "FullName não pode estar em branco!",
            groups = {AccountValidate.createAccount.class})
    private String fullName;

    @Email
    @NotBlank(
            message = "Email não pode estar em branco!",
            groups = {AccountValidate.createAccount.class})
    private String email;

    @CPF
    @NotBlank(
            message = "CPF não pode estar em branco!",
            groups = {AccountValidate.createAccount.class})
    private String cpf;

    @NotNull(
            message = "Birth não pode ser nulo!",
            groups = {AccountValidate.createAccount.class})
    private String birth;

    public Owner convertToEntity() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(skipIdFieldsMap);
        return mapper.map(this, Owner.class);
    }

    @JsonIgnore
    PropertyMap<AccountDTO, Owner> skipIdFieldsMap = new PropertyMap<>() {
        @Override
        protected void configure() {
            skip().setId(null);
        }
    };
}
