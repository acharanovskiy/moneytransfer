package edu.andrew.service;

import edu.andrew.dao.AccountRepository;
import edu.andrew.dao.SessionFactoryProvider;
import edu.andrew.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class MoneyTransferServiceImpl implements MoneyTransferService {
    private SessionFactory sessionFactory;
    private AccountRepository accountRepository;

    public MoneyTransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findBy(String accountNumber) {
        return accountRepository.findBy(accountNumber);
    }

    @Override
    public boolean transfer(String from, String to, BigDecimal amount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Account sender = findBy(from);
        Account receiver = findBy(to);
        sender.setFunds(sender.getFunds().subtract(amount));
        receiver.setFunds(receiver.getFunds().add(amount));
        accountRepository.update(sender);
        accountRepository.update(receiver);
        tx.commit();
        return true;
    }
}
