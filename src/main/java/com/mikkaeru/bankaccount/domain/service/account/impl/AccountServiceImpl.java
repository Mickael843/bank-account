package com.mikkaeru.bankaccount.domain.service.account.impl;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.service.owner.OwnerService;
import com.mikkaeru.bankaccount.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private OwnerService ownerService;

    @Autowired private AccountRepository accountRepository;

    private static final String AGENCY = "";
    private static final String BANK_CODE = "";

    @Override
    public Account create(Account account) {

        account.setAgency(AGENCY);
        account.setBankCode(BANK_CODE);

        account.generateSecurityCode();
        account.generateAccountNumber();
        account.generateExpirationDate();

        accountValidation(account);

        account.setCreatedAt(OffsetDateTime.now());

        return accountRepository.save(account);
    }

    @Override
    public void update(Account account) {

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

    // Verifica a integridade dos dados contidos no objeto 'account'
    // caso algum dado obrigatório esteja nulo ou em brando
    // será dispara a exceção 'DataIntegrityViolationException' retornando
    // status 400 para o client e informando o campo incorreto.
    private void accountValidation(Account account) {

        if (account.getAccountType() == null) {
            throw new DataIntegrityViolationException("O tipo da conta não pode ser nulo!");
        }

        if (account.getBankCode().isBlank() || account.getBankCode() == null) {
            throw new DataIntegrityViolationException("O código do banco não pode ser nulo!");
        }

        if (account.getAgency().isBlank() || account.getAgency() == null) {
            throw new DataIntegrityViolationException("A agência não pode ser nula!");
        }

        if (account.getAccountNumber().isBlank() || account.getAccountNumber() == null) {
            throw new DataIntegrityViolationException("O número da conta não pode ser nulo!");
        }

        if (account.getSecurityCode().isBlank() || account.getSecurityCode() == null) {
            throw new DataIntegrityViolationException("O código de segurança não pode ser nulo!");
        }

        if (account.getExpirationDate() == null) {
            throw new DataIntegrityViolationException("A data de validade não pode ser nulo!");
        }

        if (account.getOwnerAccount() == null) {
            throw new DataIntegrityViolationException("O proprietário da conta não pode ser nulo!");
        }
    }
}
