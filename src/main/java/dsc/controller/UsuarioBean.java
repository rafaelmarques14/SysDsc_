package dsc.controller;

import dsc.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named("usuarioBean")
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private String emailLogin;
    private String senhaLogin;
    private boolean loggedIn = false;

    // Cadastro de usuário
    public void cadastrarUsuario() {
        if (usuario != null && usuario.getEmail() != null) {
            usuarios.add(usuario);
            usuario = new Usuario(); // Limpa o formulário após o cadastro
        }
    }

    // Remoção de usuário
    public void removerUsuario() {
        if (emailLogin != null && !emailLogin.isEmpty()) {
            usuarios.removeIf(u -> u.getEmail().equals(emailLogin));
            emailLogin = null; // Limpa o campo após remoção
            loggedIn = false; // Desloga o usuário após a remoção
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário removido com sucesso. Você será deslogado."));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml"); // Redireciona para a página de login
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Atualização de usuário
    public void atualizarUsuario() {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuario.getEmail())) {
                usuarios.set(i, usuario);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso: Usuário atualizado com sucesso.", "Usuário atualizado com sucesso."));
                break;
            }
        }
        usuario = new Usuario(); // Limpa o formulário após a atualização
    }

    // Busca de usuário
    public void buscarUsuario() {
        Usuario foundUser = buscarUsuarioPeloEmail(emailLogin);
        if (foundUser != null) {
            usuario = foundUser; // Atualiza o objeto usuario com o encontrado
        } else {
            usuario = new Usuario(); // Limpa os dados se não encontrar
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado", "Nenhum usuário encontrado com este e-mail."));
        }
    }

    // Preparar a busca
    public void prepararBusca() {
        this.emailLogin = ""; // Limpa o campo de e-mail
        this.usuario = new Usuario(); // Limpa o objeto usuário
        this.usuario.setNome("");
    }

    // Carregar dados do usuário para atualização
    public void carregarUsuarioParaAtualizar() {
        Usuario foundUser = buscarUsuarioPeloEmail(emailLogin);
        if (foundUser != null) {
            usuario = foundUser;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário não encontrado para atualização."));
        }
    }

    // Método auxiliar para buscar usuário pelo email
    private Usuario buscarUsuarioPeloEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // Login de usuário
    public String login() {
        Usuario usuario = buscarUsuarioPeloEmail(emailLogin);
        if (usuario != null && usuario.getSenha().equals(senhaLogin)) {
            loggedIn = true;
            carregarUsuarioParaAtualizar(); // Carrega os dados do usuário logado
            return "home?faces-redirect=true"; // Redireciona para a página inicial após login bem-sucedido
        } else {
            loggedIn = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login falhou:", "Usuário ou senha inválidos."));
            return null; // Retorna null para permanecer na página de login
        }
    }

    // Logout de usuário
    public String logout() {
        loggedIn = false;
        emailLogin = null;
        senhaLogin = null;
        return "login?faces-redirect=true"; // Redireciona para a página de login
    }

    // Getter e Setter para emailLogin e senhaLogin
    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getSenhaLogin() {
        return senhaLogin;
    }

    public void setSenhaLogin(String senhaLogin) {
        this.senhaLogin = senhaLogin;
    }

    // Getter e Setter para loggedIn
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // Getter para a lista de usuários
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Getter e Setter para o atributo usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
