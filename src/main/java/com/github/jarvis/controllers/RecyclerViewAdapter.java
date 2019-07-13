package com.github.jarvis.controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.jarvis.ImageDataModel;
import com.github.jarvis.dynamicformbuilder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivendrakumar on 13/10/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ImageDataModel> mValues;
    private Context mContext;
    private ItemListener mListener;
    private ArrayList<ImageDataModel> arrayList;

    RecyclerViewAdapter(Context context, List<ImageDataModel> values, ItemListener itemListener) {

        mValues = values;
        this.mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        RelativeLayout relativeLayout;
        ImageDataModel item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            relativeLayout = v.findViewById(R.id.relativeLayout);

        }

        void setData(ImageDataModel item) {
            this.item = item;

            textView.setText(item.text);
            if(item.drawable!=null && !item.drawable.isEmpty()) {
                Glide
                        .with(imageView.getContext())
                        .load(item.drawable)
                        .into(imageView);
            } else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cameraicon));
            }
            if(item.color!=null && !item.color.isEmpty()) {
                relativeLayout.setBackgroundColor(Color.parseColor(item.color));
            }

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), item);
            }
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (inflater != null) {
            view = inflater.inflate(R.layout.recycler_view_item, null);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(int position, ImageDataModel item);
    }


}
