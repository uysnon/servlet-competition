package ru.rseu.gorkin.web.validators;

public enum ValidationResultClasses {
    OK, WARNING, ERROR;

    public boolean isNormal() {
        return this == OK;
    }
}
