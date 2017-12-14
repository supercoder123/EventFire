package com.eventfire.ashley.eventfire;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Participants_List_Activity extends AppCompatActivity {

    private DatabaseReference muref;
    private Query uq;
    private User_model userGet,newReg;
    private eventModel eventBobj;
    private TextView users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants__list_);

        Bundle getuser=getIntent().getExtras();
        userGet=(User_model) getuser.getSerializable("userObj");

        Bundle get=getIntent().getExtras();
        eventBobj=(eventModel) get.getSerializable("EventPassObj");

        users= (TextView) findViewById(R.id.textViewreg);
        users.setText("\n");


        muref= FirebaseDatabase.getInstance().getReference().child("Participating Users");
        uq=muref.orderByChild("events").equalTo(eventBobj.getEventName());

        ChildEventListener regUsers=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             //   for(DataSnapshot retSnapshot:dataSnapshot.getChildren()){
                    newReg=dataSnapshot.getValue(User_model.class);
                    users.append("Name: "+newReg.getUsername()+"\n"+"Class: "+newReg.getClassDiv()+"\n"+"College: "+newReg.getCollege());
                    users.append("\n_____________________________________");

                //users.setPaintFlags(users.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                //    }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        uq.addChildEventListener(regUsers);

    }
}
