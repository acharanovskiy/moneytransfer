package edu.andrew.util;

import edu.andrew.dao.AccountRepository;
import edu.andrew.dao.AccountRepositoryImpl;
import edu.andrew.dao.SessionFactoryProvider;
import edu.andrew.model.Account;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static edu.andrew.dao.model.AccountBuilder.accountBuilder;

public class TestingUtil {
    private static final AccountRepository repository = new AccountRepositoryImpl();

    public static Account createAccount(String accountNumber, BigDecimal funds) {
        Account account = accountBuilder()
                .with(accountNumber)
                .with(funds)
                .build();
        repository.save(account);
        return account;
    }

    public static void clearDatabase() {
        EntityManager em = SessionFactoryProvider.getSessionFactory().createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DROP TABLE ACCOUNT").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
