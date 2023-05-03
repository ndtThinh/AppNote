package com.example.appnote.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnote.R;
import com.example.appnote.data.DataNotePlans;
import com.example.appnote.model.NoteClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DetailNoteFragment extends Fragment {

    public static final String TAG = DetailNoteFragment.class.getName();
    private ImageButton btnBack, btnSave;
    private TextView tvTime;
    private EditText mTittle, mContent;
    private NoteClass myNote;
    private String state = "";
    private EditAddNoteViewModel editAddNoteViewModel;

    public DetailNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_detail_note, container, false);
        btnBack = views.findViewById(R.id.btn_back_detail_note);
        tvTime = views.findViewById(R.id.textView_time);
        btnSave = views.findViewById(R.id.btn_save_edit);
        mTittle = views.findViewById(R.id.edit_tittle);
        mContent = views.findViewById(R.id.edit_content);
        editAddNoteViewModel= new ViewModelProvider(this).get(EditAddNoteViewModel.class);

        setData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == "add new") {
                    saveNewNote();
                }
                if (state == "edit") {
                    saveEditNote();
                }

            }
        });
        return views;
    }

    private void saveNewNote() {
        String tittle = mTittle.getText().toString();
        String content = mContent.getText().toString();
        String time = tvTime.getText().toString();
        NoteClass note = new NoteClass(tittle, content, time);
        editAddNoteViewModel.addNewNoteToFirebase(note);
        Toast.makeText(getActivity(), "Add success", Toast.LENGTH_SHORT).show();
        Fragment HomeFragment = new HomeNoteFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, HomeFragment);
        fragmentTransaction.commit();
       //getFragmentManager().popBackStack();
    }

    private void saveEditNote() {
        String tittle = mTittle.getText().toString();
        String content = mContent.getText().toString();
        String time = tvTime.getText().toString();
        NoteClass note = new NoteClass(tittle, content, time);
        editAddNoteViewModel.updateNoteToFirebase(note);
        //getFragmentManager().popBackStack();
        Fragment HomeFragment = new HomeNoteFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, HomeFragment);
        fragmentTransaction.commit();

        Toast.makeText(getActivity(), "Edit success", Toast.LENGTH_SHORT).show();
    }

    private void setEmptyView() {
        mTittle.setText("");
        mContent.setText("");
        tvTime.setText("");
    }

    private void setData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            state = bundle.getString("state");
            if (state == "add new") {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String currentTime = dateFormat.format(new Date());
                tvTime.setText(currentTime);
            } else if (state == "edit") {
                myNote = (NoteClass) bundle.get("note");
                tvTime.setText(myNote.getTime());
                mTittle.setText(myNote.getTittle());
                mContent.setText(myNote.getContent());
            }
        }
    }


}