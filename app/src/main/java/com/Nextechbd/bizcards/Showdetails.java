package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.Nextechbd.bizcards.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Showdetails extends AppCompatActivity implements AdapterClass.OnItemClickListener{
//public class Showdetails extends AppCompatActivity {
public static final String Card_Id="card_id";
    public static final String Card_Details="details";
    public static final String EMAIL1="mail1";
    public static final String EMAIL2="mail2";
    public static final String ID="id";
    public static final String PHONE1="phone1";
    public static final String PHONE2="phone2";
    public static final String image="image";
private EditText search;
private ImageButton ok;
public int userId;


    private RecyclerView recyclerView;
    private AdapterClass adapter;
    private RecyclerView.LayoutManager layoutmanager;
    List<Show_element> data;
    private Showapi mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.onStart();
        final ProgressDialog progressDialog = new ProgressDialog(Showdetails.this);
        progressDialog.setTitle("Please wait");

        progressDialog.show();
        SessionManagment sessionManagment = new SessionManagment(Showdetails.this);
      userId = sessionManagment.getSession();

        if (userId != -1) {
            setContentView(R.layout.activity_showdetails);
            //final ArrayList<Card_element>[] data = new ArrayList[]{new ArrayList<>()};
            mAPIService = AppConfig.getAPIService4();
            search=(EditText)findViewById(R.id.search);
            recyclerView=findViewById(R.id.show);
            ok=(ImageButton)findViewById(R.id.ok);
            recyclerView.setHasFixedSize(true);
            layoutmanager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutmanager);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    String SEARCH="";
                    SEARCH = SEARCH + search.getText().toString();

                    Call<List<Show_element>> call = mAPIService.getdata(String.valueOf(userId), SEARCH);
                    SEARCH = "";


                    call.enqueue(new Callback<List<Show_element>>() {
                        @Override
                        public void onResponse(Call<List<Show_element>> call, Response<List<Show_element>> response) {
                            List<Show_element> showresponse = response.body();
                            data = showresponse;

                            // data.add(new Card_element("abc","bfg"));
                            //data = new ArrayList<>(Arrays.asList(cardresponse.getCard()));
                            //int l=data.size();
                            //Log.d("data", String.valueOf(l));
                            //Toast.makeText(Recycleviewactivity.this, l, Toast.LENGTH_SHORT).show();
                            adapter = new AdapterClass(Showdetails.this, showresponse);

                            adapter.setOnClickListener(Showdetails.this);
                            progressDialog.dismiss();
                            recyclerView.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<List<Show_element>> call, Throwable t) {
                            progressDialog.dismiss();
                        }


                    });
                }
            });
            String SEARCH="";

            Call<List<Show_element>> call = mAPIService.getdata( String.valueOf(userId),SEARCH);
            SEARCH="";

            call.enqueue(new Callback<List<Show_element>>() {
                @Override
                public void onResponse(Call<List<Show_element>> call, Response<List<Show_element>> response) {
                    List<Show_element> showresponse=response.body();
                    data=showresponse;
                    // data.add(new Card_element("abc","bfg"));
                    //data = new ArrayList<>(Arrays.asList(cardresponse.getCard()));
                    //int l=data.size();
                    //Log.d("data", String.valueOf(l));
                    //Toast.makeText(Recycleviewactivity.this, l, Toast.LENGTH_SHORT).show();


                    adapter=new AdapterClass(Showdetails.this,showresponse);

                    adapter.setOnClickListener(Showdetails.this);
                    progressDialog.dismiss();
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<List<Show_element>> call, Throwable t) {
                    progressDialog.dismiss();
                }


            });


        }
        else {
            Intent intent = new Intent(Showdetails.this, Login.class);
            startActivity(intent);
        }


    }
    public void OnItemClick(int position) {
        Intent detailIntent=new Intent(this,descriptionActivity.class);
        //Show_element currentItem=examplelist.get(position);
        Show_element clickItem=data.get(position);
        detailIntent.putExtra(Card_Id,clickItem.getCard_id());
        detailIntent.putExtra(Card_Details,clickItem.getCard_details());
        detailIntent.putExtra(EMAIL1,clickItem.getEmail1());
        detailIntent.putExtra(EMAIL2,clickItem.getEmail2());
        detailIntent.putExtra(PHONE1,clickItem.getPhone1());
        detailIntent.putExtra(PHONE2,clickItem.getPhone2());
        detailIntent.putExtra(image,clickItem.getCard_image());
        // detailIntent.putExtra(EXTRA_LIKES,clickItem.getmLikes());
        // detailIntent.putExtra("imageUrlAll", Images);
        startActivity(detailIntent);

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
            SessionManagment sessionManagment = new SessionManagment(Showdetails.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(Showdetails.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
