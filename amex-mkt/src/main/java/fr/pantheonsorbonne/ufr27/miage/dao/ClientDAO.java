package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Client;
public interface ClientDAO{
    Client createNewClient(Integer idClient, String genre, Integer age, String profession);
}

