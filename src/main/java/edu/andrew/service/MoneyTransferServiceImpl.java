package edu.andrew.service;

import edu.andrew.TransferFailedException;
import edu.andrew.dao.AccountRepository;
import edu.andrew.dao.SessionFactoryProvider;
import edu.andrew.model.Account;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class MoneyTransferServiceImpl implements MoneyTransferService {
    private static final Logger log =  Logger.getLogger(MoneyTransferServiceImpl.class);
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
    public void transfer(Account from, Account to, BigDecimal amount) throws TransferFailedException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            from.setFunds(from.getFunds().subtract(amount));
            to.setFunds(to.getFunds().add(amount));
            accountRepository.update(from);
            accountRepository.update(to);
        } catch (Exception e) {
            tx.rollback();
            log.error(String.format("Failed to transfer money from %s to %s", from, to), e);
            throw new TransferFailedException(String.format("Failed to transfer money from %s to %s", from, to), e);
        }
        tx.commit();
    }
}
