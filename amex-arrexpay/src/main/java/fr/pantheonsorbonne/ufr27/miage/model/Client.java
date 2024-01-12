package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @Column(name = "idClient", nullable = false)
    private Integer idClient;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num_carte;

    private Float montant_argent;

    public Client(Integer idClient,Integer num_carte,Float montant_argent) {
        this.idClient = idClient;
        this.num_carte = num_carte;
        this.montant_argent = montant_argent;
    }

    public Client() {}

    public Integer getNum_carte(){return num_carte;}

    public void setNum_carte(Integer num_carte){this.num_carte = num_carte;}

    public Integer getIdClient(){return idClient;}

    public void setIdClient(Integer idClient){this.idClient=idClient;}

    public Float getMontantArgent(){return montant_argent;}

    public  void setMontantArgent(Float montant_argent){this.montant_argent=montant_argent;}
}