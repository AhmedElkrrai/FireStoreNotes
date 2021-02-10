package com.example.firestoredemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.firestoredemo.R;
import com.example.firestoredemo.databinding.ActivityNotesBinding;
import com.example.firestoredemo.model.Note;
import com.example.firestoredemo.model.NoteRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class NotesActivity extends AppCompatActivity {
    private static final String USER_ID = "userId";
    private static String ID = "";
    private DocumentSnapshot mLastQueriedDocument;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection(NoteRepository.NOTES);

    private NotesAdapter mAdapter;
    private ActivityNotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(NotesActivity.this, MainActivity.class));
        } else {
            ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        binding.fab.setOnClickListener(view ->
                new Add_Edit_Note_Dialog().show(getSupportFragmentManager(), "Add_Note_Dialog"));

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query;

        if (mLastQueriedDocument != null) {
            query = collectionReference
                    .whereEqualTo(USER_ID, ID)
                    .orderBy(NoteRepository.PRIORITY, Query.Direction.DESCENDING)
                    .startAfter(mLastQueriedDocument);
        } else {
            query = collectionReference
                    .whereEqualTo(USER_ID, ID)
                    .orderBy(NoteRepository.PRIORITY, Query.Direction.DESCENDING);
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() > 0) {
                    mLastQueriedDocument = task.getResult().getDocuments()
                            .get(task.getResult().size() - 1);
                    mAdapter.startListening();
                }
            }
        });

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        mAdapter = new NotesAdapter(options);

        RecyclerView mRecyclerView = binding.notesRecycler;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((documentSnapshot, position) -> {
            NoteViewModel sharedViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
            Note note = documentSnapshot.toObject(Note.class);
            sharedViewModel.setNote(note);
            new Add_Edit_Note_Dialog().show(getSupportFragmentManager(), "Edit_Note_Dialog");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.optionSignOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}