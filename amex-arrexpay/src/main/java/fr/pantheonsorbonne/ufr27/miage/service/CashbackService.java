package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.CashBack;

public interface CashbackService {

    Double getCashbackAmount(int idClient) throws CustomerNotFoundException;

    CashBack cashback(int idClient);

    boolean verifPrice(double price);

    Integer returnTaux(Integer idClient);

    void addMontant(Integer idClient, double montant);
}