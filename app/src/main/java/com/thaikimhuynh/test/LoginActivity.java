package com.thaikimhuynh.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassword;
    Button btnLogin, btnClose;
    TextView txtMessage;
    SharedPreferences sharedPreferences;
    String Key_Preference="LOGIN_PREFERENCE";
    CheckBox chkSaveLoginInfor;
    public static final String DATABASE_NAME = "product.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(LoginActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addViews();
        copyDataBase();
        readLoginInformation();

    }
    private void addViews() {
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        txtMessage=findViewById(R.id.txtMessage);
        chkSaveLoginInfor=findViewById(R.id.chkSaveLoginInfor);


    }
    public void exitApp(View view) {
        confirmExit();
    }

    private void confirmExit() {
        //Create builder object (instance):
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        //set title:
        builder.setTitle("Confirm Exit!");
        //set messsage (body content)
        builder.setMessage("Are you sure you want to exit?");
        //set Icon:
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //set actions button for user interactions:
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //Create Dialog object:
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        //show dialog for user interactions:
        dialog.show();
    }
    public void openMainActivity(View view) {
        String userName=edtUserName.getText().toString();
        String pwd=edtPassword.getText().toString();
        if(loginSystem(userName,pwd)!=null)
        {

            Intent intent=new Intent(LoginActivity.this, ListOfProductsActivity.class);



            startActivity(intent);
            Toast.makeText(
                            LoginActivity.this,
                            "Login Successful!",
                            Toast.LENGTH_SHORT)
                    .show();
            SharedPreferences sharedPreferences = getSharedPreferences(Key_Preference, MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("USER_NAME",userName);
            editor.putString("PASSWORD",pwd);
            editor.putBoolean("SAVED",chkSaveLoginInfor.isChecked());
            editor.commit();


        } else{
            //Alarm login failed
            txtMessage.setText("Login failed, please check your account again");
            Toast.makeText(
                    LoginActivity.this,
                    "Login Failed!",
                    Toast.LENGTH_SHORT).show();

        }
    }
    void readLoginInformation()
    {
        sharedPreferences=getSharedPreferences(Key_Preference,MODE_PRIVATE);
        String userName=sharedPreferences.getString("USER_NAME","");
        String pwd=sharedPreferences.getString("PASSWORD","");
        boolean saved=sharedPreferences.getBoolean("SAVED",false);
        if(saved)
        {
            edtUserName.setText(userName);
            edtPassword.setText(pwd);
        }
        else {
            edtUserName.setText("");
            edtPassword.setText("");
        }
        chkSaveLoginInfor.setChecked(saved);
    }
    public Account loginSystem(String userName, String pwd)
    {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        String sql="select * from Account where UserName='"+userName+"' and PassWord='"+pwd+"'";
        Cursor cursor=database.rawQuery(sql,null);
        if(cursor.moveToNext())
        {
            String usn=cursor.getString(1);
            String p=cursor.getString(2);
            Account ac= new Account(usn,p);
            cursor.close();
            return ac;
        }
        cursor.close();
        return null;
    }
}
