package com.example.lab_nt118.bai4;

import android.content.Context;
import android.graphics.Color;
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

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    private final Context context;
    private final int resource;
    private final List<Employee> objects;

    public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        // Tái sử dụng convertView để tối ưu hiệu năng
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tvIdName = convertView.findViewById(R.id.tv_id_name);
            holder.imgManagerIcon = convertView.findViewById(R.id.img_manager_icon);
            holder.tvStaffLabel = convertView.findViewById(R.id.tv_staff_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Employee emp = objects.get(position);
        if (emp != null) {
            holder.tvIdName.setText(emp.getId() + " - " + emp.getFullName());

            // Kiểm tra nhân viên có phải là Manager hay không
            if (emp.isManager()) {
                // Hiển thị icon Manager, ẩn chữ Staff
                holder.imgManagerIcon.setVisibility(View.VISIBLE);
                holder.tvStaffLabel.setVisibility(View.GONE);
            } else {
                // Hiển thị chữ Staff, ẩn icon Manager
                holder.imgManagerIcon.setVisibility(View.GONE);
                holder.tvStaffLabel.setVisibility(View.VISIBLE);
            }

            // Đổi màu nền xen kẽ giữa các dòng sử dụng position % 2
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#FFFFFF")); // Trắng
            } else {
                convertView.setBackgroundColor(Color.parseColor("#E3F2FD")); // Xanh nhạt
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvIdName;
        ImageView imgManagerIcon;
        TextView tvStaffLabel;
    }
}