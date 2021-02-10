package com.example.firestoredemo.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteRepository {
    private final CollectionReference collectionReference;

    public static final String NOTES = "notes";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String PRIORITY = "priority";

    public NoteRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collectionReference = db.collection(NOTES);
    }

    public void addNote(Note note) {
        DocumentReference documentReference = collectionReference.document();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        note.setUserId(userId);
        note.setNoteId(documentReference.getId());

        documentReference.set(note);
    }

    public void updateNote(Note note) {
        DocumentReference documentReference = collectionReference.document(note.getNoteId());
        documentReference.update(
                TITLE, note.getTitle(),
                TEXT, note.getText(),
                PRIORITY, note.getPriority()
        );
    }

    public void deleteNote(Note note) {
        DocumentReference documentReference = collectionReference.document(note.getNoteId());
        documentReference.delete();
    }
}