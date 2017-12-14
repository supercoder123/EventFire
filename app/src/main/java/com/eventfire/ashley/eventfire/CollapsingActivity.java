package com.eventfire.ashley.eventfire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CollapsingActivity extends Activity {

    private eventModel eventBobj;
    private TextView eventName,desc,date,duration,cperson,phoneNum;
    private ImageView evenImage;
    private ImageButton arroww;
    private User_model userGet;
    private Button reg,vreg;
    private DatabaseReference sref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);


        Bundle getuser=getIntent().getExtras();
        userGet=(User_model) getuser.getSerializable("userObj");

        Bundle get=getIntent().getExtras();
        eventBobj=(eventModel) get.getSerializable("EventPassObj");

                eventName=(TextView) findViewById(R.id.textView2);
                desc=(TextView) findViewById(R.id.textView5);
                date=(TextView) findViewById(R.id.textView6);
                duration=(TextView) findViewById(R.id.textViewduration);
                cperson=(TextView) findViewById(R.id.textView8);
                phoneNum=(TextView) findViewById(R.id.textphone);
                evenImage= (ImageView) findViewById(R.id.bgheader);

        reg= (Button) findViewById(R.id.register);
        vreg= (Button) findViewById(R.id.viewreg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               userGet.setEvents(eventBobj.getEventName().toString());
                sref= FirebaseDatabase.getInstance().getReference("Participating Users");
                sref.push().setValue(userGet);

                Toast.makeText(CollapsingActivity.this,"Thank You for registering", Toast.LENGTH_SHORT).show();

                 reg.setEnabled(false);

            }
        });

        vreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CollapsingActivity.this,Participants_List_Activity.class);
                Bundle regEventsBundle=new Bundle();
                regEventsBundle.putSerializable("userObj",userGet);

                Bundle eventsDetailsBundle=new Bundle();
                eventsDetailsBundle.putSerializable("EventPassObj",eventBobj);

                intent.putExtras(regEventsBundle);
                intent.putExtras(eventsDetailsBundle);


                startActivity(intent);
            }
        });

        arroww= (ImageButton) findViewById(R.id.arrow);
        arroww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        eventName.setText(eventBobj.getEventName());
        desc.setText(eventBobj.getDescription());
        date.setText(eventBobj.getDate());
        duration.setText(eventBobj.getDuration());
        cperson.setText(eventBobj.getContactPerson());
        phoneNum.setText(eventBobj.getPhoneNumber());
        Picasso.with(getApplicationContext()).load(eventBobj.getEventPhotoUrl()).fit().into(evenImage);






        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);//object of ctoolbar
        collapsingToolbarLayout.setTitle(" ");
        Context context = this;
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorAccent));//collapsing tool bar color change
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);//



    }
}
