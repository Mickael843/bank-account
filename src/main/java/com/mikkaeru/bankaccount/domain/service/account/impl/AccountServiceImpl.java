package com.mikkaeru.bankaccount.domain.service.account.impl;

import com.mikkaeru.bankaccount.domain.exception.DomainException;
import com.mikkaeru.bankaccount.domain.exception.DuplicatedDataException;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
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
    public Owner create(Owner owner) {

        accountValidation(owner);

        owner.setCreatedAt(OffsetDateTime.now());

        return accountRepository.save(owner);
    }

    @Override
    public Owner update(Owner owner) {

        Optional<Owner> accountOptional = accountRepository.findByExternalId(owner.getExternalId());

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        populateFields(owner, accountOptional.get());

        accountValidation(owner);

        return accountRepository.save(owner);
    }

    @Override
    public Owner findOne(UUID externalId) {

        Optional<Owner> accountOptional = accountRepository.findByExternalId(externalId);

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        return accountOptional.get();
    }

    @Override
    public Page<Owner> findAllPages(Integer page) {

        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by("fullName"));

        Page<Owner> accountPage = accountRepository.findAll(pageRequest);

        if (page == 0 && accountPage.getTotalElements() == 0) {
            throw new EntityNotFoundException("Nenhuma página foi encontrada!");
        }

        return accountPage;
    }

    @Override
    public void delete(UUID externalId) {

        Optional<Owner> accountOptional = accountRepository.findByExternalId(externalId);

        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND);
        }

        accountRepository.delete(accountOptional.get());
    }

    // Verifica a integridade dos dados contidos no objeto 'account'
    // caso algum dado obrigatório esteja nulo ou em brando
    // será dispara a exceção 'DataIntegrityViolationException' retornando
    // status 400 para o client e informando o campo incorreto.
    private void accountValidation(Owner owner) {

        if (owner.getExternalId() == null) {
            throw new DataIntegrityViolationException("ExternalId não pode ser nulo!");
        }

        if (owner.getFullName().isBlank() || owner.getFullName() == null) {
            throw new DataIntegrityViolationException("FullName não pode ser nulo!");
        }

        if (owner.getEmail().isBlank() || owner.getEmail() == null) {
            throw new DataIntegrityViolationException("Email não pode ser nulo!");
        }

        if (owner.getCpf().isBlank() || owner.getCpf() == null) {
            throw new DataIntegrityViolationException("cpf não pode ser nulo!");
        }

        if (owner.getBirth() == null) {
            throw new DataIntegrityViolationException("Birth não pode ser nulo!");
        }

        if (owner.getId() == null) {
            duplicateFieldsValidation(owner);
        }
    }

    // Irá verificar se existem campos duplicados e retornar um objeto com os campos que não
    // vão ser alterados preenchidos.
    private void duplicateFieldsValidation(Owner owner) {
        StringBuilder duplicatedFields = new StringBuilder();

        List<Owner> accountsSaved = accountRepository.findAll();

        // Percorre a lista de contas já salvas no banco de dados e se existir
        // algum dados igual em nosso objeto 'account' uma exceção será lançada.
        for (Owner ownerInDatabase : accountsSaved) {

            if (owner.getExternalId().equals(ownerInDatabase.getExternalId())) {
                duplicatedFields.append("externalId|");
            }

            if (owner.getFullName().equals(ownerInDatabase.getFullName())) {
                duplicatedFields.append("fullName|");
            }

            if (owner.getEmail().equals(ownerInDatabase.getEmail())) {
                duplicatedFields.append("email|");
            }

            if (owner.getCpf().equals(ownerInDatabase.getCpf())) {
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
    private void populateFields(Owner owner, Owner ownerInDatabase) {

        if (owner.getFullName() == null) {
            owner.setFullName(ownerInDatabase.getFullName());
        }

        if (owner.getEmail() == null) {
            owner.setEmail(ownerInDatabase.getEmail());
        }

        if (owner.getCpf() == null) {
            owner.setCpf(ownerInDatabase.getCpf());
        }

        if (owner.getBirth() == null) {
            owner.setBirth(ownerInDatabase.getBirth());
        }

        owner.setId(ownerInDatabase.getId());
        owner.setUpdatedAt(OffsetDateTime.now());
        owner.setCreatedAt(ownerInDatabase.getCreatedAt());
    }
}
