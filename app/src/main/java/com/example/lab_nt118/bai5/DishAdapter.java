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

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    private final Context context;
    private final int resource;
    private final List<Dish> objects;

    public DishAdapter(@NonNull Context context, int resource, @NonNull List<Dish> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imgDish = convertView.findViewById(R.id.img_dish);
            holder.imgStar = convertView.findViewById(R.id.img_star);
            holder.tvDishName = convertView.findViewById(R.id.tv_dish_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dish dish = objects.get(position);
        if (dish != null) {
            // Đặt hình đại diện
            if (dish.getThumbnail() != null) {
                holder.imgDish.setImageResource(dish.getThumbnail().getImageResId());
            }

            // Đặt tên món ăn
            holder.tvDishName.setText(dish.getName());

            // Kích hoạt hiệu ứng Marquee (cuộn chữ) cho TextView tên món ăn
            holder.tvDishName.setSelected(true);

            // Kiểm tra xem có Khuyến mãi hay không để hiển thị Icon Ngôi Sao
            if (dish.isPromotion()) {
                holder.imgStar.setVisibility(View.VISIBLE);
            } else {
                holder.imgStar.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgDish;
        ImageView imgStar;
        TextView tvDishName;
    }
}