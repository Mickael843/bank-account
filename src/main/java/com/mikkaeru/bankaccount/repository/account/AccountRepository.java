package com.mikkaeru.bankaccount.repository.account;

import com.mikkaeru.bankaccount.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByExternalId(UUID externalId);
}
