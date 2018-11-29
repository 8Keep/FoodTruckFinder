package com.example.baohong.poop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainPgViewAdapter extends RecyclerView.Adapter<MainPgViewAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;
    int width , height;

    public MainPgViewAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MainPgViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_listitem, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainPgViewAdapter.ViewHolder viewHolder, int i) {
        ListItem listItem = listItems.get(i);

        viewHolder.cardView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewHolder.cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = viewHolder.cardView.getWidth();
            }
        });

        viewHolder.heading.setText(listItem.getHead());
        viewHolder.desc.setText(listItem.getDesc());
        if(!listItem.getImgURL().isEmpty())
            Picasso.get()
                    .load(listItem.getImgURL())
                    .placeholder(R.drawable.progress_anim)
                    .resize(width, 550)
                    .centerCrop()
                    .into(viewHolder.imageView);
        else
            Picasso.get()
                    .load("http://192.168.0.17/images/default.png")
                    .into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView heading, desc;
        public ImageView imageView;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.heading);
            desc = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
