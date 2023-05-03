package com.example.appnote.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.R;
import com.example.appnote.data.DataNotePlans;
import com.example.appnote.data.Repository;
import com.example.appnote.model.NoteClass;
import com.example.appnote.note.DetailNoteFragment;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<ViewHolderNote> {

    private ArrayList<NoteClass> mNoteList;
    private Repository database;
    private OnEditNoteListener onEditNoteListener;

    public interface OnEditNoteListener{
        void onEditNoteListener(NoteClass noteClass);
        void onDeleteNoteListener(NoteClass noteClass);
    }

    public NoteAdapter(ArrayList<NoteClass> mNoteList,OnEditNoteListener onEditNoteListener1) {
        this.mNoteList = mNoteList;
        this.onEditNoteListener=onEditNoteListener1;
    }

    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderNote(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int position) {
        NoteClass note = mNoteList.get(position);
        holder.tittle.setText(note.getTittle());
        holder.time.setText(note.getTime());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditNoteListener.onDeleteNoteListener(note);
            }
        });
        holder.tittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditNoteListener.onEditNoteListener(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}

class ViewHolderNote extends RecyclerView.ViewHolder {
    TextView tittle, time;
    ImageButton btnDelete;

    public ViewHolderNote(@NonNull View itemView) {
        super(itemView);
        tittle = itemView.findViewById(R.id.item_note_tittle);
        time = itemView.findViewById(R.id.item_note_time);
        btnDelete = itemView.findViewById(R.id.item_delete);
    }
}
