package com.example.appnote.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.adapter.NoteAdapter;
import com.example.appnote.data.DataNotePlans;
import com.example.appnote.data.Repository;
import com.example.appnote.model.NoteClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeNoteFragment extends Fragment {


    private ImageButton btnAdd, btnSearch;
    private RecyclerView mRecyclerView;
    private NoteAdapter noteAdapter;
    private NoteClass mNote;
    private ArrayList<NoteClass> mNoteList;
    private HomeNoteFragmentViewModel homeNoteFragmentViewModel;

    public HomeNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_home_note, container, false);
        InitHomeNoteFragment(views);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailNoteFragment addNewNoteFragment = new DetailNoteFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                String code = "add new";
                Bundle bundle = new Bundle();
                bundle.putString("state", code);
                addNewNoteFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.home_fragment, addNewNoteFragment);
                fragmentTransaction.addToBackStack(addNewNoteFragment.TAG);
                fragmentTransaction.commit();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment, new SearchFragmentNote());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return views;
    }

    public void InitHomeNoteFragment(View views){
        btnAdd = views.findViewById(R.id.btn_add_new_note);
        btnSearch = views.findViewById(R.id.btn_search_note);
        mRecyclerView = views.findViewById(R.id.recycler_view_note);
        homeNoteFragmentViewModel= new ViewModelProvider(this).get(HomeNoteFragmentViewModel.class);
        mNoteList= new ArrayList<>();
        homeNoteFragmentViewModel.getMutableLiveDataNote().observe(getViewLifecycleOwner(),new Observer<ArrayList<NoteClass>>() {
            @Override
            public void onChanged(ArrayList<NoteClass> noteClasses) {
                mNoteList=noteClasses;
                onSetRecyclerView();
            }
        });
    }

    public void onSetRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noteAdapter = new NoteAdapter(mNoteList, new NoteAdapter.OnEditNoteListener() {
            @Override
            public void onEditNoteListener(NoteClass noteClass) {
                Fragment fragment = new DetailNoteFragment();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("state", "edit");
                bundle.putSerializable("note", noteClass);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.home_fragment, fragment);
                fragmentTransaction.addToBackStack("edit");
                fragmentTransaction.commit();

            }

            @Override
            public void onDeleteNoteListener(NoteClass noteClass) {
                Repository database = new Repository();
                database.deleteDataFromFireBase(noteClass.getTittle());
                //can cap nhap giao dien
            }
        });
        mRecyclerView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "Resume cũ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(),"onpause homenotefargment",Toast.LENGTH_LONG).show();
    }
}