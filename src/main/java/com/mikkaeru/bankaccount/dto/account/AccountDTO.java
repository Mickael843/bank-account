package com.mikkaeru.bankaccount.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.model.enumeration.AccountType;
import com.mikkaeru.bankaccount.domain.validation.AccountValidate.createAccount;
import com.mikkaeru.bankaccount.domain.validation.AccountValidate.updateAccount;
import com.mikkaeru.bankaccount.dto.owner.OwnerDTO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class AccountDTO {

    @NotNull(
            message = "ExternalId não pode ser nulo!",
            groups = {createAccount.class})
    private UUID externalId;

    @NotNull(
            message = "O tipo da conta não pode ser nulo!",
            groups = {createAccount.class})
    private AccountType accountType;

    @NotNull(
            message = "Owner não pode ser nulo!",
            groups = {createAccount.class, updateAccount.class})
    private OwnerDTO owner;

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
