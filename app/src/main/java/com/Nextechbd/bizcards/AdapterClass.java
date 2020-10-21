package com.Nextechbd.bizcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ExampleViewholder> {
    private List<Show_element> examplelist;
    private ArrayList<Show_element> data=new ArrayList();
    private Context context;
    private OnItemClickListener mListener;
    public AdapterClass(Context context,List<Show_element> examplelist) {
        this.examplelist = examplelist;
        this.context=context;

    }

    public AdapterClass()
    {

    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnClickListener (OnItemClickListener listener){
        mListener=listener;
    }

    public void name()
    {

    }



    @Override
    //public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.details,parent,false);
    // Viewholder vh=new Viewholder(v);
    // return vh;

    //}
    public AdapterClass.ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.details,parent,false);
        ExampleViewholder vh=new ExampleViewholder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewholder holder, int position) {
        Show_element currentItem=examplelist.get(position);
        Picasso.with(context)
                .load(currentItem.getCard_image())
                .fit()
                .centerCrop()
                .into(holder.image);
String details=currentItem.getCard_details();
String summery="";
for(int i=0;i<details.length();i++)
{
    if(details.charAt(i)=='\n')
    {
        break;
    }
    summery=summery+details.charAt(i);
}
holder.title.setText(summery);
       // holder.details.setText(currentItem.getCard_details());

        // data.add(new Show_element(currentItem.getProduct_price(),currentItem.getProduct_type(),currentItem.getProduct_image()));


    }

    @Override
    public int getItemCount() {
        return examplelist.size();
    }
    public  class ExampleViewholder extends RecyclerView.ViewHolder{


        public TextView title;
        public ImageView image;


        public ExampleViewholder( View itemView) {
            super(itemView);


          // details=itemView.findViewById(R.id.card_details);
            image=itemView.findViewById(R.id.image);
            title=(itemView).findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }

    }
}


