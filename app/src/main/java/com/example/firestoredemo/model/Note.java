package com.example.firestoredemo.model;

public class Note {
    private String title;
    private String text;
    private String priority;
    private int position;

    public Note(String title, String text, String priority) {
        this.title = title;
        this.text = text;
        this.priority = priority;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}