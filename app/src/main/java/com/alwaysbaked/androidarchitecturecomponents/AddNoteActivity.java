package com.alwaysbaked.androidarchitecturecomponents;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alwaysbaked.androidarchitecturecomponents.Database.AddNoteViewModel;
import com.alwaysbaked.androidarchitecturecomponents.Database.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddNoteActivity";

    //widgets
    @BindView(R.id.etAddTitle)
    TextInputEditText mAddTitle;
    @BindView(R.id.etAddDescription)
    TextInputEditText mAddDescription;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private AddNoteViewModel mAddNoteViewModel;
    private CompositeDisposable disposable;
    private Note mAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Log.d(TAG, "onCreate: started.");

        ButterKnife.bind(this);

        disposable = new CompositeDisposable();

        mAddNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel.class);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: note added");
                if (mAddTitle.getText() != null && mAddDescription.getText() != null) {
                    mAddNote = new Note(mAddTitle.getText().toString(), mAddDescription.getText().toString());
                    Completable.fromAction(() -> mAddNoteViewModel.addNote(mAddNote))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable.add(d);
                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(AddNoteActivity.this, "Note Added.", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "Enter a Note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
