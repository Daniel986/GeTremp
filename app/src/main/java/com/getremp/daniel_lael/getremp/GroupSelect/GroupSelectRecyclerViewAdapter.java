package com.getremp.daniel_lael.getremp.GroupSelect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getremp.daniel_lael.getremp.GroupSelectionActivity;
import com.getremp.daniel_lael.getremp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupSelectRecyclerViewAdapter extends RecyclerView.Adapter<GroupSelectRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "GroupSelectRecyclerView";
    private ArrayList<String> gsImageNames;
    private ArrayList<String> gsImages;
    private Context context;


    public GroupSelectRecyclerViewAdapter(ArrayList<String> gsImageNames, ArrayList<String> gsImages, Context context) {
        this.gsImageNames = gsImageNames;
        this.gsImages = gsImages;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group_select_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(!gsImages.get(position).isEmpty()) {
            if(!gsImages.get(position).equals("no image"))
                Glide.with(context).asBitmap().load(gsImages.get(position)).into(holder.groupImage);
            if (!gsImageNames.get(position).isEmpty())
                holder.groupName.setText(gsImageNames.get(position));
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GroupSelectionActivity groupSelectionActivity = (GroupSelectionActivity)context;
                    groupSelectionActivity.moveToTrempSelect(gsImageNames.get(position));
                    // TODO : request group's routine tremps

                    Log.d(TAG, "onClick: clicked on " + gsImageNames.get(position));
                    Toast.makeText(context, "Clicked on " + gsImageNames.get(position), Toast.LENGTH_SHORT).show();
                }
            });
            if(gsImageNames.indexOf(gsImageNames.get(position)) % 2 == 1)
            {
                Log.d(TAG, "PARITY 0 - ImageName: " + gsImageNames.get(position) + ", index: " + gsImageNames.indexOf(gsImageNames.get(position)) + ", position: " + position);
                holder.groupName.setTextColor(context.getResources().getColor(R.color.colorMedGrey));
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorLightOrange));
            }
            else if (gsImageNames.indexOf(gsImageNames.get(position)) % 2 == 0)
            {
                Log.d(TAG, "PARITY 1 - ImageName: " + gsImageNames.get(position) + ", index: " + gsImageNames.indexOf(gsImageNames.get(position)) + ", position: " + position);
                holder.groupName.setTextColor(context.getResources().getColor(R.color.colorLightOrange));
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorMedGrey));
            }
        }
    }

    @Override
    public int getItemCount() {
        return gsImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView groupImage;
        TextView groupName;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            groupImage = itemView.findViewById(R.id.gs_li_image);
            groupName = itemView.findViewById(R.id.gs_li_image_name);
            parentLayout = itemView.findViewById(R.id.gs_li_parent);

        }
    }
}
