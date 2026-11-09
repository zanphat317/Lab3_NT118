package com.example.lab_nt118.bai5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_nt118.R;

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {

    private final Context context;
    private final Thumbnail[] thumbnails;

    public ThumbnailAdapter(@NonNull Context context, @NonNull Thumbnail[] objects) {
        super(context, 0, objects);
        this.context = context;
        this.thumbnails = objects;
    }

    // Hiển thị trên Spinner khi đã chọn (chỉ hiện Tên)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_selected_thumbnail, parent, false);
        }

        TextView tvSelectedName = convertView.findViewById(R.id.tv_selected_name);
        Thumbnail thumb = getItem(position);
        if (thumb != null) {
            tvSelectedName.setText(thumb.getName());
        }

        return convertView;
    }

    // Hiển thị trong Dialog Spinner khi chọn (hiện cả Hình + Tên)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thumbnail, parent, false);
        }

        ImageView imgThumb = convertView.findViewById(R.id.img_thumb);
        TextView tvThumbName = convertView.findViewById(R.id.tv_thumb_name);

        Thumbnail thumb = getItem(position);
        if (thumb != null) {
            imgThumb.setImageResource(thumb.getImageResId());
            tvThumbName.setText(thumb.getName());
        }

        return convertView;
    }
}