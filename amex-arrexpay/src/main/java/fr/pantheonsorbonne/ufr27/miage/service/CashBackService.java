package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CashBackService {

    @PersistenceContext(name = "Cashback")
    EntityManager em;

    @Transactional
    public CashBack cashback(CashBack CashBack) {
        em.persist(CashBack);
        return CashBack;
    }
}