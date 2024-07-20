package dsc.controller;

import dsc.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

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
    public void removerUsuario(String email) {
        usuarios.removeIf(u -> u.getEmail().equals(email));
    }

    // Atualização de usuário
    public void atualizarUsuario() {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuario.getEmail())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        usuario = new Usuario(); // Limpa o formulário após a atualização
    }

    // Busca de usuário
    public Usuario buscarUsuario(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    // Login de usuário
    public String login() {
        Usuario usuario = buscarUsuario(emailLogin);
        if (usuario != null && usuario.getSenha().equals(senhaLogin)) {
            loggedIn = true;
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
