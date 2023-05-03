package com.example.appnote.model;

import java.io.Serializable;

public class NoteClass implements Serializable {
    private String tittle,content;
    private String time;

    public NoteClass(String tittle, String content, String time) {
        this.tittle = tittle;
        this.content = content;
        this.time = time;
    }

    public NoteClass() {
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
