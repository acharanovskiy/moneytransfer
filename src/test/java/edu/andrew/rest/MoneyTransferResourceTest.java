package edu.andrew.rest;

import edu.andrew.RestServer;
import edu.andrew.dao.Database;
import edu.andrew.model.Account;
import edu.andrew.util.TestingUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MoneyTransferResourceTest {
    private static final String SERVICE_URL = "http://localhost:8181/rest";
    private static final RestServer server = new RestServer();
    private static final WebTarget resource = ClientBuilder.newClient().target(SERVICE_URL).path("/account");

    @BeforeClass
    public static void setup() {
        Database.init();
        TestingUtil.createAccount("881234", BigDecimal.valueOf(333.33));
        TestingUtil.createAccount("991234", BigDecimal.valueOf(123.45));
        server.start();
    }

    @Test
    public void testInsufficientFunds() {
        MoneyTransferRequest request = createRequest("881234", "991234", BigDecimal.valueOf(500));
        Response response = resource.request().post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertEquals("Not enough funds for this transfer",  response.readEntity(String.class));
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }

    private MoneyTransferRequest createRequest(String from, String to, BigDecimal amount) {
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setSenderAccount(from);
        request.setReceiverAccount(to);
        request.setAmount(amount);
        return request;
    }
}
