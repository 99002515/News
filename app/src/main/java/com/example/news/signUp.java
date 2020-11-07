package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity {
    public  static String TAG = signUp.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"Activity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final DBHelper DB;
        DB=new DBHelper(this);
        final  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Button registerbutton=(Button)findViewById(R.id.register) ;
        Button startbutton=(Button)findViewById(R.id.button);
//        Button aboutbutton=(Button)findViewById(R.id.button2);
        final EditText nametext=(EditText)findViewById(R.id.editName);
        final EditText emailtext=(EditText)findViewById(R.id.editEmail);


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"Registration Button clicked");
                String user=nametext.getText().toString();
                String email=emailtext.getText().toString();
                if(user.equals("")||email.equals(""))
                    Toast.makeText(signUp.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else if(emailtext.getText().toString().trim().matches(emailPattern)) {
                    Boolean checkuser=DB.checkusername(user);
                    if(checkuser==false){
                        Boolean insertData=DB.insertData(user,email);
                        if(insertData==true){
                            Toast.makeText(signUp.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),signUp.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(signUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(signUp.this,"You Have Registered Already..!Start the Test",Toast.LENGTH_SHORT).show();}
                }
                else
                {
                    emailtext.setError("Enter valid Email Address!");
                }
            }


        });
        startbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(signUp.this, LoginActivity.class));
            }
        });


    }
}