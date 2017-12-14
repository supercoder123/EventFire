package com.eventfire.ashley.eventfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by gf on 25-03-2017.
 */

public class addEventActivity extends AppCompatActivity {

    public static TextView dateText;
    public static TextView duration;

    private collegeModel eventObj;
    private ProgressDialog progress;
    private StorageReference mPhoto;
    private Uri downloadUrl;
    private TextView collName;
    private DatabaseReference mDbRef;
    private Button eventImage,addEvent,datePicker;
    private static final int PHOTO_UPLOAD_CODE=2;
    private static final int DATE_CODE=3;
    public String receivedDate;
    private Uri imageUri=null;
    private ImageView imageView;
    private TextInputLayout eventName,eventDesc,contactPerson,mobileNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Bundle get=getIntent().getExtras();

        collName= (TextView) findViewById(R.id.college_name_form);
        eventImage= (Button) findViewById(R.id.eventImageUpload);
        addEvent= (Button) findViewById(R.id.addevent);
        imageView= (ImageView) findViewById(R.id.eventImageView);
        datePicker= (Button) findViewById(R.id.dateButton);
        dateText= (TextView) findViewById(R.id.dateText);
                eventName= (TextInputLayout) findViewById(R.id.inputLayouteventname);
                eventDesc= (TextInputLayout) findViewById(R.id.inputLayoutdescription);
            //  date= (TextView) findViewById(R.id.dateText);
                duration= (TextView) findViewById(R.id.duration);
                contactPerson= (TextInputLayout) findViewById(R.id.inputLayoutcontactname);
                mobileNum= (TextInputLayout) findViewById(R.id.inputLayoutcontactnumber);
        progress=new ProgressDialog(this);

        mDbRef=FirebaseDatabase.getInstance().getReference().child("Events");


        mPhoto= FirebaseStorage.getInstance().getReference();



        eventObj=(collegeModel) get.getSerializable("collObj");
        collName.setText(eventObj.getName());

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(addEventActivity.this,CalendarActivity.class);
                startActivity(intent);
                //startActivityForResult(intent,DATE_CODE);
            }
        });

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent=new Intent(Intent.ACTION_GET_CONTENT);
                photoIntent.setType("image/*");

                startActivityForResult(photoIntent,PHOTO_UPLOAD_CODE);

            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });



    }



    public void addEvent(){
        final String ename=eventName.getEditText().getText().toString().trim();
        final String desc=eventDesc.getEditText().getText().toString().trim();
        final String edate=dateText.getText().toString().trim();
        final String edur=duration.getText().toString().trim();
        final String cperson=contactPerson.getEditText().getText().toString().trim();
        final String emobile=mobileNum.getEditText().getText().toString().trim();

        if(!TextUtils.isEmpty(ename)&&!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(edate)
                &&!TextUtils.isEmpty(edur)&&!TextUtils.isEmpty(cperson)&&!TextUtils.isEmpty(emobile)&&imageUri!=null){
                progress.setTitle("Uploading Event ..");
                progress.show();
                StorageReference filepath=mPhoto.child("Event_Photo").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUrl=taskSnapshot.getDownloadUrl();
                        progress.dismiss();
                        eventModel eventObjUp=new eventModel(ename,desc,edate,emobile,cperson,downloadUrl.toString(),edur,eventObj.getName());
                        mDbRef.push().setValue(eventObjUp);
                        Toast.makeText(addEventActivity.this,"Event added successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        }
        else
        {
            Toast.makeText(addEventActivity.this,"Please enter all the details .",Toast.LENGTH_SHORT).show();

        }
    }





    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==PHOTO_UPLOAD_CODE&&resultCode==RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
     /*   if(requestCode==DATE_CODE&&resultCode==RESULT_OK){
            receivedDate=data.getStringExtra("Date");

        }*/
    }



}
