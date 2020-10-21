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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Nextechbd.bizcards.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatProfile extends AppCompatActivity {
    Button add, upload;
    EditText name, job_title, company, phone, email, website, address;
    public String Name,Phone,jobtitle,Company,Email,Website,Address,user_id,imgdata;
    ImageView img;
    private creatapi mAPIService;
    private Checkapi mAPIService1;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;

    final int IMAGE_REQUEST_CODE = 999;
    private ProgressBar progressBar;
    private Uri filepath;
    private Bitmap bitmap;
    public int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_profile);

        add = (Button) findViewById(R.id.ubuttonadd);
        upload = (Button) findViewById(R.id.ubuttonupload);
        name = (EditText) findViewById(R.id.name);
        job_title = (EditText) findViewById(R.id.jobtitle);
        company = (EditText) findViewById(R.id.company);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        website = (EditText) findViewById(R.id.website);
        address = (EditText) findViewById(R.id.address);
        img = (ImageView) findViewById(R.id.image);
        mAPIService = AppConfig.getAPIService11();
        mAPIService1 = AppConfig.getAPIService12();
        add.setOnClickListener(new View.OnClickListener() {

            // @Override
            public void onClick(View v) {


                //check=1;
                ActivityCompat.requestPermissions(CreatProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                //ActivityCompat.requestPermissions(Addproduct.this,new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST);
                imageselect();
            }

            // Toast.makeText(Login.this, "yes", Toast.LENGTH_SHORT).show();
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
    }

    public void imageselect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancle"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatProfile.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancle")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                img.setImageBitmap(bitmap);
                check = 1;

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    img.setImageBitmap(bitmap);
                    check = 1;
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
    private void uploadImage() {
        if (check == 0) {
            Toast.makeText(this, "select image ", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(CreatProfile.this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        SessionManagment sessionManagment = new SessionManagment(CreatProfile.this);
        user_id = String.valueOf(sessionManagment.getSession());

        if (name.getText().toString().isEmpty()) {
            name.setError("enter name");
          name.requestFocus();
            return;
        }
        if (phone.getText().toString().isEmpty()) {
            phone.setError("enter office phone");
            phone.requestFocus();
            return;
        }

        if (job_title.getText().toString().isEmpty()) {
            job_title.setError("enter job title");
            job_title.requestFocus();
            return;
        }



        Call<Verifyresponse> call = mAPIService1.getStringScalar(user_id );
        call.enqueue(new Callback<Verifyresponse>() {
            @Override
            public void onResponse(Call<Verifyresponse> call, Response<Verifyresponse> response) {
                Verifyresponse verifyresponse=response.body();
                String msg="no";
                String responsemsg=verifyresponse.getMessage();
                if(responsemsg.equals(msg)) {
progressDialog.dismiss();
                    try {
                        sendPost();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Login.this, loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(CreatProfile.this, "Already have card", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreatProfile.this, FirstActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Verifyresponse> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(CreatProfile.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });

 }
    public void sendPost() throws IOException {
       String imgdata = imgToString(bitmap);
        final ProgressDialog progressDialog = new ProgressDialog(CreatProfile.this);
        progressDialog.setTitle("Please wait");
progressDialog.show();
        SessionManagment sessionManagment = new SessionManagment(CreatProfile.this);
        String user_Id = String.valueOf(sessionManagment.getSession());
        Call<Recordresponse> call = mAPIService.getStringScalar(imgdata,name.getText().toString(),job_title.getText().toString(),company.getText().toString(),phone.getText().toString(),
                email.getText().toString(),website.getText().toString(),address.getText().toString(),user_Id);
check=0;
        call.enqueue(new Callback<Recordresponse>() {
            @Override
            public void onResponse(Call<Recordresponse> call, Response<Recordresponse> response) {
                Recordresponse recordresponse=response.body();
                String responsemsg=recordresponse.getMessage();


                progressDialog.dismiss();
                Toast.makeText(CreatProfile.this, responsemsg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatProfile.this, FirstActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Recordresponse> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(CreatProfile.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatProfile.this, FirstActivity.class);
                startActivity(intent);

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
            SessionManagment sessionManagment = new SessionManagment(CreatProfile.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(CreatProfile.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


}
