package com.example.lab_nt118.bai6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_nt118.R;

import java.util.List;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> {

    private final Context context;
    private final List<Hero> heroList;

    public HeroAdapter(Context context, List<Hero> heroList) {
        this.context = context;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Bơm (inflate) layout cho từng dòng item_hero
        View view = LayoutInflater.from(context).inflate(R.layout.item_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Cập nhật dữ liệu vào ViewHolder tại vị trí position
        Hero hero = heroList.get(position);
        if (hero != null) {
            holder.tvHeroName.setText(hero.getName());
            holder.imgHeroAvatar.setImageResource(hero.getImageResId());
        }
    }

    @Override
    public int getItemCount() {
        return heroList != null ? heroList.size() : 0;
    }

    // Class ViewHolder để lưu trữ và tái sử dụng các View thành phần trong mỗi item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHeroAvatar;
        TextView tvHeroName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHeroAvatar = itemView.findViewById(R.id.img_hero_avatar);
            tvHeroName = itemView.findViewById(R.id.tv_hero_name);
        }
    }
}