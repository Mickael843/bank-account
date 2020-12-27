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

    @ManyToOne
    @Column(name = "owner_account", nullable = false)
    private Owner owner;

    @Column(unique = true, nullable = false)
    private UUID externalId;

    @Enumerated(ORDINAL)
    @Column(nullable = false)
    private AccountType type;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String securityCode;

    @Column(nullable = false)
    private OffsetDateTime valid;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public OffsetDateTime getValid() {
        return valid;
    }

    public void setValid(OffsetDateTime valid) {
        this.valid = valid;
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
        return Objects.equals(id, account.id) && Objects.equals(owner, account.owner) && Objects.equals(externalId, account.externalId) && type == account.type && Objects.equals(bankCode, account.bankCode) && Objects.equals(agency, account.agency) && Objects.equals(number, account.number) && Objects.equals(securityCode, account.securityCode) && Objects.equals(valid, account.valid) && Objects.equals(createdAt, account.createdAt) && Objects.equals(updatedAt, account.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, externalId, type, bankCode, agency, number, securityCode, valid, createdAt, updatedAt);
    }

    public AccountDTO convertToDTO() {
        return new ModelMapper().map(this, AccountDTO.class);
    }
}
