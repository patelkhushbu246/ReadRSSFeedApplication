package com.example.readrssfeedapplication.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.readrssfeedapplication.Interface.ItemClickListener;
import com.example.readrssfeedapplication.Model.RSSObject;
import com.example.readrssfeedapplication.R;

import androidx.recyclerview.widget.RecyclerView;

 class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView titletxt,pubdatetxt,contenttxt;
    private ItemClickListener itemClickListener;
    public FeedViewHolder( View itemView) {
        super(itemView);
        titletxt=(TextView) itemView.findViewById(R.id.txt_title);
        pubdatetxt=(TextView) itemView.findViewById(R.id.txt_pubdate);
        contenttxt=(TextView) itemView.findViewById(R.id.txt_content);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
   private RSSObject rssObject;
   private Context mContext;
   private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater=LayoutInflater.from(mContext);


    }

    @Override
    public FeedViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View itemView=inflater.inflate(R.layout.row,parent,false);
       return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( FeedViewHolder holder, int position) {

        holder.titletxt.setText(rssObject.getItems().get(position).getTitle());
        holder.pubdatetxt.setText(rssObject.getItems().get(position).getPubDate());
        holder.contenttxt.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick){
                    Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
