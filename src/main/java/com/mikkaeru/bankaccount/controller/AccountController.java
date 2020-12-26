package com.mikkaeru.bankaccount.controller;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.createAccount;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.updateAccount;
import com.mikkaeru.bankaccount.dto.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // Irá converter uma string em no seguinte formato 'dd/MM/yyyy' em um objeto LocalDate
    private LocalDate convertDate(String date) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return  LocalDate.parse(date, format);
    }

    private Account convertDTO(AccountDTO accountDTO) {

        LocalDate birth = null;

        if (accountDTO.getBirth() != null) {
            birth = convertDate(accountDTO.getBirth());
        }

        Account account = accountDTO.convertToEntity();
        account.setBirth(birth);

        return account;
    }
}
