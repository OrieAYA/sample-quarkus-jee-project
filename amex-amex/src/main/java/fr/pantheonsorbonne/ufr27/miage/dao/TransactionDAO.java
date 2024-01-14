package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Transaction;

public interface TransactionDAO {

    Transaction createNewTransaction(Integer idClient, double montantTransaction);

    Transaction findTransaction(Integer idClient);
}
