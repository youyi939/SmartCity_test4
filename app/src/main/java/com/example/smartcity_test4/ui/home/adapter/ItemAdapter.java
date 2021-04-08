package com.example.smartcity_test4.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.home.pojo.ItemData;

import java.util.List;


public class ItemAdapter extends ArrayAdapter<ItemData> {
    private int resourceId;


    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<ItemData> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }
        ItemData itemData = getItem(position);
        TextView title = convertView.findViewById(R.id.title_data);
        TextView content = convertView.findViewById(R.id.content_data);
        TextView time = convertView.findViewById(R.id.createTime_data);
        TextView num = convertView.findViewById(R.id.viewNum_data);
        ImageView imageView = convertView.findViewById(R.id.img_data);

        title.setText(itemData.getTitle());
        content.setText(itemData.getContent());
        time.setText(itemData.getCreateTime());
        num.setText(String.valueOf(itemData.getViewNumber()));


        Glide.with(getContext()).load(itemData.getUrl()).into(imageView);

        return convertView;
    }
}

