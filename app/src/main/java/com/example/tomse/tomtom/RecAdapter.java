package com.example.tomse.tomtom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecHolder> {

    private List<Earthquake> mDataset;
    private Context context;

    public static class RecHolder extends RecyclerView.ViewHolder {
        public TextView magnitude, place, time, place_offset, day, date;

        public RecHolder(View view) {
            super(view);
            magnitude = view.findViewById(R.id.magnitude);
            place_offset = view.findViewById(R.id.place_offset);
            place = view.findViewById(R.id.place);
            time = view.findViewById(R.id.time);
            day = view.findViewById(R.id.day);
            date = view.findViewById(R.id.date);

        }
    }

    public RecAdapter(List<Earthquake> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecAdapter.RecHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View HolderView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        RecHolder recHolder = new RecHolder(HolderView);
        context = parent.getContext();
        return recHolder;
    }

    @Override
    public void onBindViewHolder(RecHolder holder, int position) {

        final Earthquake currentEarthquake = mDataset.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quakeUrl = currentEarthquake.getUrl();
                Intent quakeIntent = new Intent(Intent.ACTION_VIEW);
                quakeIntent.setData(Uri.parse(quakeUrl));
                startActivity(context, quakeIntent, Bundle.EMPTY);

            }
        });




        String mMag = Double.toString(currentEarthquake.getMagnitude());
        holder.magnitude.setText(mMag);
        GradientDrawable circle = (GradientDrawable) holder.magnitude.getBackground();
        int circleColor = getCircleColor(currentEarthquake.getMagnitude());
        circle.setColor(circleColor);


        String rawLocation = currentEarthquake.getLocation();
        String locationOne;
        String locationTwo;
        if (rawLocation.contains("of")) {
            String[] splitLocation = rawLocation.split("(?<=of)");
            locationOne = splitLocation[0];
            locationTwo = splitLocation[1];
        } else {
            locationOne = context.getString(R.string.near_by);
            locationTwo = rawLocation;
        }
        holder.place_offset.setText(locationOne);
        holder.place.setText(locationTwo);


        Date date = new Date(currentEarthquake.getTimeInMilliseconds());

        String time = new SimpleDateFormat("HH:mm").format(date);
        holder.time.setText(time);

        String day = new SimpleDateFormat("EEEE").format(date);
        holder.day.setText(day);

        String year = new SimpleDateFormat("dd.MM.yyyy").format(date);
        holder.date.setText(year);



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private int getCircleColor (double _magnitude) {
        int circleColor = 0;
        if (_magnitude > 1) {
            circleColor = R.color.magnitude_1;
        }

        if (_magnitude > 5) {
            circleColor = R.color.magnitude_5;
        }

        if (_magnitude > 6) {
            circleColor = R.color.magnitude_6;
        }

        if (_magnitude > 7) {
            circleColor = R.color.magnitude_7;
        }


        return ContextCompat.getColor(context, circleColor);
    }


}
