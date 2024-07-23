package dsc.controller;

import dsc.model.Evento;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Named("eventoBean")
public class EventoBean implements Serializable {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private Evento evento;
    private List<Evento> eventos;

    @PostConstruct
    public void init() {
        evento = new Evento();
        eventos = new ArrayList<>();
    }

    public String formatarData(Date data) {
        if (data != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(data);
        }
        return "";
    }


    // Métodos de ação
    public void cadastrarEvento() {
        if (evento.getNome() == null || evento.getNome().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nome do evento é obrigatório."));
            return;
        }
        if (evento.getData() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Data do evento é obrigatória."));
            return;
        }
        eventos.add(new Evento(evento.getNome(), evento.getData()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento cadastrado com sucesso!"));
        evento = new Evento(); // Resetar o formulário

        // Redirecionar para atualizar a página
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepararCadastro() {
        this.evento = new Evento(); // Inicializa um novo Evento
    }

    public void removerEvento(Evento evento) {
        eventos.removeIf(e -> e.getNome().equals(evento.getNome()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evento removido com sucesso!"));
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
        evento = new Evento(); // Resetar o formulário se não encontrar o evento
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
