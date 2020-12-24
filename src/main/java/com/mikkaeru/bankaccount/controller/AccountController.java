package com.mikkaeru.bankaccount.controller;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.dto.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1/accounts", produces = APPLICATION_JSON_VALUE)
public class AccountController {

    // TODO Criar um método que transforme uma string em um determinado formato em um objeto LocalDate.

    @Autowired private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AccountDTO accountDTO) {
        Account account = accountService.create(accountDTO.convertToEntity());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(account.getExternalId())
                .toUri()
        ).build();
    }

}
