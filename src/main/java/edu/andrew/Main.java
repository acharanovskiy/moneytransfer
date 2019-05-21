package edu.andrew;

import edu.andrew.dao.Database;
import edu.andrew.dao.SessionFactoryProvider;

public class Main {
    private static final RestServer server = new RestServer();

    public static void main(String[] args) {
        Database.init();
        Database.createDummyAccounts();
        server.start();

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                server.stop();
                SessionFactoryProvider.getSessionFactory().close();
            }
        }
    }
}
