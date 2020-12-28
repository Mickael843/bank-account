package com.mikkaeru.bankaccount.controller;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.validation.AccountValidate.createAccount;
import com.mikkaeru.bankaccount.domain.validation.AccountValidate.updateAccount;
import com.mikkaeru.bankaccount.dto.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1/accounts", produces = APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@Validated(createAccount.class) @RequestBody AccountDTO accountDTO) {

        Account account = accountService.create(convertDTO(accountDTO));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(account.getExternalId())
                .toUri()
        ).build();
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<?> update(@Validated(updateAccount.class) @RequestBody AccountDTO accountDTO, @PathVariable UUID externalId) {

        accountDTO.setExternalId(externalId);

        accountService.update(convertDTO(accountDTO));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<AccountDTO> getOne(@PathVariable UUID externalId) {
        return ResponseEntity.ok(accountService.findOne(externalId).convertToDTO());
    }

    @GetMapping("page/{page}")
    public ResponseEntity<Page<AccountDTO>> getAll(@PathVariable Integer page) {
        return ResponseEntity.ok(convertToPageDTO(accountService.findAllPages(page)));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<?> delete(@PathVariable UUID externalId) {
        accountService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    // Irá converter uma string em no seguinte formato 'dd/MM/yyyy' em um objeto LocalDate
    private LocalDate convertDate(String date) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return  LocalDate.parse(date, format);
    }

    private Account convertDTO(AccountDTO accountDTO) {

        LocalDate birth = null;

        if (accountDTO.getOwner().getBirth() != null) {
            birth = convertDate(accountDTO.getOwner().getBirth());
        }

        // Expressões regulares
        // Retira da string tudo que não for um número
        if (accountDTO.getOwner().getCpf() != null) {
            accountDTO.getOwner().setCpf(accountDTO.getOwner().getCpf().replaceAll("[^0-9]", ""));
        }

        Account account = accountDTO.convertToEntity();
        account.getOwnerAccount().setBirth(birth);

        return account;
    }

    private Page<AccountDTO> convertToPageDTO(Page<Account> accountPage) {

        List<AccountDTO> outputs = new ArrayList<>();

        accountPage.get().forEach(account -> {
            outputs.add(account.convertToDTO());
        });

        return new PageImpl<>(outputs);
    }
}
