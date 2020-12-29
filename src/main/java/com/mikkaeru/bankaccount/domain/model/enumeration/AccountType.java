package com.mikkaeru.bankaccount.domain.model.enumeration;

public enum AccountType {

    CURRENT_ACCOUNT("Conta corrente", "001"),
    SAVINGS_ACCOUNT("Conta poupança", "013");

    String type;
    String operationCode;

    AccountType(String type, String operationCode) {
        this.type = type;
        this.operationCode = operationCode;
    }

    public String getType() {
        return type;
    }

    public String getOperationCode() {
        return operationCode;
    }
}
