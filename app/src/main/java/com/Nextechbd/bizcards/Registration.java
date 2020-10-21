package com.Nextechbd.bizcards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Nextechbd.bizcards.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    EditText name,phone,referel,password1,password2;
    private RegistrationApi mAPIService;
    private Verifyapi mAPIService1;
    Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final ProgressDialog progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setTitle("Please wait");
        name=(EditText)findViewById(R.id.uname);
        phone=(EditText) findViewById(R.id.uphone);
        referel=(EditText)findViewById(R.id.referel);
        password1=(EditText) findViewById(R.id.upassword1);
        password2=(EditText) findViewById(R.id.upassword2);
        submitBtn=(Button) findViewById(R.id.ubutton);
        mAPIService= AppConfig.getAPIService();
        mAPIService1= AppConfig.getAPIService3();
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() )
                {
                    name.setError("UserName is required");
                    name.requestFocus();
                    return;
                }
                if(phone.getText().toString().isEmpty() )
                {
                    phone.setError("PhoneNumber is required");
                    phone.requestFocus();
                    return;
                }
                if(!(phone.getText().toString().length()==11 ))
                {
                    phone.setError("Insert valid PhoneNumber");
                    phone.requestFocus();
                    return;
                }
                if(!(phone.getText().toString().charAt(0)=='0'&& phone.getText().toString().charAt(1)=='1'))
                {
                    phone.setError("Insert valid PhoneNumber");
                    phone.requestFocus();
                    return;
                }

                if(password1.getText().toString().isEmpty() )
                {
                    password1.setError("pasword is required");
                    password1.requestFocus();
                    return;
                }
                if(password2.getText().toString().isEmpty())
                {
                    password2.setError("Confirm pasword is required");
                    password2.requestFocus();
                    return;
                }
                if(!(password1.getText().toString().equals( password2.getText().toString())))
                {
                    password2.setError("Confirm pasword is required");
                    password2.requestFocus();

                    return;
                }
                if(!(referel.getText().toString().isEmpty()))
                {
                    if(referel.getText().toString().length()!=6)
                    {
                        referel.setError("Enter 6 digit referel code");
                        referel.requestFocus();
                        return;
                    }
                }
                try {

                    send( name.getText().toString(),phone.getText().toString(), password1.getText().toString(),referel.getText().toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

    }
    public void send(String name, String phone,  String password,String referel) throws IOException {
        final String Name=name;
        final String Mobile=phone;
        final String Password=password;
        final String Referel=referel;
        final ProgressDialog progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setTitle("Please wait");
progressDialog.show();
        Call<Verifyresponse> call = mAPIService1.getStringScalar(  phone, password );
        call.enqueue(new Callback<Verifyresponse>() {
            @Override
            public void onResponse(Call<Verifyresponse> call, Response<Verifyresponse> response) {
                Verifyresponse verifyresponse=response.body();
                String msg="yes";
                String responsemsg=verifyresponse.getMessage();
                if(responsemsg.equals(msg)) {

                    try {
                        progressDialog.dismiss();
                        sendPost(Name, Mobile, Password,Referel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Login.this, loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Registration.this, verifyresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Verifyresponse> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(Registration.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void sendPost( String name, String phone, String password,String referel) throws IOException {

        Call<String> call = mAPIService.getStringScalar( name, phone, password,referel);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Toast.makeText(Registration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(Registration.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}

