package fr.pantheonsorbonne.ufr27.miage.dto;

public class Client {

    int idClient;
    String genre;
    int age;
    String profession;

    public Client(int idClient, String genre, int age, String profession) {
        this.idClient = idClient;
        this.genre = genre;
        this.age = age;
        this.profession = profession;
    }

    public Client() {
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
