package edu.andrew.dao;

import edu.andrew.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private SessionFactory sessionFactory;

    public AccountRepositoryImpl() {
        this.sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public List<Account> getAllAccounts() {
        EntityManager em = sessionFactory.createEntityManager();
        List<Account> list = em.createQuery("from Account a", Account.class).getResultList();
        em.close();
        return list;
    }

    public Account findBy(String accountNumber) {
        EntityManager em = sessionFactory.createEntityManager();
        return em.find(Account.class, accountNumber);
    }

    public void save(Account account) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    public Account update(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(account);
        return account;
    }
}
