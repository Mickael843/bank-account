package com.mikkaeru.bankaccount.domain.service.account;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AccountService {

    Account create(Account account);

    void update(Account account);

    Account findOne(UUID externalId);

    Page<Account> findAllPages(Integer page);

    void delete(UUID externalId);
}
