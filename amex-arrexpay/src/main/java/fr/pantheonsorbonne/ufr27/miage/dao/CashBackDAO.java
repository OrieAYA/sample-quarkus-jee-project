package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.CashBack;

public interface CashBackDAO {
    CashBack CreateNewCashback(Integer idClient, Integer idTransaction, float tauxCashback);

    CashBack FindCashback(Integer idCashback);
}