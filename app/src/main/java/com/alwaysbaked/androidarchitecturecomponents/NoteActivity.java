package com.alwaysbaked.androidarchitecturecomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alwaysbaked.androidarchitecturecomponents.Database.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity implements View.OnLongClickListener {
    private static final String TAG = "NoteActivity";
    private static final int COLUMN_NUM = 2;

    @BindView(R.id.rvNotesList)
    RecyclerView mNotesList;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private NoteViewModel mNoteViewModel;
    private NoteAdapter adapter;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Log.d(TAG, "onCreate: started.");

        ButterKnife.bind(this);

        disposable = new CompositeDisposable();

        mFab.setOnClickListener(v -> startActivity(new Intent(NoteActivity.this, AddNoteActivity.class)));

        getNotes();

    }

    private void getNotes() {
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getNotesList().observe(NoteActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> noteList) {
                Log.d(TAG, "onChanged: getting the notes");
                initRecyclerView(noteList);
            }
        });

    }

    private void initRecyclerView(List<Note> noteList) {
        Log.d(TAG, "initRecyclerView: initialising recycler view.");
        adapter = new NoteAdapter(noteList, this);
        mNotesList.setAdapter(adapter);
        mNotesList.setLayoutManager(new StaggeredGridLayoutManager(COLUMN_NUM, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public boolean onLongClick(View v) {
        Completable.fromAction(() -> mNoteViewModel.deleteNote((Note) v.getTag()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: deleted note");
                        Toast.makeText(NoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
        return true;
    }
}
