package com.example.appnote.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.appnote.data.Repository;
import com.example.appnote.model.NoteClass;

import java.util.ArrayList;

public class HomeNoteFragmentViewModel extends ViewModel {
    private MutableLiveData<ArrayList<NoteClass>> mutableLiveDataNote;
    private Repository database;

    public LiveData<ArrayList<NoteClass>> getMutableLiveDataNote() {
        mutableLiveDataNote= new MutableLiveData<>();
        database = new Repository();
        loadData();
        return mutableLiveDataNote;
    }

    private void loadData() {
        database = new Repository();
        database.getArrayListMutableLiveData().observeForever(new Observer<ArrayList<NoteClass>>() {
            @Override
            public void onChanged(ArrayList<NoteClass> noteClasses) {
                mutableLiveDataNote.setValue(noteClasses);
            }
        });
        database.getNotesFromFirebase();
    }

}
