package com.mikkaeru.bankaccount.repository;

import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByExternalId(UUID externalId);
}
