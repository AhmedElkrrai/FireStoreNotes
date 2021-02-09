package com.example.firestoredemo.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.firestoredemo.model.Note;
import com.example.firestoredemo.model.NoteRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private final MutableLiveData<List<Note>> noteMutableLiveData = new MutableLiveData<>();
    private final NoteRepository repository;
    private Note note;
    private final List<Note> notes;

    public MutableLiveData<List<Note>> getNoteMutableLiveData() {
        return noteMutableLiveData;
    }

    public NoteViewModel() {
        repository = new NoteRepository();
        if (noteMutableLiveData.getValue() == null)
            noteMutableLiveData.setValue(repository.getNotes());
        notes = noteMutableLiveData.getValue();
    }

    public void addNote(Note note) {
        repository.addNote(note);

        notes.add(note);
        noteMutableLiveData.setValue(notes);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);

        notes.set(note.getPosition(), note);
        noteMutableLiveData.setValue(notes);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);

        notes.remove(note.getPosition());
        noteMutableLiveData.setValue(notes);
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}