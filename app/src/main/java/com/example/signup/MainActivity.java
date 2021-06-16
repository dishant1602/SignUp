package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText name, mailid, contact, psswd;
    ImageView imageView2;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        mailid = findViewById(R.id.mailid);
        contact = findViewById(R.id.contact);
        psswd = findViewById(R.id.psswd);
        imageView2 = findViewById(R.id.imageView2);
        auth=FirebaseAuth.getInstance();

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please Wait");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String str_name = name.getText().toString();
                String str_mailid = mailid.getText().toString();
                String str_contact = contact.getText().toString();
                String str_psswd = psswd.getText().toString();
                if((str_name.length()==0)&(str_contact.length()==0)&(str_mailid.length()==0)&(str_psswd.length()==0)){
                    Toast.makeText(MainActivity.this, "All fields are empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if(str_name.length()==0){
                    name.setError("Name field cannot be empty");
                    progressDialog.dismiss();
                }
                else if(str_mailid.length()==0){
                    mailid.setError("Mail Id cannot be empty");
                    progressDialog.dismiss();
                }
                else if(!str_mailid.contains("@")){
                    mailid.setError("Enter a valid email id");
                    progressDialog.dismiss();
                }
                else if(str_contact.length()<10){
                    contact.setError("Phone Number cannot be less than 10 digits");
                    progressDialog.dismiss();
                }
                else if(str_contact.length()>10) {
                    contact.setError("Phone Number cannot be more than 10 digits");
                    progressDialog.dismiss();
                }
                else if(str_psswd.length()<=8){
                    psswd.setError("Password must be greater than 8 digits");
                    progressDialog.dismiss();
                }
                else{
                    auth.createUserWithEmailAndPassword(str_mailid, str_psswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser fuser = auth.getCurrentUser();
                                String fuid = fuser.getUid();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("Name",str_name);
                                hashMap.put("Mail ID",str_mailid);
                                hashMap.put("Password",str_psswd);
                                hashMap.put("Contact",str_contact);
                                FirebaseDatabase.getInstance().getReference("Users").child(fuid).setValue(hashMap);
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please enter a new email", Toast.LENGTH_SHORT).show();
                                mailid.requestFocus();
                                progressDialog.dismiss();
                            }
                        }
                    });
                    progressDialog.dismiss();
                }
            }
        });
    }
}