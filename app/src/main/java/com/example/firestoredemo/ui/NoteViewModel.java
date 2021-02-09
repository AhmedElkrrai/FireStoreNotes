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
    private MutableLiveData<List<Note>> noteMutableLiveData = new MutableLiveData<>();
    private NoteRepository repository;
    private Note note;

    public MutableLiveData<List<Note>> getNoteMutableLiveData() {
        return noteMutableLiveData;
    }

    public NoteViewModel() {
        if (noteMutableLiveData.getValue() == null)
            noteMutableLiveData.setValue(new ArrayList<>());
        repository = new NoteRepository();
    }

    public void addNote(Note note) {
        repository.addNote(note);

        List<Note> notes = noteMutableLiveData.getValue();
        notes.add(note);
        noteMutableLiveData.setValue(notes);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);

        List<Note> notes = noteMutableLiveData.getValue();
        notes.set(note.getPosition(), note);
        noteMutableLiveData.setValue(notes);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);

        List<Note> notes = noteMutableLiveData.getValue();
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