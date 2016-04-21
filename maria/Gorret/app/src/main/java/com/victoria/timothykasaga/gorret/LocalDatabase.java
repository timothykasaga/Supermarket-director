package com.victoria.timothykasaga.gorret;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Leontymo on 4/21/2016.
 */
public class LocalDatabase {
    public static final String SP_NAME = "userData";
    SharedPreferences localDatabase;

    public LocalDatabase(Context context){
        localDatabase = context.getSharedPreferences(SP_NAME,0);
    }
    public void storeData(Admin admin){
        SharedPreferences.Editor SPEditor = localDatabase.edit();
        SPEditor.putString("Name",admin.name);
        SPEditor.putString("Username",admin.username);
        SPEditor.putString("Password",admin.pass);
        SPEditor.putString("Tel",admin.tel);
        SPEditor.putString("Email",admin.email);
        SPEditor.commit();
    }
    public Admin getLoggedInAdmin(){
        String name = localDatabase.getString("Name","");
        String username = localDatabase.getString("Username","");
        String password = localDatabase.getString("Password","");
        String tel = localDatabase.getString("Tel","");
        String email = localDatabase.getString("Email","");

        Admin admin = new Admin(name,email,username,password,tel);
        return admin;
    }
    public void setuserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }
    public Boolean getuserLoggedIn(){
        if(localDatabase.getBoolean("loggedIn",false))
        {
            return  true;
        }else{
            return false;
        }
    }

    public void clearData(){
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
