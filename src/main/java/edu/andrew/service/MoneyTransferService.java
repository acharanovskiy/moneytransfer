package edu.andrew.service;

import edu.andrew.model.Account;

import java.math.BigDecimal;

public interface MoneyTransferService {
    void save(Account account);
    Account findBy(String accountNumber);
    boolean transfer(String from, String to, BigDecimal amount);
}
