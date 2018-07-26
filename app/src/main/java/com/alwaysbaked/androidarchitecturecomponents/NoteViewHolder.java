package com.alwaysbaked.androidarchitecturecomponents;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NoteViewHolder";

    @BindView(R.id.tvTitle)
    TextView mTitle;
    @BindView(R.id.tvDescription)
    TextView mDescription;

    public NoteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
