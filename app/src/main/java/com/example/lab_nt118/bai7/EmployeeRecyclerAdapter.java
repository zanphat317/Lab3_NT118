package com.example.lab_nt118.bai7;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_nt118.R;

import java.util.List;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder> {

    private final Context context;
    private final List<Employee> employeeList;

    public EmployeeRecyclerAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee_bai7, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee emp = employeeList.get(position);
        if (emp != null) {
            holder.tvIdName.setText(emp.getId() + " - " + emp.getFullName());

            // Hiển thị icon Manager nếu là Manager, ngược lại hiện nhãn Staff
            if (emp.isManager()) {
                holder.imgManagerIcon.setVisibility(View.VISIBLE);
                holder.tvStaffLabel.setVisibility(View.GONE);
            } else {
                holder.imgManagerIcon.setVisibility(View.GONE);
                holder.tvStaffLabel.setVisibility(View.VISIBLE);
            }

            // Đổi màu nền chẵn lẻ xen kẽ trắng và xanh nhạt bằng position % 2
            if (position % 2 == 0) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")); // Trắng
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E3F2FD")); // Xanh nhạt
            }
        }
    }

    @Override
    public int getItemCount() {
        return employeeList != null ? employeeList.size() : 0;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdName;
        ImageView imgManagerIcon;
        TextView tvStaffLabel;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdName = itemView.findViewById(R.id.tv_id_name);
            imgManagerIcon = itemView.findViewById(R.id.img_manager_icon);
            tvStaffLabel = itemView.findViewById(R.id.tv_staff_label);
        }
    }
}