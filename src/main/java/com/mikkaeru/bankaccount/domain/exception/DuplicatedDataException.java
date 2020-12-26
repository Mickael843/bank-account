package com.mikkaeru.bankaccount.domain.exception;

public class DuplicatedDataException extends DomainException {

    public DuplicatedDataException(Error invalidDuplicatedData) {
        super(invalidDuplicatedData);
    }
}
