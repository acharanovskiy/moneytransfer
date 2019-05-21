package edu.andrew.rest;

import edu.andrew.RestServer;
import edu.andrew.dao.Database;
import edu.andrew.model.Account;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MoneyTransferResourceTest {
    private static final String SERVICE_URL = "http://localhost:8181/rest";
    private static final RestServer server = new RestServer();

    @BeforeClass
    public static void setup() {
        Database.init();
        Database.createDummyAccounts();
        server.start();
    }

    @Test
    public void test() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SERVICE_URL).path("/account");
        Response response = target.request().get();

        List<Account> responseEntity = (List<Account>) response.readEntity(List.class);

        assertEquals(2, responseEntity.size());
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
}
