package com.Nextechbd.bizcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

import com.github.chrisbanes.photoview.PhotoView;
import com.Nextechbd.bizcards.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.Nextechbd.bizcards.Showdetails.Card_Id;
import static com.Nextechbd.bizcards.Showdetails.Card_Details;
import static com.Nextechbd.bizcards.Showdetails.EMAIL1;
import static com.Nextechbd.bizcards.Showdetails.EMAIL2;
import static com.Nextechbd.bizcards.Showdetails.PHONE1;
import static com.Nextechbd.bizcards.Showdetails.PHONE2;
import static com.Nextechbd.bizcards.Showdetails.image;
public class descriptionActivity extends AppCompatActivity {
    TextView Details,Email1,Email2,Phone1,Phone2,mail1,mail2,pne1,pne2;
    ImageButton call1,call2,msg1,msg2;
    PhotoView imageView;
    Button edit,delete;
    Deleteapi mAPIService;
    public static final String number = "number";

    public static final String Location=null;
    public static final String PRODUCT_ID="id";
    public static final String PRODUCT_PHONE="phone";
    public static final String PRODUCT_DESCRIPTION="description";
    public static final String IMAGE="image";
    String product_location;
    public  String card_id,details,email1,email2,phone1,phone2,Image;
    private ScaleGestureDetector scaleGestureDetector;
static Bitmap bm;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
       // mAPIService=Apiutlize.getAPIService6();
call1=(ImageButton)findViewById(R.id.call1);
        call2=(ImageButton)findViewById(R.id.call2);
        msg1=(ImageButton)findViewById(R.id.msg1);
        msg2=(ImageButton)findViewById(R.id.msg2);
imageView=(PhotoView) findViewById(R.id.image);
mAPIService=AppConfig.getAPIService17();

call1.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
if(!(phone1.isEmpty()))
{
    String contact="";
    for(int i=0;i<phone1.length();i++)
    {
        if(phone1.charAt(i)==' ')
        {
            continue;
        }else
        {contact+=phone1.charAt(i);

        }

    }
    if(contact.charAt(0)=='8' && contact.charAt(1)=='8')
    {
        contact='+'+contact;
    }
    String dial = "tel:" + contact;
    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
}

// Do something in response to button click
    }
});
call2.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
         if (!(phone2.isEmpty()))
        {
            String contact="";
            for(int i=0;i<phone2.length();i++)
            {
                if(phone1.charAt(i)==' ')
                {
                    continue;
                }else
                {contact+=phone2.charAt(i);

                }

            }
            if(contact.charAt(0)=='8' && contact.charAt(1)=='8')
            {
                contact='+'+contact;
            }
            String dial = "tel:" + contact;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        }
// Do something in response to button click
    }
});

msg1.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        if(!(phone1.isEmpty()))
        {
            String contact="";
            for(int i=0;i<phone1.length();i++)
            {
                if(phone1.charAt(i)==' ')
                {
                    continue;
                }else
                {contact+=phone1.charAt(i);

                }

            }
            if(contact.charAt(0)=='8' && contact.charAt(1)=='8')
            {
                contact='+'+contact;
            }
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);

            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address"  ,contact);
            smsIntent.putExtra("sms_body"  , "");

            try {
                startActivity(smsIntent);
                finish();
                // Log.i("Finished sending SMS...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(descriptionActivity.this,
                        "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
            }

           // Intent intent = new Intent(descriptionActivity.this,SMSActivity.class);

           // intent.putExtra(number,contact);
            //startActivity(intent);
        }

// Do something in response to button click
    }
});
msg2.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        if(!(phone1.isEmpty()))
        {
            String contact="";
            for(int i=0;i<phone1.length();i++)
            {
                if(phone1.charAt(i)==' ')
                {
                    continue;
                }else
                {contact+=phone1.charAt(i);

                }

            }
            if(contact.charAt(0)=='8' && contact.charAt(1)=='8')
            {
                contact='+'+contact;
            }
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);

            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address"  ,contact);
            smsIntent.putExtra("sms_body"  , "");

            try {
                startActivity(smsIntent);
                finish();
                // Log.i("Finished sending SMS...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(descriptionActivity.this,
                        "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
            }
           // Intent intent = new Intent(descriptionActivity.this,SMSActivity.class);

           // intent.putExtra(number,contact);
           // startActivity(intent);
        }

// Do something in response to button click
    }
});


        card_id=intent.getStringExtra(Card_Id);
        details = intent.getStringExtra(Card_Details);
        email1=intent.getStringExtra(EMAIL1);
        email2=intent.getStringExtra(EMAIL2);
        phone1=intent.getStringExtra(PHONE1);
        phone2=intent.getStringExtra(PHONE2);
Image=intent.getStringExtra(image);
        Details=(TextView)findViewById(R.id.details);
        Email1=(TextView)findViewById(R.id.email1);
        Email2=(TextView)findViewById(R.id.email2);
        Phone1=(TextView)findViewById(R.id.phone1);
        Phone2=(TextView)findViewById(R.id.phone2);
        mail1=(TextView)findViewById(R.id.mail1);
        mail2=(TextView)findViewById(R.id.mail2);
        pne1=(TextView)findViewById(R.id.pne1);
        pne2=(TextView)findViewById(R.id.pne2);

        //edit=(Button)findViewById(R.id.buttonedit);
        //delete=(Button)findViewById(R.id.buttondlt);
        if(email1.isEmpty())
        {
mail1.setVisibility(View.INVISIBLE);
        }
        if(email2.isEmpty())
        {
            mail2.setVisibility(View.INVISIBLE);
        }
        if(phone1.isEmpty())
        {
            pne1.setVisibility(View.INVISIBLE);
            call1.setVisibility(View.INVISIBLE);
            msg1.setVisibility(View.INVISIBLE);
        }
        if(phone2.isEmpty())
        {
            pne2.setVisibility(View.INVISIBLE);
            call2.setVisibility(View.INVISIBLE);
            msg2.setVisibility(View.INVISIBLE);
        }
         Details.setText(details);
         Email1.setText(email1);
         Email2.setText(email2);
         Phone1.setText(phone1);
         Phone2.setText(phone2);


         Picasso.with(this).load(Image).fit().centerInside().into(imageView);
        /*scaleGestureDetector = new ScaleGestureDetector(this,
                new MySimpleOnScaleGestureListener());
        imageView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), "clicked image", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "clicked image", Toast.LENGTH_SHORT).show();
            }
        });*/

       // scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
       // Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        //scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(descriptionActivity.this, EditActivity.class);


                //Show_element currentItem=examplelist.get(position);

                intent.putExtra(PRICE,product_price);
                //intent.putExtra(PRODUCT_ID,id);
                intent.putExtra("mes",id);
                intent.putExtra(PRODUCT_PHONE,product_phone);
                intent.putExtra(PRODUCT_DESCRIPTION,product_description);
                intent.putExtra(IMAGE,product_image);
                //intent.putExtra(EXTRA_CREATOR,clickItem.getProduct_price());
                // detailIntent.putExtra(EXTRA_LIKES,clickItem.getmLikes());
                // detailIntent.putExtra("imageUrlAll", Images);
                // startActivity(detailIntent);
                startActivity(intent);
            }
        });*/
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sendPost(id);
                } catch (IOException e) {

                }
            }
        });*/


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar2, menu);
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
            SessionManagment sessionManagment = new SessionManagment(descriptionActivity.this);
            sessionManagment.removeSession();
            Intent intent = new Intent(descriptionActivity.this, Login.class);
            startActivity(intent);
            //Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

        }

        if(id==R.id.back)
        {
            Intent intent = new Intent(descriptionActivity.this,Showdetails.class);
            startActivity(intent);
        }
        if(id==R.id.delete)
        {
            final CharSequence[] items={"Delete","Cancle"};
            AlertDialog.Builder builder=new AlertDialog.Builder(descriptionActivity.this);
            builder.setTitle("Are You Delete Card????");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if(items[i].equals("Delete"))
                    {
                        //Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(intent,CAMERA_REQUEST);
                        try {
                            sendPost();
                        } catch (IOException e) {

                        }
                    }

                    else if(items[i].equals("Cancle"))
                    {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event) || handled;
    }
    public class MySimpleOnScaleGestureListener extends
            SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub

            float scaleFactor = detector.getScaleFactor();
            if(scaleFactor > 1){
                Log.v("inside scale factor if","if");
               // textGestureAction.setText("Scale Out: " + String.valueOf(scaleFactor));

                Bitmap resizedbitmap = Bitmap.createScaledBitmap(bm, 200,
                        480, true);
                imageView.setImageBitmap(resizedbitmap);


            }else{
               // Log.v("inside scale factor else","else");
                //textGestureAction.setText("Scale In: " + String.valueOf(scaleFactor));


                Bitmap resizedbitmap = Bitmap.createScaledBitmap(bm, 400,
                        480, true);

                imageView.setImageBitmap(resizedbitmap);



            }

            return true;
        }
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
   public void sendPost() throws IOException {

        Call<Deleteresponse> call = mAPIService.delete(card_id,Image);

        call.enqueue(new Callback<Deleteresponse>() {
            @Override
            public void onResponse(Call<Deleteresponse> call, Response<Deleteresponse> response) {
                Deleteresponse deleteresponse=response.body();

                // String responsemsg=deleteresponse.getMessage();


                Toast.makeText(descriptionActivity.this, deleteresponse.getMessage(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(descriptionActivity.this, Showdetails.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Deleteresponse> call, Throwable t) {
                Toast.makeText(descriptionActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });




    }
}

