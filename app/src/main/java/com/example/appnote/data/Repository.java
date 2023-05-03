package com.example.appnote.data;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.appnote.model.NoteClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private MutableLiveData<ArrayList<NoteClass>> arrayListMutableLiveData = new MutableLiveData<>();
    FirebaseFirestore fireStore;

    public MutableLiveData<ArrayList<NoteClass>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    public Repository() {
        fireStore = FirebaseFirestore.getInstance();
    }

    public void getNotesFromFirebase() {
        fireStore.collection("notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<NoteClass> listNote = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                String time = snapshot.getString("time");
                                String tittle = snapshot.getString("tittle");
                                String content = snapshot.getString("content");
                                Log.d("Object:", tittle);
                                listNote.add(new NoteClass(tittle, content, time));
                            }
                            arrayListMutableLiveData.setValue(listNote);
                        } else {
                            arrayListMutableLiveData.setValue(new ArrayList<>());
                            Log.d("Repository", "onComplete: error getting data ");
                        }
                    }
                });
    }

    public void deleteDataFromFireBase(String mTittle) {
        Query query = fireStore.collection("notes")
                .whereEqualTo("tittle", mTittle);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                documentSnapshot.getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "onComplete: delete success");
                                        }
                                        else {
                                            Log.e(TAG, "onComplete:ERRORR ",task.getException() );
                                        }
                                    }
                                });
                            }
                        }
                        else {
                            Log.e(TAG, "onComplete: erorr getting data",task.getException() );
                        }
                    }
                });
    }

    public void addDataToFireBase(NoteClass mNoteClass) {
        String tittle = mNoteClass.getTittle();

        Map<String, Object> noteData = new HashMap<>();
        noteData.put("tittle", mNoteClass.getTittle());
        noteData.put("content", mNoteClass.getContent());
        noteData.put("time", mNoteClass.getTime());

        // Tạo một document mới với tên là "note_{time}"
        fireStore.collection("notes")
                .document("note_" + tittle)
                .set(noteData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: OKK");
                    }
                });
    }
    public void updateDataToFireBase(NoteClass mNoteClass){
        Map<String,Object> updateNoteData=new HashMap<>();
        updateNoteData.put("tittle",mNoteClass.getTittle());
        updateNoteData.put("content",mNoteClass.getContent());
        updateNoteData.put("time",mNoteClass.getTime());
        Query query = fireStore.collection("notes")
                .whereEqualTo("time", mNoteClass.getTime());
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            documentSnapshot.getReference().update(updateNoteData);
                        }
                        Log.d(TAG, "onSuccess: update success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "onFailure: failed",e );
                    }
                });
    }

}
