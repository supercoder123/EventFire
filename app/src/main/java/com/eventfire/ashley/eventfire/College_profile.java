package com.eventfire.ashley.eventfire;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class College_profile extends AppCompatActivity {

    private FirebaseDatabase rDb;
    final Context mContext = this;
    private DatabaseReference rDbRef;
    private TextView colRetrieveText,colNameRet,colAddRet;
    private ImageButton iButton;
    private Button viewEvent,add;
    private collegeModel collBobj;
    private User_model userGet;
    public String retPassword,collPasswordString,dialogPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_college_profile);

        colRetrieveText=(TextView)findViewById(R.id.branch_list);
        colNameRet=(TextView)findViewById(R.id.college_profile_name);
        colAddRet=(TextView)findViewById(R.id.college_profile_short_bio);
        iButton=(ImageButton)findViewById(R.id.user_profile_photo);
        viewEvent= (Button) findViewById(R.id.viewevent);
        add=(Button)findViewById(R.id.addeventprofile);
        rDb=FirebaseDatabase.getInstance();
        rDbRef=rDb.getReference().child("Colleges");


        Bundle getuser=getIntent().getExtras();
        userGet=(User_model) getuser.getSerializable("userObj");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //  retrievePassword();
                dialogBox();

            }
        });

        viewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle userBundle=new Bundle();
                userBundle.putSerializable("userObj",userGet);

                Bundle collegeBundle=new Bundle();
                collegeBundle.putSerializable("collObj",collBobj);
                Intent intent=new Intent(College_profile.this,EventListActivity.class);
                intent.putExtras(collegeBundle);
                intent.putExtras(userBundle);
                startActivity(intent);
            }
        });




        Bundle get=getIntent().getExtras();
        final String strCol=get.getString("Name");
        final String strAdd=get.getString("Add");
        collBobj=(collegeModel) get.getSerializable("collObj");
        String collPasswordString =collBobj.getPassword();

        colNameRet.setText(strCol);
        colAddRet.setText(strAdd);

        if(collBobj.getCivil()==true)
        {
            colRetrieveText.append("Civil\n");
        }
         if(collBobj.getMech()==true)
        {
            colRetrieveText.append("Mechanical\n");
        }
        if(collBobj.getProduction()==true)
        {
            colRetrieveText.append("Production\n");
        }
        if(collBobj.getElectronics()==true)
        {
            colRetrieveText.append("Electronics\n");
        }
        if(collBobj.getComputer()==true)
        {
            colRetrieveText.append("Computer\n");
        }
        if(collBobj.getInstrumentation()==true)
        {
            colRetrieveText.append("Instrumentation\n");
        }
        if(collBobj.getBiomedical()==true)
        {
            colRetrieveText.append("Biomedical\n");
        }
        if(collBobj.getAutomobile()==true)
        {
            colRetrieveText.append("Automobile\n");
        }
        if(collBobj.getExtc()==true)
        {
            colRetrieveText.append("Electronics and Telecommunication\n");
        }
        if(collBobj.getChemical()==true)
        {
            colRetrieveText.append("Chemical\n");
        }
        if(collBobj.getiT()==true)
        {
            colRetrieveText.append("Information Technology\n");
        }

        Picasso.with(getApplicationContext()).load(collBobj.photoUrl).fit().into(iButton);







    }

    public  void dialogBox(){
        LayoutInflater li = LayoutInflater.from(mContext);
        View dialogView = li.inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        // set title
        alertDialogBuilder.setTitle("Enter the password");
        // set custom dialog icon
        //alertDialogBuilder.setIcon(R.drawable.ic_launcher);
        // set custom_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(dialogView);
        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.et_input);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                 dialogPassword=userInput.getText().toString().trim();

                                if(dialogPassword.equals(collBobj.getPassword())){
                                    Bundle collegeBundle=new Bundle();
                                    collegeBundle.putSerializable("collObj",collBobj);

                                    Intent intent=new Intent(College_profile.this,addEventActivity.class);
                                    intent.putExtras(collegeBundle);
                                    startActivity(intent);
                                }
                                else if (TextUtils.isEmpty(dialogPassword)){
                                    Toast.makeText(College_profile.this, "Please enter something", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(College_profile.this, "Wrong password,try again ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();


    }

    /*public void retrievePassword(){
        rDbRef.orderByChild("password").equalTo(collPasswordString).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                collegeModel colPass=dataSnapshot.getValue(collegeModel.class);
                retPassword=colPass.getPassword();
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
        });

    }*/


}
