package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Cashback;

public interface CashbackDAO {
    Cashback createNewCashback(Integer idClient, Integer idTransaction, float tauxCashback);
}
