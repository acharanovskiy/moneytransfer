package edu.andrew.dao;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class Database {

    public static void init() {
        EntityManager em = SessionFactoryProvider.getSessionFactory().createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery(createTableScript()).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    private static String createTableScript() {
        return convertStreamToString(Database.class.getClassLoader().getResourceAsStream("sql/createTable.sql"));
    }

    private static String convertStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
    }
}
