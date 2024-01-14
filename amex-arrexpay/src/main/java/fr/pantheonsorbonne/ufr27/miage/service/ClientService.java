package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClientService{

    @PersistenceContext(name = "Client")
    EntityManager em;

    @Inject
    CashBackService cashBackService;

    @Transactional
    public Client client(Client client) {
        em.persist(client);
        return client;
    }

    public void ajoutArgent(float montantArgent, int clientId) {

    }
}