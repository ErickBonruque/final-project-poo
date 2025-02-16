package javali.database;

import java.util.List;
import javali.database.dao.AppDAO;
import javali.database.dao.UsuarioDAO;
import javali.models.AppModel;
import javali.models.Usuario;
import javali.utils.SessionManager;

public class DatabaseManager {
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final AppDAO appDAO = new AppDAO();

    // Métodos relacionados a Usuário
    public static boolean validarLogin(String email, String senha) {
        return usuarioDAO.validarLogin(email, senha);
    }

    public static boolean criarUsuario(String nome, String email, String senha) {
        return usuarioDAO.criarUsuario(nome, email, senha);
    }

    public static boolean editarUsuario(int id, String nome, String email, String senha) {
        return usuarioDAO.editarUsuario(id, nome, email, senha);
    }

    public static Usuario getUsuario(String email, String senha) {
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if (usuario != null) {
            SessionManager.getInstance().setUsuarioLogado(usuario);
            usuario.setApps(getAppsDoUsuario());
        }
        return usuario;
    }

    public static boolean emailJaExiste(String email) {
        return usuarioDAO.emailJaExiste(email);
    }

    // Métodos relacionados a Apps
    public static boolean adicionarApp(AppModel app) {
        Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
        return appDAO.adicionarApp(app, usuario.getId());
    }

    public static boolean verificarApp(String nome, String email, String senha, String site) {
        return appDAO.verificarApp(nome, email, senha, site);
    }

    public static boolean removerApp(int appId) {
        Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
        return appDAO.removerApp(appId, usuario.getId());
    }

    public static boolean editarApp(AppModel app) {
        return appDAO.editarApp(app);
    }

    public static List<AppModel> getAppsDoUsuario() {
        Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
        return appDAO.getAppsDoUsuario(usuario.getId());
    }
} 