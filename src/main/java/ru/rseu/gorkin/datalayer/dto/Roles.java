package ru.rseu.gorkin.datalayer.dto;

public enum Roles {
    GUEST(0),
    PARTICIPANT(1),
    EXPERT(2),
    ADMINISTRATOR(3);

    private int id;
    private String title;
    private String description;

    Roles(int id) {
        this.id = id;
    }

    public static Roles getInstance(int id) {
        for (Roles role : Roles.values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
