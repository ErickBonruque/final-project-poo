package javali.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javali.models.Usuario;
import javali.database.connection.SQLiteConnection;
import javali.utils.CryptoUtils;

public class UsuarioDAO {
    private static Connection connection = SQLiteConnection.getConnection();

    public boolean validarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(email));
            stmt.setString(2, CryptoUtils.encrypt(senha));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }

    public boolean criarUsuario(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(nome));
            stmt.setString(2, CryptoUtils.encrypt(email));
            stmt.setString(3, CryptoUtils.encrypt(senha));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean editarUsuario(int id, String nome, String email, String senha) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(nome));
            stmt.setString(2, CryptoUtils.encrypt(email));
            stmt.setString(3, CryptoUtils.encrypt(senha));
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao editar usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario getUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(email));
            stmt.setString(2, CryptoUtils.encrypt(senha));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(
                    CryptoUtils.decrypt(rs.getString("nome")),
                    CryptoUtils.decrypt(rs.getString("email")),
                    CryptoUtils.decrypt(rs.getString("senha"))
                );
                usuario.setId(rs.getInt("id"));
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public boolean emailJaExiste(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CryptoUtils.encrypt(email));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar email: " + e.getMessage());
            return false;
        }
    }
} 