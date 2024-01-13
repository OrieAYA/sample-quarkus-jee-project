package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @Column(name = "idClient", nullable = false)
    private int idClient;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num_carte;

    private float montant_argent;

    public Client(Integer idClient,Integer num_carte,Float montant_argent) {
        this.idClient = idClient;
        this.num_carte = num_carte;
        this.montant_argent = montant_argent;
    }

    public Client() {}

    public int getNum_carte(){return num_carte;}

    public void setNum_carte(Integer num_carte){this.num_carte = num_carte;}

    public int getIdClient(){return idClient;}

    public void setIdClient(Integer idClient){this.idClient=idClient;}

    public float getMontantArgent(){return montant_argent;}

    public  void setMontantArgent(Float montant_argent){this.montant_argent=montant_argent;}
}