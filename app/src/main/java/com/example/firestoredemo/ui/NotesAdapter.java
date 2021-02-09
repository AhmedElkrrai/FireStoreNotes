package com.example.firestoredemo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> mNotesList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(mNotesList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public void setList(List<Note> notes) {
        this.mNotesList = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_item_title_tv);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    Note note = mNotesList.get(position);
                    note.setPosition(position);
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClicked(note);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}