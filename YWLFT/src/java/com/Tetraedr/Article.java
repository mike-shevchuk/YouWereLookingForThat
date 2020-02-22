package com.Tetraedr;

import lombok.Data;

@Data
public class Article {
    private long id;
    private String content;

    public long getId() {  return id; }
    public void setId(long id) {        this.id = id;    }
    public String getContent() {        return content;    }
    public void setContent(String content) {        this.content = content;}
    public Article() {  }
    public Article(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        //System.out.println("Інформація яку перехопив java id = " + id +  " Повідомлення "+ content);
        return "Article{" +
                "NEW id = " + id +
                ", content = '" + content + '\'' +
                '}';
    }
}