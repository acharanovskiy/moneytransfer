package edu.andrew.service;

import edu.andrew.TransferFailedException;
import edu.andrew.dao.AccountRepository;
import edu.andrew.dao.AccountRepositoryImpl;
import edu.andrew.dao.Database;
import edu.andrew.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static edu.andrew.dao.model.AccountBuilder.accountBuilder;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MoneyTransferServiceTest {
    private AccountRepository repository = new AccountRepositoryImpl();
    private MoneyTransferService transferService = new MoneyTransferServiceImpl(repository);

    @BeforeClass
    public static void prepareDB() {
        Database.init();
    }

    @Before
    public void setupAccounts() {
        Account sender = accountBuilder().with("1234").with(BigDecimal.valueOf(1.23)).build();
        Account recipient = accountBuilder().with("5678").with(BigDecimal.valueOf(3.21)).build();
        transferService.save(sender);
        transferService.save(recipient);
    }

    @Test
    public void test1() throws TransferFailedException {
        transferService.transfer("1234", "5678", BigDecimal.ONE);

        assertEquals(BigDecimal.valueOf(0.23), repository.findBy("1234").getFunds().stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(4.21), repository.findBy("5678").getFunds().stripTrailingZeros());
    }

    @After
    public void rollback() {

    }
}
