package com.mikkaeru.bankaccount.controller;

import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.domain.service.account.AccountService;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.createAccount;
import com.mikkaeru.bankaccount.domain.validation.account.AccountValidate.updateAccount;
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

        Owner owner = accountService.create(convertDTO(accountDTO));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(owner.getExternalId())
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

    private Owner convertDTO(AccountDTO accountDTO) {

        LocalDate birth = null;

        if (accountDTO.getBirth() != null) {
            birth = convertDate(accountDTO.getBirth());
        }

        Owner owner = accountDTO.convertToEntity();
        owner.setBirth(birth);

        // Expressões regulares
        // Retira da string tudo que não for um número
        if (owner.getCpf() != null) {
            owner.setCpf(owner.getCpf().replaceAll("[^0-9]", ""));
        }

        return owner;
    }

    private Page<AccountDTO> convertToPageDTO(Page<Owner> accountPage) {

        List<AccountDTO> outputs = new ArrayList<>();

        accountPage.get().forEach(account -> {
            outputs.add(account.convertToDTO());
        });

        return new PageImpl<>(outputs);
    }
}
