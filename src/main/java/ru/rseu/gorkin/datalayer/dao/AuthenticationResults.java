package ru.rseu.gorkin.datalayer.dao;

public enum AuthenticationResults {
    NO_SUCH_USER_FOUND("Не найдено пользователя с таким логином"),
    WRONG_PASSWORD("Неправильный пароль"),
    SUCCESS("Успешная авторизация"),
    USER_BLOCKED("Учетная запись заблокирована"),
    USER_DELETED("Учетная запись удалена");

    private String description;

    AuthenticationResults(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
