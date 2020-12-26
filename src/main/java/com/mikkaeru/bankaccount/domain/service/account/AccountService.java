package com.mikkaeru.bankaccount.domain.service.account;

import com.mikkaeru.bankaccount.domain.model.account.Account;

import java.util.UUID;

public interface AccountService {

    Account create(Account account);

    Account update(Account account);

    Account findOne(UUID externalId);
}
