package dsc.controller;

import dsc.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named("usuarioBean")
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuario = new Usuario(); // Adicione este atributo

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
    public void atualizarUsuario(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuario.getEmail())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    // Busca de usuário
    public Usuario buscarUsuario(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    // Login de usuário
    public boolean login(String email, String senha) {
        Usuario usuario = buscarUsuario(email);
        return usuario != null && usuario.getSenha().equals(senha);
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