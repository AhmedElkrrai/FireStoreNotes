package com.example.firestoredemo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.firestoredemo.R;
import com.example.firestoredemo.databinding.AddNoteDialogBinding;
import com.example.firestoredemo.model.Note;

import java.util.Objects;

public class Add_Edit_Note_Dialog extends DialogFragment {
    private static final String TAG = "AddNoteDialog";
    private NoteViewModel sharedViewModel;
    private AddNoteDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_dialog, container, false);

        View view = binding.getRoot();

        binding.addNoteBackButton.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());
        binding.priorityPlus.setOnClickListener(v -> {
            int priority = Integer.parseInt(binding.priorityText.getText().toString());
            if (priority == 9)
                priority = 1;
            else ++priority;
            binding.priorityText.setText(String.valueOf(priority));
        });
        binding.priorityMinus.setOnClickListener(v -> {
            int priority = Integer.parseInt(binding.priorityText.getText().toString());
            if (priority == 1)
                priority = 9;
            else --priority;
            binding.priorityText.setText(String.valueOf(priority));
        });

        if (getTag().equals("Add_Note_Dialog")) {
            binding.cancelNoteBT.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());
            binding.addNoteBT.setOnClickListener(v -> {
                String title = Objects.requireNonNull(binding.titleEditText.getEditText()).getText().toString();
                String text = Objects.requireNonNull(binding.subjectEditText.getEditText()).getText().toString();
                String priority = binding.priorityText.getText().toString();

                if (title.isEmpty() || text.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                sharedViewModel.addNote(new Note(title, text, priority));

                Toast.makeText(getActivity(), "Note Saved", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getDialog()).dismiss();
            });
        }
        if (getTag().equals("Edit_Note_Dialog")) {
            //get note
            sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NoteViewModel.class);
            Note note = sharedViewModel.getNote();

            //display note
            binding.titleEditText.getEditText().setText(note.getTitle());
            binding.subjectEditText.getEditText().setText(note.getText());
            binding.priorityText.setText(note.getPriority());

            //update note
            binding.addNoteBT.setOnClickListener(v -> {
                String title = Objects.requireNonNull(binding.titleEditText.getEditText()).getText().toString();
                String text = Objects.requireNonNull(binding.subjectEditText.getEditText()).getText().toString();
                String priority = binding.priorityText.getText().toString();

                if (title.isEmpty() || text.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                note.setTitle(title);
                note.setText(text);
                note.setPriority(priority);

                sharedViewModel.updateNote(note);

                Toast.makeText(getActivity(), "Note Updated", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getDialog()).dismiss();
            });

            //delete note
            binding.cancelNoteBT.setText("مسح");
            binding.cancelNoteBT.setOnClickListener(view1 -> {
                sharedViewModel.deleteNote(note);
                Toast.makeText(getActivity(), "Note Deleted", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            });

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.edit_round_2);
        int width = getResources().getDimensionPixelSize(R.dimen._329sdp);
        int height = getResources().getDimensionPixelSize(R.dimen._495sdp);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NoteViewModel.class);
    }
}