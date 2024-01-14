package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Entity
public class CashBack {


    private Integer idTransaction;

    @Id
    private Integer idClient;

    @Column(name = "tauxCashback", nullable = false)
    private float tauxCashback;

    private double montantCashback;

    public CashBack(Integer idClient){
        this.idClient=idClient;
        this.idTransaction=0;
        this.tauxCashback=15;
        this.montantCashback=0;
    }

    public CashBack() {}

    public Integer getIdClient() {return idClient;}

    public void setIdClient(Integer idClient) {this.idClient = idClient;}

    public float getTauxCashback() {return tauxCashback;}

    public void setTauxCashback(float tauxCashback) {this.tauxCashback = tauxCashback;}

    public Integer getIdTransaction() {return idTransaction;}

    public void setIdTransaction(Integer idTransaction) {this.idTransaction = idTransaction;}

    public double getMontantCashback() {return montantCashback;}

    public void setMontantCashback(double montantCashback) {this.montantCashback = montantCashback;}
}
