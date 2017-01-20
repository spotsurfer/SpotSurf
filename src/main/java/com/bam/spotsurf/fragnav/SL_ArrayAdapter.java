package com.bam.spotsurf.fragnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bam.spotsurf.R;
import com.bam.spotsurf.objects.Spot;

import java.util.List;


/**
 * Created by bmerm on 8/7/2016.
 */
public class SL_ArrayAdapter extends ArrayAdapter<Spot> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public SL_ArrayAdapter(Context context, List<Spot> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.sl_row, parent, false);


            viewHolder.itemView = (TextView) convertView.findViewById(R.id.row_txt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Spot spot = getItem(position);
        if (spot!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(String.format("   %s, %s",spot.getAddress() , spot.getStartDate()));
        }

        return convertView;
    }
}
