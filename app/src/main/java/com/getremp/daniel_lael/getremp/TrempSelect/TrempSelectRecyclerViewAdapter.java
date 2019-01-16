package com.getremp.daniel_lael.getremp.TrempSelect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.TrempSelectionActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrempSelectRecyclerViewAdapter extends RecyclerView.Adapter<TrempSelectRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "TrempSelectRecyclerView";
    Context context;
    ArrayList<RegularTremp> tremps;

    public TrempSelectRecyclerViewAdapter(ArrayList<RegularTremp> tremps, Context context) {
        this.context = context;
        this.tremps = tremps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tremp_select_regular_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(position < getItemCount())
        {
            if(tremps.get(position) != null) {
                // TODO : return when capacity is larger
                //if (!tremps.get(position).getDriverImage().equals("no image")) {
                 //   Glide.with(context).asBitmap().load(tremps.get(position).getDriverImage()).into(holder.driverImage);
                //}
                holder.driver.setText(tremps.get(position).getDriver());
                holder.destination.setText(tremps.get(position).getDestination());
                holder.origin.setText(tremps.get(position).getOrigin());
                holder.day.setText(context.getString(R.string.ts_day) + " " + tremps.get(position).getDay());
                holder.hour.setText(tremps.get(position).getHour());

                holder.num_of_seats.setText(tremps.get(position).getOccupiedSeats() + "/" + tremps.get(position).getTotalSeats());

                holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TrempSelectionActivity trempSelectionActivity = (TrempSelectionActivity) context;
                        //groupSelectionActivity.sendUserToServer(gsImageNames.get(position));

                        Log.d(TAG, "onClick: clicked on " + tremps.get(position).getDriver());
                        Toast.makeText(context, "Clicked on " + tremps.get(position).getDriver(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return tremps.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView driverImage;
        TextView driver, destination, origin, day, hour, num_of_seats;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            driverImage = itemView.findViewById(R.id.ts_li_image);
            driver = itemView.findViewById(R.id.ts_li_driver_name);
            destination = itemView.findViewById(R.id.ts_li_dest);
            origin = itemView.findViewById(R.id.ts_li_origin);
            day = itemView.findViewById(R.id.ts_li_day);
            hour = itemView.findViewById(R.id.ts_li_hour);
            num_of_seats = itemView.findViewById(R.id.ts_li_num_of_seats);

            parentLayout = itemView.findViewById(R.id.ts_li_parent);

        }
    }
}
