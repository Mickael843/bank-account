package com.mikkaeru.bankaccount.repository.owner;

import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByCpf(String cpf);
}
