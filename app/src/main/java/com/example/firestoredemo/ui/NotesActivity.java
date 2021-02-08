package com.example.firestoredemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.firestoredemo.R;
import com.example.firestoredemo.databinding.ActivityNotesBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_notes);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(view ->
                new Add_Edit_Note_Dialog().show(getSupportFragmentManager(), "Add_Note_Dialog"));

        RecyclerView mRecyclerView = binding.notesRecycler;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        NotesAdapter mAdapter = new NotesAdapter();

        NoteViewModel sharedViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        sharedViewModel.getNoteMutableLiveData().observe(this, mAdapter::setList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(note -> {
            sharedViewModel.setNote(note);
            new Add_Edit_Note_Dialog().show(getSupportFragmentManager(), "Edit_Note_Dialog");
        });
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
}