package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.Nextechbd.bizcards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstActivity extends AppCompatActivity {
Button scan,show,creat,profile;
ImageView image1,image2,image3;
Galaryfront mapi;
public int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        scan=(Button)findViewById(R.id.scan);
        show=(Button)findViewById(R.id.show);
        creat=(Button)findViewById(R.id.creat);
        profile=(Button)findViewById(R.id.profile);
        mapi=AppConfig.getAPIService14();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, Showdetails.class);

                startActivity(intent);
            }
        });
      creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this, CreatProfile.class);
                startActivity(intent);
            }
        });

      profile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent = new Intent(FirstActivity.this, MyProfile.class);
              startActivity(intent);
          }
      });
        SessionManagment sessionManagment = new SessionManagment(FirstActivity.this);
        userId = sessionManagment.getSession();
        Call<List<Galaryresponse>> call = mapi.getStringScalar(String.valueOf(userId));
        call.enqueue(new Callback<List<Galaryresponse>>() {
            @Override
            public void onResponse(Call<List<Galaryresponse>> call, Response<List<Galaryresponse>> response) {
                List<Galaryresponse> showresponse = response.body();
                int i=showresponse.size();

                    if(i==1)
                    {
                       image1=(ImageView)findViewById(R.id.image1);
                        Picasso.with(FirstActivity.this).load(showresponse.get(0).getCard_image()).fit().centerInside().into(image1);
                    }
                    else if(i==2)
                    {
                        image1=(ImageView)findViewById(R.id.image1);
                        Picasso.with(FirstActivity.this).load(showresponse.get(0).getCard_image()).fit().centerInside().into(image1);
                        image2=(ImageView)findViewById(R.id.image2);
                        Picasso.with(FirstActivity.this).load(showresponse.get(1).getCard_image()).fit().centerInside().into(image2);

                    }
                    else if(i==3)
                    {
                        image1=(ImageView)findViewById(R.id.image1);
                        Picasso.with(FirstActivity.this).load(showresponse.get(0).getCard_image()).fit().centerInside().into(image1);
                        image2=(ImageView)findViewById(R.id.image2);
                        Picasso.with(FirstActivity.this).load(showresponse.get(1).getCard_image()).fit().centerInside().into(image2);
                        image3=(ImageView)findViewById(R.id.image3);
                        Picasso.with(FirstActivity.this).load(showresponse.get(2).getCard_image()).fit().centerInside().into(image3);

                    }



            }

            @Override
            public void onFailure(Call<List<Galaryresponse>> call, Throwable t) {

            }


        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.addImage) {
            ShowImageImportDialouge();
            check=1;
        }*/
        if (id == R.id.logout) {
            SessionManagment sessionManagment = new SessionManagment(FirstActivity.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(FirstActivity.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
