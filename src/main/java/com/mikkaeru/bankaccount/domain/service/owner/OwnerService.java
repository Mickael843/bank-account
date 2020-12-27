package com.mikkaeru.bankaccount.domain.service.owner;

import com.mikkaeru.bankaccount.domain.model.owner.Owner;
import org.springframework.data.domain.Page;

public interface OwnerService {

    Owner create(Owner owner);

    Owner update(Owner owner);

    Owner findOne(String cpf);

    Page<Owner> findAllPages(Integer page);

    void delete(String cpf);
}
