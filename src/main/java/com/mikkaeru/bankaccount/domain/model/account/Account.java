package com.mikkaeru.bankaccount.domain.model.account;

import com.mikkaeru.bankaccount.domain.model.enumeration.AccountType;
import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import com.mikkaeru.bankaccount.dto.account.AccountDTO;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.AUTO;

@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Owner ownerAccount;

    @Column(unique = true, nullable = false)
    private UUID externalId;

    @Enumerated(ORDINAL)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String securityCode;

    @Column(nullable = false)
    private OffsetDateTime expirationDate;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Owner ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(ownerAccount, account.ownerAccount) && Objects.equals(externalId, account.externalId) && accountType == account.accountType && Objects.equals(bankCode, account.bankCode) && Objects.equals(agency, account.agency) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(securityCode, account.securityCode) && Objects.equals(expirationDate, account.expirationDate) && Objects.equals(createdAt, account.createdAt) && Objects.equals(updatedAt, account.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerAccount, externalId, accountType, bankCode, agency, accountNumber, securityCode, expirationDate, createdAt, updatedAt);
    }

    public AccountDTO convertToDTO() {
        return new ModelMapper().map(this, AccountDTO.class);
    }

    public void generateSecurityCode() {
        // TODO criar código que gera aleatoriamente um código de segurança de 3 dígitos.
    }

    public void generateAccountNumber() {
        // TODO criar código que gera aleatoriamente um código de segurança de 8 dígitos.
    }

    public void generateExpirationDate() {
        // TODO criar código que gera aleatoriamente uma data de validade de acordo com a data em que a conta foi criada.
    }
}
