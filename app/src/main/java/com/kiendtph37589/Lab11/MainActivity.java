package com.kiendtph37589.Lab11;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView tvKQ;
    FirebaseFirestore database;
    Context context=this;
    ToDo toDo=null;
    String strKQ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ = findViewById(R.id.tvKQ);
        database = FirebaseFirestore.getInstance();//khoi tao

//        insert();
//        update();
//        select();
        delete();
    }
    void insert(){
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id,"title 11","content 11");
        database.collection("TODO")
                .document(id)
                .set(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "insert Thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void update(){
        String id = "771d9c53-8a48-4caa-b2d1-9a75de5f64b1";
        toDo = new ToDo(id,"title 11 update","content 11 update");
        database.collection("TODO")
                .document(id)
                .update(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete(){
        String id = "d796d164-6cb3-4467-a9d9-380716c2e451";
        database.collection("TODO")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")
                .get()//lay ve du lieu
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            strKQ="";
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                ToDo t = doc.toObject(ToDo.class);//chuyen du lieu doc duocsang dang TODO
                                list.add(t);
                                strKQ+= "id: "+t.getId()+"\n";
                                strKQ+= "title: "+t.getTitle()+"\n";
                                strKQ+= "content: "+t.getContent()+"\n";
                            }
                            tvKQ.setText(strKQ);
                        }else {
                            Toast.makeText(context, "Select thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return list;
    }
}