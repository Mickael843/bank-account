package com.mikkaeru.bankaccount.domain.service.account;

import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AccountService {

    Owner create(Owner owner);

    Owner update(Owner owner);

    Owner findOne(UUID externalId);

    Page<Owner> findAllPages(Integer page);

    void delete(UUID externalId);
}
