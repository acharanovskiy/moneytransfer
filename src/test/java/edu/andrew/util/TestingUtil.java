package edu.andrew.util;

import edu.andrew.dao.AccountRepository;
import edu.andrew.dao.AccountRepositoryImpl;
import edu.andrew.model.Account;

import java.math.BigDecimal;

import static edu.andrew.dao.model.AccountBuilder.accountBuilder;

public class TestingUtil {
    private static final AccountRepository repository = new AccountRepositoryImpl();

    public static void createAccount(String accountNumber, BigDecimal funds) {
        Account account = accountBuilder()
                .with(accountNumber)
                .with(funds)
                .build();
        repository.save(account);
    }

}
