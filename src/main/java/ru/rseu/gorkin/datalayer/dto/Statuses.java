package ru.rseu.gorkin.datalayer.dto;

public enum Statuses {
    ACTIVE(1),
    BLOCKED(2),
    DELETED(3);

    private int id;
    private String title;
    private String description;

    Statuses(int id) {
        this.id = id;
    }

    public static Statuses getInstance(int id) {
        for (Statuses status : Statuses.values()) {
            if (status.id == id) {
                return status;
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
