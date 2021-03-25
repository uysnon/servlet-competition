package ru.rseu.gorkin.web.viewclasses;

import java.util.Objects;

public class UrlMessage {
    private String content;
    private String url;

    public UrlMessage() {
    }

    public UrlMessage(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMessage)) return false;
        UrlMessage that = (UrlMessage) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, url);
    }

    @Override
    public String toString() {
        return "UrlMessage{" +
                "content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
