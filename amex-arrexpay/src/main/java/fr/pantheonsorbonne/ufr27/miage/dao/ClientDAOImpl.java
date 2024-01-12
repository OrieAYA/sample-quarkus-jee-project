package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class ClientDAOImpl implements ClientDAO{

    @PersistenceContext
    EntityManager em;

    @Override
    public Client CreateNewClient(Integer idClient,Integer num_carte,Float montant_argent) {
        Client Client = new Client(idClient,num_carte,montant_argent);
        em.persist(Client);
        return Client;
    }

    @Override
    public Client FindClient(Integer idClient) {
        return null;
    }
}