package com.example.firestoredemo.model;

public class Note {
    private String title;
    private String text;
    private String priority;
    private String noteId;
    private String userId;

    public Note() {

    }

    public Note(String title, String text, String priority) {
        this.title = title;
        this.text = text;
        this.priority = priority;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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