package edu.andrew;

import edu.andrew.dao.Database;

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
            }
        }
    }
}
