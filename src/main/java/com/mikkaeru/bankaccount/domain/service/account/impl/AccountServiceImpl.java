package com.mikkaeru.bankaccount.domain.service.account.impl;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.service.owner.OwnerService;
import com.mikkaeru.bankaccount.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private OwnerService ownerService;

    @Autowired private AccountRepository accountRepository;

    @Override
    public Account create(Owner convertDTO) {
        return null;
    }

    @Override
    public void update(Owner convertDTO) {

    }

    @Override
    public Account findOne(UUID externalId) {
        return null;
    }

    @Override
    public Page<Account> findAllPages(Integer page) {
        return null;
    }

    @Override
    public void delete(UUID externalId) {

    }
}
