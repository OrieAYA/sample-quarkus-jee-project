package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.CashBackDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CashbackServiceImpl implements CashbackService {

    @PersistenceContext()
    EntityManager em;

    @Inject
    CashBackDAO cashBackDAO;
    @Override
    @Transactional
    public Double getCashbackAmount(int idClient) throws CustomerNotFoundException {
       return cashBackDAO.findCashback(idClient);
    }

    @Override
    @Transactional
    public CashBack cashback(int idClient) {
        CashBack c = new CashBack(idClient);
        if(em.find(CashBack.class, idClient)!=null){
            return c;
        }
        em.persist(c);
        return c;
    }

    @Override
    @Transactional
    public boolean verifPrice(double price) {
        return !(price > 5000.0);
    }

    @Override
    @Transactional
    public Integer returnTaux(Integer idClient) {
        CashBack c = em.find(CashBack.class, idClient);
        return (int) c.getTauxCashback();
    }

    @Override
    @Transactional
    public void addMontant(Integer idClient, double montant) {
        CashBack c = em.find(CashBack.class, idClient);
        c.setMontantCashback(c.getMontantCashback()+montant);
        em.persist(c);
    }
}