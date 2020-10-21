package com.Nextechbd.bizcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Nextechbd.bizcards.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {
    EditText phone,password;
    Button login;
    private Loginapi mAPIService;
    TextView regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        super.onStart();
        SessionManagment sessionManagment = new SessionManagment(Login.this);
        int userId = sessionManagment.getSession();
        Intent intent1=getIntent();


        if (userId != -1 ) {
            Intent intent = new Intent(Login.this, FirstActivity.class);
            startActivity(intent);
            // Toast.makeText(Login.this, "yes", Toast.LENGTH_SHORT).show();
        }
        else {
            setContentView(R.layout.activity_login);
            phone = (EditText) findViewById(R.id.uphone);
            password = (EditText) findViewById(R.id.upassword);

            login = (Button) findViewById(R.id.ubuttonlogin);
            regButton = (TextView) findViewById(R.id.registerID);
            mAPIService = AppConfig.getAPIService7();

            regButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Intent intent = new Intent(Login.this, Registration.class);

                    startActivity(intent);
                    return false;
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phone.getText().toString().isEmpty()) {
                        phone.setError("PhoneNumber is required");
                        phone.requestFocus();
                        return;
                    }
                    if (password.getText().toString().isEmpty()) {
                        password.setError("Password is required");
                        password.requestFocus();
                        return;
                    }
                    try {
                        sendPost(phone.getText().toString(), password.getText().toString());
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
    public void sendPost(String phone,  String password) throws IOException {
        final String p=password;
        Call<Loginresponse> call = mAPIService.getStringScalar( phone, password );

        call.enqueue(new Callback<Loginresponse>() {
            @Override
            public void onResponse(Call<Loginresponse> call, Response<Loginresponse> response) {
                Loginresponse loginresponse=response.body();
                String msg="successfull";
                String responsemsg=loginresponse.getMessage();
                if(responsemsg.equals(msg)) {
                    User user=new User(loginresponse.getId(),loginresponse.getPhone());
                    SessionManagment sessionManagment=new SessionManagment(Login.this) ;
                    sessionManagment.saveSession(user);
                    Intent intent = new Intent(Login.this, FirstActivity.class);

                    startActivity(intent);
                    //    Intent intent = new Intent(Login.this, MainActivity.class);
                    //     startActivity(intent);

                    // Toast.makeText(Login.this, loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Login.this, loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Loginresponse> call, Throwable t) {

                Toast.makeText(Login.this, "something error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}