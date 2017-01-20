package com.bam.spotsurf.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bam.spotsurf.R;

/**
 * Created by bmerm on 10/27/2016.
 */
public class SpotViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView address;
    TextView status;
    ImageView imageView;

    public SpotViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        address = (TextView) itemView.findViewById(R.id.address);
        status = (TextView) itemView.findViewById(R.id.status);
        //imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}