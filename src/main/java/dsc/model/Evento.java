package dsc.model;

import java.util.Date;

public class Evento {
    private String nome;
    private Date data;

    // Construtor padrão
    public Evento() {}

    // Construtor com parâmetros
    public Evento(String nome, Date data) {
        this.nome = nome;
        this.data = data;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
