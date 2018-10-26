package com.example.truptiadalapure.geospocdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class EmployeeAdapter  extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

    private Context context;
    private List<Employee> employeeList;

    public EmployeeAdapter (List<Employee>list){

        this.employeeList = list;
    }



    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_adapter, parent, false);
        return new EmployeeAdapter.MyViewHolder(itemView);
        //return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Employee emp = employeeList.get(position);
        holder.txtName.setText(emp.getName());
        holder.txtPhone.setText(emp.getPhone());

        //Picasso.with(context).load(new File(emp.getImagePath())).into(holder.imageView);
        Picasso.get().load(emp.getImagePath()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtPhone;
        public TextView timestamp;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtPhone = view.findViewById(R.id.txtPhone);
            imageView = view.findViewById(R.id.imageView);
        }
    }

}
