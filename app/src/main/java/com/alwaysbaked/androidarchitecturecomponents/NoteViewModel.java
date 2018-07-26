package com.alwaysbaked.androidarchitecturecomponents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alwaysbaked.androidarchitecturecomponents.Database.Note;
import com.alwaysbaked.androidarchitecturecomponents.Database.NoteDatabase;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private static final String TAG = "NoteViewModel";

    private NoteDatabase noteDatabase;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(this.getApplication());
    }

    public LiveData<List<Note>> getNotesList() {
        Log.d(TAG, "getNotesList: getting note list.");
        return noteDatabase.noteDao().getNotes();
    }

    public void deleteNote(Note note) {
        Log.d(TAG, "deleteNote: deleting note");
        noteDatabase.noteDao().deleteNote(note);
    }

}