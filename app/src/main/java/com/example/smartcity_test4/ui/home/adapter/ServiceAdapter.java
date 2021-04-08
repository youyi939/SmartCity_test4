package com.example.smartcity_test4.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.home.pojo.Item_service;

import java.util.List;



public class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item_service> list;
    private RecyclerView.ViewHolder holder;
    private int resourceId;

    public ServiceAdapter(List<Item_service> list,  int resourceId) {
        this.list = list;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        holder = new RecyclerView.ViewHolder(view) {};
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item_service test = list.get(position);
        TextView name = holder.itemView.findViewById(R.id.name_service);
        ImageView imageView = holder.itemView.findViewById(R.id.img_service);

        name.setText(test.getServiceName());
        if (test.getServiceName().equals("更多服务")){
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }else {
            Glide.with(holder.itemView.getContext()).load(test.getUrl()).into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
