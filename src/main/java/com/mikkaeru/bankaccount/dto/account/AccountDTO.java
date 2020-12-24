package com.mikkaeru.bankaccount.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikkaeru.bankaccount.domain.model.account.Account;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDTO {

    @NotNull
    private UUID externalId;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @CPF
    @NotBlank
    private String cpf;

    @NotNull
    private String dateBirth;

    public Account convertToEntity() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(skipIdFieldsMap);
        return mapper.map(this, Account.class);
    }

    @JsonIgnore
    PropertyMap<AccountDTO, Account> skipIdFieldsMap = new PropertyMap<>() {
        @Override
        protected void configure() {
            skip().setId(null);
        }
    };
}
