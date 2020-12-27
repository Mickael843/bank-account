package com.mikkaeru.bankaccount.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mikkaeru.bankaccount.domain.model.enumeration.AccountType;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.createAccount;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.updateAccount;
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
    private AccountType type;

    @NotNull(
            message = "ExternalId não pode ser nulo!",
            groups = {createAccount.class, updateAccount.class})
    private OwnerDTO owner;

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
