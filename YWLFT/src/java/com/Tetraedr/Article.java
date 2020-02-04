package com.Tetraedr;

public class Article {
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        System.out.println("Інформація яку перехопив java id = " + id +
                " Повідомлення "+ content);
        return "Article{" +
                "NEW id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}