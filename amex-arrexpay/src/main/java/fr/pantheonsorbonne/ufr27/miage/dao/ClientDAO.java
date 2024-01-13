package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Client;

public interface ClientDAO{
    Client CreateNewClient(int idClient,int num_carte,float montant_argent);

    Client FindClient(int idClient);
}