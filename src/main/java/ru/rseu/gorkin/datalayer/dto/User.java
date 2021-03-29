package ru.rseu.gorkin.datalayer.dto;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private Roles role;
    private Statuses status;
    private int sessionsCount;

    public User() {
    }

    public User(int id, String login, String password, String name, Roles role, Statuses status, int sessionsCount) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = status;
        this.sessionsCount = sessionsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }


    public int getSessionsCount() {
        return sessionsCount;
    }

    public void setSessionsCount(int sessionsCount) {
        this.sessionsCount = sessionsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                sessionsCount == user.sessionsCount &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                role == user.role &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, role, status, sessionsCount);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", sessionsCount=" + sessionsCount +
                '}';
    }
}
