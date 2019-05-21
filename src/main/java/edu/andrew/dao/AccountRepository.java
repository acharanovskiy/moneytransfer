package edu.andrew.dao;

import edu.andrew.model.Account;

public interface AccountRepository {
    void save(Account account);
    Account update(Account account);
    Account findBy(String accountNumber);
}
