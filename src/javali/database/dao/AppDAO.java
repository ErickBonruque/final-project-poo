package javali.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javali.models.AppModel;
import javali.database.connection.SQLiteConnection;
import javali.utils.CryptoUtils;

public class AppDAO {
    private static Connection connection = SQLiteConnection.getConnection();

    public boolean adicionarApp(AppModel app, int usuarioId) {
        String sql = "INSERT INTO apps (nome, email, senha, website, notas, data_criacao, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(app.getName()));
            stmt.setString(2, CryptoUtils.encrypt(app.getEmail()));
            stmt.setString(3, CryptoUtils.encrypt(app.getPassword()));
            stmt.setString(4, CryptoUtils.encrypt(app.getWebsite()));
            stmt.setString(5, CryptoUtils.encrypt(app.getNotes()));
            stmt.setDate(6, app.getDate());
            stmt.setInt(7, usuarioId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar app: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarApp(String nome, String email, String senha, String site) {
        String sql = "SELECT * FROM apps WHERE nome = ? AND email = ? AND senha = ? AND website = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(nome));
            stmt.setString(2, CryptoUtils.encrypt(email));
            stmt.setString(3, CryptoUtils.encrypt(senha));
            stmt.setString(4, CryptoUtils.encrypt(site));
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar app: " + e.getMessage());
            return false;
        }
    }

    public boolean removerApp(int appId, int usuarioId) {
        String sql = "DELETE FROM apps WHERE id = ? AND usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appId);
            stmt.setInt(2, usuarioId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover app: " + e.getMessage());
            return false;
        }
    }

    public boolean editarApp(AppModel app) {
        String sql = "UPDATE apps SET nome = ?, email = ?, senha = ?, website = ?, notas = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(app.getName()));
            stmt.setString(2, CryptoUtils.encrypt(app.getEmail()));
            stmt.setString(3, CryptoUtils.encrypt(app.getPassword()));
            stmt.setString(4, CryptoUtils.encrypt(app.getWebsite()));
            stmt.setString(5, CryptoUtils.encrypt(app.getNotes()));
            stmt.setInt(6, app.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao editar app: " + e.getMessage());
            return false;
        }
    }

    public List<AppModel> getAppsDoUsuario(int usuarioId) {
        List<AppModel> apps = new ArrayList<>();
        String sql = "SELECT * FROM apps WHERE usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AppModel app = new AppModel(
                    CryptoUtils.decrypt(rs.getString("nome")),
                    CryptoUtils.decrypt(rs.getString("email")),
                    CryptoUtils.decrypt(rs.getString("senha")),
                    CryptoUtils.decrypt(rs.getString("website")),
                    CryptoUtils.decrypt(rs.getString("notas")),
                    rs.getDate("data_criacao")
                );
                app.setId(rs.getInt("id"));
                app.setUserId(usuarioId);
                apps.add(app);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar apps do usuário: " + e.getMessage());
        }
        return apps;
    }
} 