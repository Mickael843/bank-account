package com.mikkaeru.bankaccount.domain.service.account;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AccountService {

    Account create(Owner convertDTO);

    void update(Owner convertDTO);

    Account findOne(UUID externalId);

    Page<Account> findAllPages(Integer page);

    void delete(UUID externalId);
}
