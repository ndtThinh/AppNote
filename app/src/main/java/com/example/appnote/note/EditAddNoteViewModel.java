package com.example.appnote.note;

import androidx.lifecycle.ViewModel;

import com.example.appnote.data.Repository;
import com.example.appnote.model.NoteClass;

public class EditAddNoteViewModel extends ViewModel {
    private Repository database;
    public void addNewNoteToFirebase(NoteClass newNote){
        database=new Repository();
        database.addDataToFireBase(newNote);
    }
    public void updateNoteToFirebase(NoteClass noteClass){
        database=new Repository();
        database.updateDataToFireBase(noteClass);
    }
}
