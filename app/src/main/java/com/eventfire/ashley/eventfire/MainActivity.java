
package com.eventfire.ashley.eventfire;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import java.security.KeyPair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mDb;
    private DatabaseReference mDbRef,muref;
    private RecyclerView colListRecyclerView;
    private User_model userGet,newReg,userGetName;
    private Query uq;
    private static int flag=0;
    private Bundle userBundle;
    private TextView usernamemain;
    private String users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        usernamemain= (TextView) findViewById(R.id.usernamemain);
        auth = FirebaseAuth.getInstance();

        muref= FirebaseDatabase.getInstance().getReference().child("Users");


        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        colListRecyclerView=(RecyclerView)findViewById(R.id.collegeRecycler);
        colListRecyclerView.setHasFixedSize(true);
        colListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        colListRecyclerView.setItemViewCacheSize(15);
        colListRecyclerView.setDrawingCacheEnabled(true);
        colListRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


//uq.addChildEventListener(regUsers);



        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,collegeFormActivity.class);
                startActivity(intent);

            }
        });



       mDbRef= FirebaseDatabase.getInstance().getReference().child("Colleges");
       // mDbRef.keepSynced(true);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, SignupActivity.class));
                    finish();
                }
                else{
                    flag=1;
                  Bundle getusernamem = getIntent().getExtras();
                   userGetName = (User_model) getusernamem.getSerializable("userObj");
                  PrivateKey privateKey = (PrivateKey) getusernamem.get("privatekey");
                    //Toast.makeText(MainActivity.this, "MainActivity:"+privateKey.toString(), Toast.LENGTH_SHORT).show();

                    try {
                      String decStr = RSAEncryptionJava8.decryptMessage(userGetName.getUsername(), privateKey);
                        users = auth.getCurrentUser().getEmail();
                        uq = muref.orderByChild("email").equalTo(users);

                        //Bundle getusernamem = getIntent().getExtras();
                        //userGetName = (User_model) getusernamem.getSerializable("userObj");
                        usernamemain.setText("Welcome " + decStr);

                    }catch (Exception e){

                    }
//to read values from previously signed up users
                    ChildEventListener regUsers=new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            newReg=dataSnapshot.getValue(User_model.class);

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
        };
    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
       FirebaseRecyclerAdapter <collegeModel,collegeViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<collegeModel, collegeViewHolder>(
                collegeModel.class,
                R.layout.college_list_item_card,
                collegeViewHolder.class,
                mDbRef

        ) {
            @Override
            protected void populateViewHolder(collegeViewHolder viewHolder, final collegeModel model, int position) {
                viewHolder.setAdd(model.getAddress());
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.photoUrl);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(flag==0) {
                          Bundle getuser = getIntent().getExtras();
                          userGet = (User_model) getuser.getSerializable("userObj");
                          userBundle=new Bundle();
                          userBundle.putSerializable("userObj",userGet);

                      }else{
                           userBundle=new Bundle();
                          userBundle.putSerializable("userObj",newReg);
                      }


                        String colName=model.getName();
                        String colAdd= model.getAddress();
                        Bundle collegeBundle=new Bundle();
                        collegeBundle.putString("Name",colName);
                        collegeBundle.putString("Add",colAdd);
                        collegeBundle.putSerializable("collObj",model);
                        Intent intent=new Intent(MainActivity.this,College_profile.class);
                        intent.putExtras(collegeBundle);
                        intent.putExtras(userBundle);
                        startActivity(intent);
                    }
                });

            }


        };
       colListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class collegeViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public collegeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name){
            TextView cardColName=(TextView)mView.findViewById(R.id.cardColName);
            cardColName.setText(name);
        }

        public void setAdd(String address){
            TextView cardColAdd=(TextView)mView.findViewById(R.id.cardColAdd);
            cardColAdd.setText(address);
        }

        public void setImage(Context ctx, String photoUrl){
            ImageView img=(ImageView)mView.findViewById(R.id.cardColImage);
            Picasso.with(ctx).load(photoUrl).fit().into(img);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.menuSignOut:
               auth.signOut();
                //startActivity(new Intent(MainActivity.this, SignupActivity.class));
                //finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

    }



    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
