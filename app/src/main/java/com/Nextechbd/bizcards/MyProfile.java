package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nextechbd.bizcards.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {
TextView name,job,company,phone,email,website,address;
ImageView image;
Profileapi mapi;
      public String Name,Job,Company,Phone,Email,Website,Address,Image,user_Id;

    public static final String NAME="name";
    public static final String JOB="job";
    public static final String COMPANY="company";
    public static final String PHONE="phone";
    public static final String EMAIL="demail";
    public static final String WEBSITE="website";
    public static final String ADDRESS="address";
    public static final String IMAGE="image";
    public static final String ID="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        final ProgressDialog progressDialog = new ProgressDialog(MyProfile.this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        SessionManagment sessionManagment = new SessionManagment(MyProfile.this);
    user_Id = String.valueOf(sessionManagment.getSession());
        mapi=AppConfig.getAPIService13();

        Call<getprofileresponse> call = mapi.getStringScalar(user_Id );
        call.enqueue(new Callback<getprofileresponse>() {
            @Override
            public void onResponse(Call<getprofileresponse> call, Response<getprofileresponse> response) {
                getprofileresponse profileresponse=response.body();
                progressDialog.dismiss();
                image=(ImageView)findViewById(R.id.image);
                name=(TextView)findViewById(R.id.name);
                job=(TextView)findViewById(R.id.job);
                company=(TextView)findViewById(R.id.company);
                phone=(TextView)findViewById(R.id.phone);
                email=(TextView)findViewById(R.id.email);
                website=(TextView)findViewById(R.id.website);
                address=(TextView)findViewById(R.id.address);
                String img=profileresponse.getImage();
                Picasso.with(MyProfile.this).load(img).fit().centerInside().into(image);
                name.setText(profileresponse.getName());

                job.setText(profileresponse.getJob_title());
                company.setText(profileresponse.getCompany());
                phone.setText(profileresponse.getOffice_phone());
                email.setText(profileresponse.getEmail());
                website.setText(profileresponse.getWebsite());
                address.setText(profileresponse.getAddress());
                Name=name.getText().toString();
                Job=job.getText().toString();
                Company=company.getText().toString();
                Phone=phone.getText().toString();
                Email=email.getText().toString();
                Website=website.getText().toString();
                Address=address.getText().toString();
                Image=profileresponse.getImage();

            }
            @Override
            public void onFailure(Call<getprofileresponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(MyProfile.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if(id==R.id.editButton)
        {
            Intent intent = new Intent(MyProfile.this,EditActivity.class);

            intent.putExtra(NAME,Name);
            intent.putExtra(PHONE,Phone);
            intent.putExtra(JOB,Job);
            intent.putExtra(COMPANY,Company);
            intent.putExtra(EMAIL,Email);
            intent.putExtra(WEBSITE,Website);
            intent.putExtra(ADDRESS,Address);
            intent.putExtra(ID,user_Id);
            intent.putExtra(IMAGE,Image);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            SessionManagment sessionManagment = new SessionManagment(MyProfile.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(MyProfile.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
