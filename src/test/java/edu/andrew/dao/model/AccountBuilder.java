package edu.andrew.dao.model;

import edu.andrew.model.Account;

import java.math.BigDecimal;

public class AccountBuilder {
    private Account account = new Account();

    public static AccountBuilder accountBuilder() {
        return new AccountBuilder();
    }

    public AccountBuilder with(String number) {
        this.account.setNumber(number);
        return this;
    }

    public AccountBuilder with(BigDecimal funds) {
        this.account.setFunds(funds);
        return this;
    }

    public Account build() {
        account.setHolder("Jack Johnson");
        return account;
    }
}
