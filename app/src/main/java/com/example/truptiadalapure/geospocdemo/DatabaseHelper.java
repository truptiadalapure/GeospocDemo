package com.example.truptiadalapure.geospocdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "employee_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Employee.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Employee.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public long insertEmployee(Employee emp) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Employee.COLUMN_NAME, emp.getName());
        values.put(Employee.COLUMN_PHONE,emp.getPhone());
        values.put(Employee.COLUMN_LAT,emp.getLatitude());
        values.put(Employee.COLUMN_LONG,emp.getLongitude());

        // insert row
        long id = db.insert(Employee.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }//insert


    public Employee getEmployee(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Employee.TABLE_NAME,
                new String[]{Employee.COLUMN_ID, Employee.COLUMN_NAME, Employee.COLUMN_PHONE,Employee.COLUMN_LAT,Employee.COLUMN_LONG},
                Employee.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Employee emp = new Employee(
                cursor.getInt(cursor.getColumnIndex(Employee.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Employee.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Employee.COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(Employee.COLUMN_LAT)),
                cursor.getString(cursor.getColumnIndex(Employee.COLUMN_LONG)),
                cursor.getString(cursor.getColumnIndex(Employee.COLUMN_PHOTO_PATH)));

        // close the db connection
        cursor.close();

        return emp;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Employee.TABLE_NAME + " ORDER BY " +
                Employee.COLUMN_PHONE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(cursor.getColumnIndex(Employee.COLUMN_ID)));
                employee.setName(cursor.getString(cursor.getColumnIndex(Employee.COLUMN_NAME)));
                employee.setPhone(cursor.getString(cursor.getColumnIndex(Employee.COLUMN_PHONE)));

                notes.add(employee);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getEmployeeCount() {
        String countQuery = "SELECT  * FROM " + Employee.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }




    }
