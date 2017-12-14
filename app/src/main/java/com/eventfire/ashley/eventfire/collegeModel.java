package com.eventfire.ashley.eventfire;

import android.net.Uri;

import com.google.firebase.database.GenericTypeIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gf on 13-03-2017.
 */

public class collegeModel implements Serializable {

    public String name,address,password;
    public String photoUrl;
    public boolean civil,mech,production,electronics,computer,instrumentation,biomedical,
            automobile,extc,chemical,iT;


    public collegeModel() {

    }
    public collegeModel(String name,String address,String photoUrl,
                        String password,boolean civil,boolean mech,
                        boolean production ,boolean electronics,boolean computer,
                        boolean instrumentation,boolean biomedical,boolean automobile,
                        boolean extc,boolean chemical,boolean iT){
        this.name=name;
        this.address=address;
        this.photoUrl=photoUrl;
        this.password=password;
        this.civil=civil;
        this.mech=mech;
        this.production=production;
        this.electronics=electronics;
        this.computer=computer;
        this.instrumentation=instrumentation;
        this.biomedical=biomedical;
        this.automobile=automobile;
        this.extc=extc;
        this.chemical=chemical;
        this.iT=iT;
    }

   /* public collegeModel(String name,String address,String photoUrl,String password, List branch){
        this.name=name;
        this.address=address;
        this.photoUrl=photoUrl;
        this.password=password;
        this.branch=branch;
    }*/

//TODO

  /*public void setBranch(List branch){
       this.branch=branch;
     //  branchn.add(br);
    }*/

    public void setCivil(boolean civil){this.civil=civil; }

    public void setMech(boolean mech){this.mech=mech; }

    public void setProduction(boolean production){this.production=production; }

    public void setElectronics(boolean electronics){this.electronics=electronics; }

    public void setComputer(boolean computer ){this.computer=computer; }

    public void setInstrumentation(boolean instrumentation){this.instrumentation=instrumentation; }


    public void setBiomedical(boolean biomedical){this.biomedical=biomedical; }


    public void setAutomobile(boolean automobile){this.automobile=automobile; }


    public void setExtc(boolean extc){this.extc=extc; }


    public void setChemical(boolean chemical){this.chemical=chemical; }

    public void setiT(boolean iT ){this.iT=iT; }




    public void setName(String name){
        this.name=name;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public void setPassword(String password) { this.password=password; }

    public void setPhotoUrl(){
        this.photoUrl=photoUrl;
    }

    public String getName(){
        return name;
    }

   /* public ArrayList getBranch(){
        return (ArrayList) branch;
    }*/
    public boolean getCivil(){return civil;}

    public boolean getElectronics(){return electronics;}

    public boolean getProduction(){return production;}

    public boolean getComputer(){return computer;}

    public boolean getInstrumentation(){return instrumentation;}

    public boolean getBiomedical(){return biomedical;}

    public boolean getAutomobile(){return automobile;}

    public boolean getExtc(){return extc;}

    public boolean getChemical(){return chemical;}

    public boolean getiT(){return iT;}
    public boolean getMech(){return mech;}

    public String getAddress(){
        return address;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

    public String getPassword(){ return password;}

}
