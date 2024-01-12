package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Client;

public interface ClientDAO{
    Client CreateNewClient(Integer idClient,Integer num_carte,Float montant_argent);

    Client FindClient(Integer idClient);
}