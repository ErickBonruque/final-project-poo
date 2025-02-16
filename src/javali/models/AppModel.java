package javali.models;

import java.sql.Date;

public class AppModel {
    private String name;
    private String email;
    private String password;
    private String website;
    private String notes;
    private Date date;
    private int id;
    private int userId;

    public AppModel(String name, String email, String password, String website, String notes, Date date) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.website = website;
        this.notes = notes;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }   

    public void editApp(String name, String email, String password, String website, String notes, Date date) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.website = website;
        this.notes = notes;
        this.date = date;
    }

    @Override
    public String toString() {
        return "AppModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
