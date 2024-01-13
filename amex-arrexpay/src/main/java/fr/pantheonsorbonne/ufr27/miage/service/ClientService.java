package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collection;

@ApplicationScoped
public class ClientService {

    @PersistenceContext(name = "Client")
    EntityManager em;

    @Transactional
    public void ajoutArgent(float montantArgent, int clientId) {

        Client venue = em.find(Client.class);

        Collection<ClientArgent> clientArgents = addArgent(montantArgent);

    }

    @Transactional
    public Client client(Client client) {
        em.persist(client);
        return client;
    }
}