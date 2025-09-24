package org.example.model;

public class Mensalista {

    private int matricula;
    private String nome;

    public Mensalista() {}

    public Mensalista(String nome) {
        this.nome = nome;
    }

    public Mensalista(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }


    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
