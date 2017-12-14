package com.eventfire.ashley.eventfire;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class EventListActivity extends AppCompatActivity {

    private FirebaseDatabase mDb;
    private DatabaseReference mDbRef;
    private RecyclerView EventListRecyclerView;
    private collegeModel collBobj;
    private Query eventq;
    private User_model userGet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        EventListRecyclerView=(RecyclerView)findViewById(R.id.eventRecycler);
        EventListRecyclerView.setHasFixedSize(true);
        EventListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventListRecyclerView.setItemViewCacheSize(15);
        EventListRecyclerView.setDrawingCacheEnabled(true);
        EventListRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bundle get=getIntent().getExtras();
        collBobj=(collegeModel) get.getSerializable("collObj");
        Bundle getuser=getIntent().getExtras();
        userGet=(User_model) getuser.getSerializable("userObj");



        mDbRef= FirebaseDatabase.getInstance().getReference().child("Events");
        eventq=mDbRef.orderByChild("collegeName").equalTo(collBobj.getName());

    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<eventModel,EventListActivity.eventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<eventModel,EventListActivity.eventViewHolder>(
                eventModel.class,
                R.layout.event_list_item_card,
                EventListActivity.eventViewHolder.class,
                eventq

        ) {
            @Override
            protected void populateViewHolder(EventListActivity.eventViewHolder viewHolder, final eventModel model, int position) {
                // viewHolder.setAdd(model.getAddress());
                viewHolder.setEventName(model.getEventName());
                viewHolder.setImage(getApplicationContext(),model.eventPhotoUrl);
                viewHolder.setCollegeName(model.getCollegeName());

            viewHolder.eView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle collegeBundle=new Bundle();
                    collegeBundle.putSerializable("EventPassObj",model);
                    Bundle userBundle=new Bundle();
                    userBundle.putSerializable("userObj",userGet);

                    Intent intent= new Intent(EventListActivity.this,CollapsingActivity.class);
                    intent.putExtras(collegeBundle);
                    intent.putExtras(userBundle);

                    startActivity(intent);
                }
            });



            }


        };





    EventListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class eventViewHolder extends RecyclerView.ViewHolder{
        View eView;

        public eventViewHolder(View itemView) {
            super(itemView);
            eView=itemView;
        }

        public void setEventName(String name){
            TextView cardEventName=(TextView)eView.findViewById(R.id.eventCardTitle);
            cardEventName.setText(name);
        }

        public void setCollegeName(String name){
            TextView ColEventName=(TextView)eView.findViewById(R.id.collnamecard);
            ColEventName.setText(name);
        }


        public void setImage(Context ctx, String photoUrl){
            ImageView img=(ImageView)eView.findViewById(R.id.eventCardImage);
            Picasso.with(ctx).load(photoUrl).fit().into(img);

        }
    }

}



