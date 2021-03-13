package ru.rseu.gorkin.web.commands;

public class UrlUtils {
    private final static String URL_FORMAT = "/?command=%s";

    public static String getCommandUrl(String commandTitle) {
        return String.format(URL_FORMAT, commandTitle);
    }
}
