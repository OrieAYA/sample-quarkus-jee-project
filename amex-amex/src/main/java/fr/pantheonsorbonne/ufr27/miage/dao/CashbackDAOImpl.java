package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Cashback;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CashbackDAOImpl implements CashbackDAO{

    @PersistenceContext
    EntityManager em;

    @Override
    public Cashback createNewCashback(Integer idClient, Integer idTransaction, float tauxCashback) {
        Cashback c = new Cashback(idClient,idTransaction,tauxCashback);
        em.persist(c);
        return c;
    }
}
