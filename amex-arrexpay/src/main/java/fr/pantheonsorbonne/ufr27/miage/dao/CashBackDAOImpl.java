package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CashBackDAOImpl implements CashBackDAO{

    @PersistenceContext
    EntityManager em;

    @Override
    public CashBack CreateNewCashback(Integer idClient, Integer idTransaction, float tauxCashback) {
        CashBack CashBack = new CashBack(idClient,idTransaction,tauxCashback);
        em.persist(CashBack);
        return CashBack;
    }

    @Override
    public CashBack FindCashback(Integer idCashback) {
        return null;
    }
}