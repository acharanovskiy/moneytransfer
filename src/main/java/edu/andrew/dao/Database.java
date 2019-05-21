package edu.andrew.dao;

import edu.andrew.model.Account;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.stream.Collectors;

public final class Database {
    private static final AccountRepository repository = new AccountRepositoryImpl();

    public static void init() {
        EntityManager em = SessionFactoryProvider.getSessionFactory().createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery(createTableScript()).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public static void createDummyAccounts() {
        Account acc1 = new Account();
        acc1.setHolder("Jack Johnson");
        acc1.setFunds(BigDecimal.valueOf(10));
        acc1.setNumber("1234");

        Account acc2 = new Account();
        acc2.setHolder("John Jackson");
        acc2.setFunds(BigDecimal.valueOf(20));
        acc2.setNumber("5678");

        repository.save(acc1);
        repository.save(acc2);
    }

    private static String createTableScript() {
        return convertStreamToString(Database.class.getClassLoader().getResourceAsStream("sql/createTable.sql"));
    }

    private static String convertStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
    }
}
