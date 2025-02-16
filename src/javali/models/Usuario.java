package javali.models;

import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private List<AppModel> apps;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AppModel> getApps() {
        return apps;
    }

    public void setApps(List<AppModel> apps) {
        this.apps = apps;
    }

    public void addApp(AppModel app) {
        this.apps.add(app);
    }


    public void removeApp(int id) {
        for (AppModel app : apps) {
            if (app.getId() == id) {
                apps.remove(app);
                break;
            }
        }
    }

    public AppModel getApp(int id) {
        for (AppModel app : apps) {
            if (app.getId() == id) {
                return app;
            }
        }
        return null;
    }

    
}
