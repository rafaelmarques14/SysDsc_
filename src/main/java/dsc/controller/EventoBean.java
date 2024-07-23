package dsc.controller;

import dsc.model.Evento;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named("eventoBean")
public class EventoBean implements Serializable {
    private Evento evento;
    private List<Evento> eventos;

    @PostConstruct
    public void init() {
        evento = new Evento();
        eventos = new ArrayList<>();
    }

    // Métodos de ação
    public void cadastrarEvento() {
        eventos.add(new Evento(evento.getNome(), evento.getData()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento cadastrado com sucesso!"));
        evento = new Evento(); // Resetar o formulário
    }

    public void removerEvento() {
        eventos.removeIf(e -> e.getNome().equals(evento.getNome()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento removido com sucesso!"));
        evento = new Evento(); // Resetar o formulário
    }

    public void atualizarEvento() {
        for (Evento e : eventos) {
            if (e.getNome().equals(evento.getNome())) {
                e.setData(evento.getData());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento atualizado com sucesso!"));
                break;
            }
        }
        evento = new Evento(); // Resetar o formulário
    }

    public void prepararAtualizacao(Evento evento) {
        this.evento = evento;
        // Configurar o evento para o diálogo de atualização
    }

    public void prepararRemocao(Evento evento) {
        this.evento = evento;
        // Configurar o evento para o diálogo de remoção
    }

    public void buscarEvento() {
        for (Evento e : eventos) {
            if (e.getNome().equals(evento.getNome())) {
                evento.setData(e.getData());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento encontrado!"));
                return;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento não encontrado!"));
        evento = new Evento(); // Resetar o formulário
    }

    // Getters e Setters
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
