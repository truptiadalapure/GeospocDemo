package com.example.truptiadalapure.geospocdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {
    DatabaseHelper db;
    private List<Employee> employeeArrayList;
    EmployeeAdapter employeeAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);
        employeeArrayList = new ArrayList<>();
        employeeArrayList = db.getAllEmployees();

        employeeAdapter = new EmployeeAdapter(employeeArrayList);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(employeeAdapter);

    }





}
