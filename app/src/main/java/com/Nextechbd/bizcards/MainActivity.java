package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.Nextechbd.bizcards.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=400;
    private static final int IMAGE_PICK_STORAGE_CODE=1000;
    private static final int IMAGE_PICK_CAMERA_CODE=1001;
    String [] cameraPermission;
    String [] storagePermission;
    EditText mResultEt,mEmailEt,mEmailEt1,mEmailEt2,phone,phone1,phone2;
    TextView mail1,mail2,pne1,pne2,overview;
    public String details,Phone1,Phone2,Email1,Email2,id;
    ImageView mPreviewIv;
    Uri image_uri;
   public Bitmap bitmap;
   public int check=0;
    private Saveapi mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

mail1=(TextView)findViewById(R.id.mail1);
        mail2=(TextView)findViewById(R.id.mail2);
        pne1=(TextView)findViewById(R.id.pne1);
        pne2=(TextView)findViewById(R.id.pne2);
        overview=(TextView)findViewById(R.id.overview);
        mail1.setVisibility(View.INVISIBLE);
        mail2.setVisibility(View.INVISIBLE);
        pne1.setVisibility(View.INVISIBLE);
        pne2.setVisibility(View.INVISIBLE);
        overview.setVisibility(View.INVISIBLE);
        //actionBar.setSubtitle("Click + Button insert Image");
        mResultEt=(EditText)findViewById(R.id.resultEt);
        mResultEt.setVisibility(View.INVISIBLE);
        mPreviewIv=(ImageView)findViewById(R.id.imageIv);
        mPreviewIv.setVisibility(View.INVISIBLE);
        mEmailEt=(EditText)findViewById(R.id.emailtEt);
        mEmailEt.setVisibility(View.INVISIBLE);
        mEmailEt1=(EditText)findViewById(R.id.emailtEt1);
        mEmailEt1.setVisibility(View.INVISIBLE);
        phone=(EditText)findViewById(R.id.phone);
        phone.setVisibility(View.INVISIBLE);
        phone1=(EditText)findViewById(R.id.phone1);
        phone1.setVisibility(View.INVISIBLE);
        mAPIService= AppConfig.getAPIService10();
        cameraPermission=new String[]{Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ShowImageImportDialouge();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            SessionManagment sessionManagment = new SessionManagment(MainActivity.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }
        if(id==R.id.save)
        {
            savecard();
        }
        if(id==R.id.back)
        {
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowImageImportDialouge() {



        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if(!checkCameraPermission())
                    {
                        requestCameraPermission();
                    }else{
                        pickCamera();
                    }
                }
               if(which==1){
                   if(!checkStoragePermission())
                   {
                       requestStoragePermission();
                   }else{
                       pickGallery();
                   }
               }
            }
        });
        dialog.create().show();
    }
    private void pickGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);

        startActivityForResult(intent,IMAGE_PICK_STORAGE_CODE);

    }
    private void pickCamera(){
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"newPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Images to Text");
         image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

         Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
         startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);

    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermission,STORAGE_REQUEST_CODE);
    }
    private boolean checkStoragePermission(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return  result;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermission,CAMERA_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==
                (PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean CameraAccepted=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    boolean WriteStorageAccepted=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    if(CameraAccepted && WriteStorageAccepted){
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this,"permission Denaid",Toast.LENGTH_LONG).show();
                    }



        }
                break;
            case  STORAGE_REQUEST_CODE:
                if(grantResults.length>0){

                    boolean WriteStorageAccepted=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    if( WriteStorageAccepted){
                        pickGallery();
                    }
                    else {
                        Toast.makeText(this,"permission Denaid",Toast.LENGTH_LONG).show();
                    }
    }
}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_STORAGE_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
           else if(requestCode==IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri=result.getUri();
                mPreviewIv.setVisibility(View.VISIBLE);
                mPreviewIv.setImageURI(resultUri);
                check=1;

                BitmapDrawable bitmapDrawable=(BitmapDrawable)mPreviewIv.getDrawable();
                 bitmap=bitmapDrawable.getBitmap();
                TextRecognizer recognizer=new TextRecognizer.Builder(getApplicationContext()).build();
                if(!recognizer.isOperational()){
                    Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
                }
           else{
                Frame frame=new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items=recognizer.detect(frame);
                    StringBuilder sb=new StringBuilder();
                    for(int i=0;i<items.size();i++){
                        TextBlock myItem=items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }


                    mResultEt.setText("");
                    mEmailEt.setText("");
                    mEmailEt1.setText("");
                    phone.setText("");
                    phone1.setText("");
                    String s = sb.toString();


                    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+?.[a-zA-Z0-9-.]+").matcher(s);
                    //String pattern = "/(^(\\+88|0088)?(01){1}[3456789]{1}(\\d){8})$/";
                    String pattern = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
                    Matcher n=Pattern.compile("(\\d+)[0-9-( )]( |-)([0-9-( )]+)").matcher(s);
                    Pattern r = Pattern.compile(pattern);
                    String[] lines = s.split("\\n");
                    String xyz="";

                    for (String line : lines) {
                        int l=line.length();
                        int check=0;
                        //line.replaceAll("'", "");
                            Boolean flag = Character.isDigit(line.charAt(l-3))||Character.isDigit(line.charAt(l-4));
                            if (flag) {
                              continue;
                            }
                          for(int i=l-1;i>=0;i--)
                          {
                              if(line.charAt(i)=='@')
                              {check=1;
                                  break;
                              }

                               //line.charAt(i)='b';


                          }
                          if(check==1)
                          {
                              continue;
                          }else{
                              line=line.replaceAll("\\p{Punct}","_");
                          xyz=xyz+line+System.lineSeparator();}

                    }

                    overview.setVisibility(View.VISIBLE);
                    mResultEt.setVisibility(View.VISIBLE);
                    mResultEt.setText(xyz);
                    //n = r.matcher(sb);
                if(m.find()){
                    mail1.setVisibility(View.VISIBLE);

                    mEmailEt.setVisibility(View.VISIBLE);
                    mEmailEt.setText(m.group());
                }
                if(m.find()){
                    mail2.setVisibility(View.VISIBLE);

                    mEmailEt1.setVisibility(View.VISIBLE);
                    mEmailEt1.setText(m.group());
                    }

               if(n.find())
               {   pne1.setVisibility(View.VISIBLE);

                   phone.setVisibility(View.VISIBLE);
                   phone.setText(n.group());}
                    if(n.find())
                    {  pne2.setVisibility(View.VISIBLE);
                        phone1.setVisibility(View.VISIBLE);
                        phone1.setText(n.group());}



            } }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
                Toast.makeText(this,""+error,Toast.LENGTH_LONG).show();
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

    private void savecard() {
        if (check != 1) {
            Toast.makeText(MainActivity.this, "scan card  ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        } else if (check == 1) {
            check = 0;

        String imgdata = imgToString(bitmap);
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Please wait");
progressDialog.show();

        //name.getText().toString()
        SessionManagment sessionManagment = new SessionManagment(MainActivity.this);
        int userId = sessionManagment.getSession();
        id = String.valueOf(userId);
        details = mResultEt.getText().toString();

        Phone1 = phone.getText().toString();
        Phone2 = phone1.getText().toString();
        Email1 = mEmailEt.getText().toString();
        Email2 = mEmailEt1.getText().toString();
        Call<Recordresponse> call = mAPIService.getStringScalar(imgdata, details, Phone1, Phone2, Email1, Email2, id);

        call.enqueue(new Callback<Recordresponse>() {
            @Override
            public void onResponse(Call<Recordresponse> call, Response<Recordresponse> response) {
                Recordresponse record = response.body();
progressDialog.dismiss();
                Toast.makeText(MainActivity.this, record.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Recordresponse> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(MainActivity.this, Email2, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
    }
    }


}