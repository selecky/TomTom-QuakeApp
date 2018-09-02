package com.example.tomse.tomtom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class QuakeAdapter extends ArrayAdapter<Earthquake> {

    public QuakeAdapter(Context context, List<Earthquake> items) {
        super(context, 0, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeView = convertView.findViewById(R.id.magnitude);
        String mMag = Double.toString(currentEarthquake.getMagnitude());
        magnitudeView.setText(mMag);

        TextView placeView = convertView.findViewById(R.id.place);
        placeView.setText(currentEarthquake.getLocation());

        TextView timeView = convertView.findViewById(R.id.time);
        String mTime = Long.toString(currentEarthquake.getTimeInMilliseconds());
        timeView.setText(mTime);

        return convertView;
    }

}