package com.bam.spotsurf.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.SpotCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by bmerm on 10/27/2016.
 */
public class SpotViewAdapter extends RecyclerView.Adapter<SpotViewHolder> {

    List<SpotCard> list = Collections.emptyList();
    Context context;

    public SpotViewAdapter (List<SpotCard> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_cardview, parent, false);
        SpotViewHolder holder = new SpotViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(SpotViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.address.setText(list.get(position).getAddress());
        holder.status.setText(list.get(position).getStatus());
        //holder.imageView.setImageResource(list.get(position).getImageId());

        //animate(holder);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, SpotCard data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(SpotCard data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

}

