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
        setContentView(R.layout.activity_main);
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
                    Toast.makeText(MainActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else if(emailtext.getText().toString().trim().matches(emailPattern)) {
                    Boolean checkuser=DB.checkusername(user);
                    if(checkuser==false){
                        Boolean insertData=DB.insertData(user,email);
                        if(insertData==true){
                            Toast.makeText(MainActivity.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"You Have Registered Already..!Start the Test",Toast.LENGTH_SHORT).show();}
                }
                else
                {
                    emailtext.setError("Enter valid Email Address!");
                }
            }


        });

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Start Button clicked");
                String user = nametext.getText().toString();
                String email = emailtext.getText().toString();
                if (nametext.getText().toString().trim().equals("")) {
                    nametext.setError("First name is required!");

                    nametext.setHint("please enter username");
                }
                else if (emailtext.getText().toString().trim().equals("")) {
                    emailtext.setError("Email ID is required!");

                    emailtext.setHint("please enter Email ID");
                }
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, email);
                    if(checkuserpass==true){
                        Log.w(TAG,"Starting test");
                        String name = nametext.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtra("myname", name);
                        startActivity(intent);
                    }else{
                        Log.e(TAG,"credentials invalid");
                        Toast.makeText(MainActivity.this, "Invalid Credentials!Register to take test", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"About Button clicked");
                Intent intent=new Intent(getApplicationContext(),DeveloperActivity.class);
                startActivity(intent);
                Log.w(TAG,"Going to developer page");
            }
        });
    }
}