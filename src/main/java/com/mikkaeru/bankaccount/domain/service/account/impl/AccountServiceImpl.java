package com.mikkaeru.bankaccount.domain.service.account.impl;

import com.mikkaeru.bankaccount.domain.exception.DomainException;
import com.mikkaeru.bankaccount.domain.exception.DuplicatedDataException;
import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mikkaeru.bankaccount.domain.exception.DomainException.Error.INVALID_DUPLICATED_DATA;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private AccountRepository accountRepository;

    private static final Integer ITEMS_PER_PAGE = 4;
    private static final String NOT_FOUND = "Conta não encontrada!";

    @Override
    public Account create(Account account) {

        accountValidation(account);

        account.setCreatedAt(OffsetDateTime.now());

        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {

        Optional<Account> accountOptional = accountRepository.findByExternalId(account.getExternalId());

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        populateFields(account, accountOptional.get());

        accountValidation(account);

        return accountRepository.save(account);
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

        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by("fullName"));

        Page<Account> accountPage = accountRepository.findAll(pageRequest);

        if (page == 0 && accountPage.getTotalElements() == 0) {
            throw new EntityNotFoundException("Nenhuma página foi encontrada!");
        }

        return accountPage;
    }

    // Verifica a integridade dos dados contidos no objeto 'account'
    // caso algum dado obrigatório esteja nulo ou em brando
    // será dispara a exceção 'DataIntegrityViolationException' retornando
    // status 400 para o client e informando o campo incorreto.
    private void accountValidation(Account account) {

        if (account.getExternalId() == null) {
            throw new DataIntegrityViolationException("ExternalId não pode ser nulo!");
        }

        if (account.getFullName().isBlank() || account.getFullName() == null) {
            throw new DataIntegrityViolationException("FullName não pode ser nulo!");
        }

        if (account.getEmail().isBlank() || account.getEmail() == null) {
            throw new DataIntegrityViolationException("Email não pode ser nulo!");
        }

        if (account.getCpf().isBlank() || account.getCpf() == null) {
            throw new DataIntegrityViolationException("cpf não pode ser nulo!");
        }

        if (account.getBirth() == null) {
            throw new DataIntegrityViolationException("Birth não pode ser nulo!");
        }

        if (account.getId() == null) {
            duplicateFieldsValidation(account);
        }
    }

    // Irá verificar se existem campos duplicados e retornar um objeto com os campos que não
    // vão ser alterados preenchidos.
    private void duplicateFieldsValidation(Account account) {
        StringBuilder duplicatedFields = new StringBuilder();

        List<Account> accountsSaved = accountRepository.findAll();

        // Percorre a lista de contas já salvas no banco de dados e se existir
        // algum dados igual em nosso objeto 'account' uma exceção será lançada.
        for (Account accountInDatabase: accountsSaved) {

            if (account.getExternalId().equals(accountInDatabase.getExternalId())) {
                duplicatedFields.append("externalId|");
            }

            if (account.getFullName().equals(accountInDatabase.getFullName())) {
                duplicatedFields.append("fullName|");
            }

            if (account.getEmail().equals(accountInDatabase.getEmail())) {
                duplicatedFields.append("email|");
            }

            if (account.getCpf().equals(accountInDatabase.getCpf())) {
                duplicatedFields.append("cpf");
            }

            if (duplicatedFields.length() != 0) {
                DomainException.Error error = INVALID_DUPLICATED_DATA;
                error.setFields(DomainException.Error.convertToFieldList(duplicatedFields.toString()));
                throw new DuplicatedDataException(error);
            }
        }
    }

    // Verifica os campos que não foram enviados na requisição
    // e os preenche com os dados já existentes no banco de dados
    private void populateFields(Account account, Account accountInDatabase) {

        if (account.getFullName() == null) {
            account.setFullName(accountInDatabase.getFullName());
        }

        if (account.getEmail() == null) {
            account.setEmail(accountInDatabase.getEmail());
        }

        if (account.getCpf() == null) {
            account.setCpf(accountInDatabase.getCpf());
        }

        if (account.getBirth() == null) {
            account.setBirth(accountInDatabase.getBirth());
        }

        account.setId(accountInDatabase.getId());
        account.setUpdatedAt(OffsetDateTime.now());
        account.setCreatedAt(accountInDatabase.getCreatedAt());
    }
}
