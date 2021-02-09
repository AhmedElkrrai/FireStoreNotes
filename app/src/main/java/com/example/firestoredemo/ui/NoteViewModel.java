package com.example.firestoredemo.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firestoredemo.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private MutableLiveData<List<Note>> noteMutableLiveData = new MutableLiveData<>();
    private Note note;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MutableLiveData<List<Note>> getNoteMutableLiveData() {
        return noteMutableLiveData;
    }

    public NoteViewModel() {
        if (noteMutableLiveData.getValue() == null)
            noteMutableLiveData.setValue(new ArrayList<>());
    }


    public void addNote(Note note) {
        List<Note> notes = noteMutableLiveData.getValue();
        notes.add(note);
        noteMutableLiveData.setValue(notes);
    }

    public void updateNote(Note note) {
        List<Note> notes = noteMutableLiveData.getValue();
        notes.set(note.getPosition(), note);
        noteMutableLiveData.setValue(notes);
    }

    public void deleteNote(Note note) {
        List<Note> notes = noteMutableLiveData.getValue();
        notes.remove(note.getPosition());
        noteMutableLiveData.setValue(notes);
    }
}