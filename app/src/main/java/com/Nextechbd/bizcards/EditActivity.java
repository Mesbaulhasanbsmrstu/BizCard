package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nextechbd.bizcards.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.Nextechbd.bizcards.MyProfile.NAME;
import static com.Nextechbd.bizcards.MyProfile.JOB;
import static com.Nextechbd.bizcards.MyProfile.COMPANY;
import static com.Nextechbd.bizcards.MyProfile.PHONE;
import static com.Nextechbd.bizcards.MyProfile.EMAIL;
import static com.Nextechbd.bizcards.MyProfile.ADDRESS;
import static com.Nextechbd.bizcards.MyProfile.WEBSITE;
import static com.Nextechbd.bizcards.MyProfile.IMAGE;
import static com.Nextechbd.bizcards.MyProfile.ID;
public class EditActivity extends AppCompatActivity {
    EditText name, job,company, phone,mail,website,address;
    public String Name,Job,Company,Phone,Email,Website,Address,Image,Id;
    ImageView image;
    Button imagebutton,save;
    String id;
    public int check = 0;

    private Updateapi mAPIService;
    private Editapi mAPIService1;
    private static final int PICK_IMAGE_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999,CAMERA_REQUEST=1;
    private Bitmap bitmap;
    private Uri filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        final ProgressDialog progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setTitle("Please wait");
        mAPIService=AppConfig.getAPIService15();
        mAPIService1=AppConfig.getAPIService16();
        name=(EditText)findViewById(R.id.name);
        job=(EditText)findViewById(R.id.job);
       company=(EditText)findViewById(R.id.company);
        phone=(EditText)findViewById(R.id.phone);
        mail=(EditText)findViewById(R.id.email);
        website=(EditText)findViewById(R.id.website);
        address=(EditText)findViewById(R.id.address);
        image=(ImageView)findViewById(R.id.image);
        imagebutton=(Button)findViewById(R.id.imageup);
        save=(Button)findViewById(R.id.save);
       Name = intent.getStringExtra(NAME);
        Job = intent.getStringExtra(JOB);
        Company = intent.getStringExtra(COMPANY);
        Phone = intent.getStringExtra(PHONE);
        Email = intent.getStringExtra(EMAIL);
        Website= intent.getStringExtra(WEBSITE);
        Address = intent.getStringExtra(ADDRESS);
        Id = intent.getStringExtra(ID);
      Image = intent.getStringExtra(IMAGE);
        name.setText(Name);
      job.setText(Job);
       company.setText(Company);
      phone.setText(Phone);
       mail.setText(Email);
       website.setText(Website);
       address.setText(Address);
        Picasso.with(this).load(Image).fit().centerInside().into(image);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EditActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},IMAGE_REQUEST_CODE);
                //ActivityCompat.requestPermissions(Addproduct.this,new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST);
                imageselect();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 upload();
            }
        });
    }
    public void imageselect()
    {
        final CharSequence[] items={"Camera", "Gallery","Cancle"};
        AlertDialog.Builder builder=new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera"))
                {
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAMERA_REQUEST);
                }
                else if(items[i].equals("Gallery"))
                {
                    Intent intent=new Intent(new Intent(Intent.ACTION_PICK));
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent,"select image"),IMAGE_REQUEST_CODE);

                }
                else if(items[i].equals("Cancle"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==CAMERA_REQUEST)
            {
                check = 0;
                Bundle bundle=data.getExtras();
                bitmap=(Bitmap)bundle.get("data");
                image.setImageBitmap(bitmap);
                check=1;

            }
            else if(requestCode==IMAGE_REQUEST_CODE)
            {
                check = 0;
                filepath=data.getData();
                try {
                    InputStream inputStream=getContentResolver().openInputStream(filepath);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    image.setImageBitmap(bitmap);
                    check=1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }
public void upload()
{
    final ProgressDialog progressDialog = new ProgressDialog(EditActivity.this);
    progressDialog.show();
    try {
        sendpost();
    }catch (IOException e) {

    }
}
    public void sendpost() throws IOException  {
        if (check == 0) {
            //final String p=password;


            // UpdatBody body=new UpdatBody(product_id,price);
            Call<UpdatBody> call = mAPIService.update(Id,name.getText().toString(),job.getText().toString(),company.getText().toString(),phone.getText().toString(),mail.getText().toString(),website.getText().toString(),address.getText().toString());

            call.enqueue(new Callback<UpdatBody>() {
                @Override
                public void onResponse(Call<UpdatBody> call, Response<UpdatBody> response) {

                    UpdatBody updateresponse = response.body();


                    Toast.makeText(EditActivity.this, updateresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MyProfile.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<UpdatBody> call, Throwable t) {


                    Toast.makeText(EditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } else if (check == 1) {
            check=0;
            String imgdata = imgToString(bitmap);

            // UpdatBody body=new UpdatBody(product_id,price);
            Call<UpdatBody> call = mAPIService1.editdata(Id,name.getText().toString(),job.getText().toString(),company.getText().toString(),phone.getText().toString(),mail.getText().toString(),website.getText().toString(),address.getText().toString(),imgdata);

            call.enqueue(new Callback<UpdatBody>() {
                @Override
                public void onResponse(Call<UpdatBody> call, Response<UpdatBody> response) {

                    UpdatBody updateresponse = response.body();


                    Toast.makeText(EditActivity.this, updateresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MyProfile.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<UpdatBody> call, Throwable t) {


                    Toast.makeText(EditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

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
            SessionManagment sessionManagment = new SessionManagment(EditActivity.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(EditActivity.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
