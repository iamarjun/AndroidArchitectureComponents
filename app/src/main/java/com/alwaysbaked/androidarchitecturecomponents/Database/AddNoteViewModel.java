package com.alwaysbaked.androidarchitecturecomponents.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

public class AddNoteViewModel extends AndroidViewModel {
    private static final String TAG = "AddNoteViewModel";

    private NoteDatabase mNoteDB;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        mNoteDB = NoteDatabase.getDatabase(this.getApplication());
    }

    public void addNote(Note note) {
        Log.d(TAG, "addNote: adding a new note.");
        mNoteDB.noteDao().addNote(note);

    }
}
