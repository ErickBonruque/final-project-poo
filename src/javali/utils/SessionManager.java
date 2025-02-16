package javali.utils;

import javali.models.Usuario;

public class SessionManager {
    private static SessionManager instance;
    private Usuario usuarioLogado;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void clearSession() {
        usuarioLogado = null;
    }
} 