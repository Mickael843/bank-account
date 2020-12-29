package com.mikkaeru.bankaccount.domain.service.account.impl;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.service.owner.OwnerService;
import com.mikkaeru.bankaccount.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private OwnerService ownerService;

    @Autowired private AccountRepository accountRepository;

    private static final Integer ITEMS_PER_PAGE = 4;

    private static final String AGENCY = "0001";
    private static final String BANK_CODE = "077";

    private static final String NOT_FOUND = "A conta não foi encontrada!";

    @Override
    public Account create(Account account) {

        account.setAgency(AGENCY);
        account.setBankCode(BANK_CODE);

        account.generateSecurityCode();
        account.generateAccountNumber();
        account.generateExpirationDate();

        accountValidation(account);

        account.setOwnerAccount(ownerService.create(account.getOwnerAccount()));

        account.setCreatedAt(OffsetDateTime.now());

        return accountRepository.save(account);
    }

    @Override
    public void update(Account account) {

        Optional<Account> accountOptional = accountRepository.findByExternalId(account.getExternalId());

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        if (account.getOwnerAccount() != null) {
            ownerService.update(account.getOwnerAccount());
        }

        populateFields(account, accountOptional.get());

        accountValidation(account);

        accountRepository.save(account);
    }

    @Override
    public Account findOne(UUID externalId) {

        Optional<Account> accountOptional = accountRepository.findByExternalId(externalId);

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        return accountOptional.get();
    }

    @Override
    public Page<Account> findAllPages(Integer page) {

        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by("createdAt"));

        Page<Account> accountPage = accountRepository.findAll(pageRequest);

        if (page == 0 && accountPage.getTotalElements() == 0) {
            throw new EntityNotFoundException("Nenhuma página foi encontrada!");
        }

        return accountPage;
    }

    @Override
    public void delete(UUID externalId) {

        Optional<Account> accountOptional = accountRepository.findByExternalId(externalId);

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        accountRepository.delete(accountOptional.get());

        ownerService.delete(accountOptional.get().getOwnerAccount().getCpf());
    }

    // Verifica a integridade dos dados contidos no objeto 'account'
    // caso algum dado obrigatório esteja nulo ou em brando
    // será dispara a exceção 'DataIntegrityViolationException' retornando
    // status 400 para o client e informando o campo incorreto.
    private void accountValidation(Account account) {

        if (account.getAccountType() == null) {
            throw new DataIntegrityViolationException("O tipo da conta não pode ser nulo!");
        }

        if (account.getBankCode() == null) {
            throw new DataIntegrityViolationException("O código do banco não pode ser nulo!");
        }

        if (account.getAgency() == null) {
            throw new DataIntegrityViolationException("A agência não pode ser nula!");
        }

        if (account.getAccountNumber() == null) {
            throw new DataIntegrityViolationException("O número da conta não pode ser nulo!");
        }

        if (account.getSecurityCode() == null) {
            throw new DataIntegrityViolationException("O código de segurança não pode ser nulo!");
        }

        if (account.getExpirationDate() == null) {
            throw new DataIntegrityViolationException("A data de validade não pode ser nulo!");
        }

        if (account.getOwnerAccount() == null) {
            throw new DataIntegrityViolationException("O proprietário da conta não pode ser nulo!");
        }
    }

    private void populateFields(Account account, Account accountInDatabase) {

        if (account.getAccountType() == null) {
            account.setAccountType(accountInDatabase.getAccountType());
        }

        if (account.getOwnerAccount() == null) {
            account.setOwnerAccount(accountInDatabase.getOwnerAccount());
        }

        account.setId(accountInDatabase.getId());
        account.setUpdatedAt(OffsetDateTime.now());
        account.setAgency(accountInDatabase.getAgency());
        account.setBankCode(accountInDatabase.getBankCode());
        account.setCreatedAt(accountInDatabase.getCreatedAt());
        account.setSecurityCode(accountInDatabase.getSecurityCode());
        account.setAccountNumber(accountInDatabase.getAccountNumber());
        account.setExpirationDate(accountInDatabase.getExpirationDate());
    }
}
