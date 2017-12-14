package com.eventfire.ashley.eventfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.supportsAssist;

public class collegeFormActivity extends AppCompatActivity {

    public TextInputLayout cName,cAddress,cPassword,cConfirmPassword;
    private CheckBox checkBoxCivil,checkBoxMech,checkBoxProd,checkBoxElec,checkBoxComp,checkBoxInstru,checkBoxBio,checkBoxAuto,checkBoxExtc,checkBoxChem,checkBoxIT;
    private Button addCollege,cImageUpload;
    private Uri downloadUrl;
    private Uri imageUri=null;
    private ImageView imageView;
    private StorageReference mPhoto;
    private ProgressDialog progress;
    private FirebaseDatabase mFDatabase;
    private DatabaseReference mDbRef;
    //GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};

    public List selectedStrings = new ArrayList();
    private collegeModel colCheckboxObj=new collegeModel();

    private static final int PHOTO_UPLOAD_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_form);

        cName=(TextInputLayout)findViewById(R.id.inputLayoutcollegename);
        cAddress=(TextInputLayout) findViewById(R.id.inputLayoutcollegeaddress);
        checkBoxCivil=(CheckBox)findViewById(R.id.checkboxCivil);
        checkBoxElec=(CheckBox)findViewById(R.id.checkboxElec);
        checkBoxProd=(CheckBox)findViewById(R.id.checkboxProd);
        checkBoxComp=(CheckBox)findViewById(R.id.checkboxComps);
        checkBoxInstru=(CheckBox)findViewById(R.id.checkboxInstru);
        checkBoxBio=(CheckBox)findViewById(R.id.checkboxBio);
        checkBoxAuto=(CheckBox)findViewById(R.id.checkboxAuto);
        checkBoxExtc=(CheckBox)findViewById(R.id.checkboxExtc);
        checkBoxChem=(CheckBox)findViewById(R.id.checkboxChem);
        checkBoxIT=(CheckBox)findViewById(R.id.checkboxIt);
        checkBoxMech=(CheckBox)findViewById(R.id.checkboxMech);

        addCollege=(Button)findViewById(R.id.addCollege);
        cImageUpload=(Button)findViewById(R.id.collegeImageUpload);
        imageView=(ImageView)findViewById(R.id.colImageView);
        cPassword=(TextInputLayout)findViewById(R.id.inputLayoutpassword);
        cConfirmPassword=(TextInputLayout)findViewById(R.id.inputLayoutconfirmpassword);

        progress=new ProgressDialog(this);

        //firebase
       mPhoto= FirebaseStorage.getInstance().getReference();
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mDbRef=FirebaseDatabase.getInstance().getReference().child("Colleges");




        addCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addNewCollege();
            }
        });

        cImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent=new Intent(Intent.ACTION_GET_CONTENT);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent,PHOTO_UPLOAD_CODE);

            }
        });

        checkBoxCivil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // selectedStrings.add(checkBoxCivil.getText().toString());
                   //colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setCivil(true);


                }
            }
        });

        checkBoxMech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // selectedStrings.add(checkBoxMech.getText().toString());
                   // colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setMech(true);

                }
            }
        });

        checkBoxProd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // selectedStrings.add(checkBoxProd.getText().toString());
          //          colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setProduction(true);
                }
            }
        });

        checkBoxElec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //selectedStrings.add(checkBoxElec.getText().toString());
        //            colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setElectronics(true);

                }
            }
        });

        checkBoxComp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  //  selectedStrings.add(checkBoxComp.getText().toString());
      //              colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setComputer(true);

                }
            }
        });

        checkBoxInstru.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //selectedStrings.add(checkBoxInstru.getText().toString());
    //                colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setInstrumentation(true);

                }
            }
        });

        checkBoxBio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  //  selectedStrings.add(checkBoxBio.getText().toString());
  //                  colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setBiomedical(true);

                }
            }
        });

        checkBoxAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // selectedStrings.add(checkBoxAuto.getText().toString());
//                    colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setAutomobile(true);


                }
            }
        });

        checkBoxExtc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  //  selectedStrings.add(checkBoxExtc.getText().toString());
                   // colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setExtc(true);

                }
            }
        });
//TODO
        checkBoxChem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  // selectedStrings.add(checkBoxChem.getText().toString());
                  //  colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setChemical(true);

                }
            }
        });

        checkBoxIT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  //  selectedStrings.add(checkBoxIT.getText().toString());
                 //   colCheckboxObj.setBranch(selectedStrings);
                    colCheckboxObj.setiT(true);

                }
            }
        });


    }




    public void addNewCollege(){

        final String colName=cName.getEditText().getText().toString().trim();
        final String colAdd=cAddress.getEditText().getText().toString().trim();
        final String password=cPassword.getEditText().getText().toString().trim();
        String confirmPassword=cConfirmPassword.getEditText().getText().toString().trim();

        if(!TextUtils.isEmpty(colName)&&!TextUtils.isEmpty(colAdd)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(confirmPassword)&&imageUri!=null){
            if (password.equals(confirmPassword)){
                progress.setTitle("Uploading Image and College details..");
                progress.show();
                StorageReference filepath=mPhoto.child("College_Photo").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUrl=taskSnapshot.getDownloadUrl();
                        progress.dismiss();
                      //  GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                      //  ArrayList<String> selectedStrings=taskSnapshot.toString();
                       // collegeModel colObj=new collegeModel(colName,colAdd,downloadUrl.toString(),password,selectedStrings);
                        colCheckboxObj=new collegeModel(colName,
                                colAdd,
                                downloadUrl.toString(),
                                password,
                                colCheckboxObj.getCivil(),
                                colCheckboxObj.getMech(),
                                colCheckboxObj.getProduction(),
                                colCheckboxObj.getElectronics(),
                                colCheckboxObj.getComputer(),
                                colCheckboxObj.getInstrumentation(),
                                colCheckboxObj.getBiomedical(),
                                colCheckboxObj.getAutomobile(),
                                colCheckboxObj.getExtc(),
                                colCheckboxObj.getChemical(),
                                colCheckboxObj.getiT()
                        );

                        mDbRef.push().setValue(colCheckboxObj);
                        Toast.makeText(collegeFormActivity.this,"College added successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
            else{
                Toast.makeText(collegeFormActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(collegeFormActivity.this,"Please enter all the details .",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==PHOTO_UPLOAD_CODE&&resultCode==RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }


}
