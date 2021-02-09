package com.example.firestoredemo.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {
    private final CollectionReference collectionReference;
    private DocumentSnapshot mLastQueriedDocument;

    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String PRIORITY = "priority";
    private static final String USER_ID = "userId";

    private static final String TAG = "NoteRepository";

    public NoteRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collectionReference = db.collection(NOTES);
    }

    public List<Note> getNotes() {
        List<Note> mNotes = new ArrayList<>();
        collectionReference.get().addOnSuccessListener(snapshots -> mNotes.addAll(snapshots.toObjects(Note.class)));
        return mNotes;
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