package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.TransactionDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionDAOImpl tdi;

    @PersistenceContext(name = "Transaction")
    EntityManager em;

    @Transactional
    public Transaction transaction(Integer idClient) {
        return tdi.findTransaction(idClient);
    }

    @Transactional
    public Transaction getMontantTransaction(Integer idTransaction) {
        Transaction t = em.find(Transaction.class, idTransaction);
        t.setTransactionStatue(true);
        em.persist(t);
        return t;
    }
}
