package com.alwaysbaked.androidarchitecturecomponents;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alwaysbaked.androidarchitecturecomponents.Database.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private static final String TAG = "NoteAdapter";

    private List<Note> noteList;
    private View.OnLongClickListener listener;

    public NoteAdapter(List<Note> noteList, View.OnLongClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Log.d(TAG, "onBindViewHolder: Title: " + noteList.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: Description: " + noteList.get(position).getDescription());

        holder.mTitle.setText(noteList.get(position).getTitle());
        holder.mDescription.setText(noteList.get(position).getDescription());

        holder.itemView.setTag(noteList.get(position));
        holder.itemView.setOnLongClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}
