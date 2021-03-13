package ru.rseu.gorkin.resources.utils;

import java.util.ResourceBundle;

public enum ConfigurationManagers {
    WEB_MANAGER(ResourceBundle.getBundle("config")),
    SQL_MANAGER(ResourceBundle.getBundle("sql"));

    private ResourceBundle resourceBundle;

    private ConfigurationManagers(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }

    public String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
