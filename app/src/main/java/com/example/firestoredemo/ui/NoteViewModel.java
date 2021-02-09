package com.example.firestoredemo.ui;

import androidx.lifecycle.ViewModel;

import com.example.firestoredemo.model.Note;
import com.example.firestoredemo.model.NoteRepository;

public class NoteViewModel extends ViewModel {
    private final NoteRepository repository;
    private Note note;

    public NoteViewModel() {
        repository = new NoteRepository();
    }

    public void addNote(Note note) {
        repository.addNote(note);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}