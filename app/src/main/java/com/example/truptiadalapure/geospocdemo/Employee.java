package com.example.truptiadalapure.geospocdemo;

public class Employee {

    public static final String TABLE_NAME = "employees";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_PHOTO_PATH = "imagePath";

    private int id;
    private String name;
    private String phone;
    private String latitude;
    private String longitude;
    private String imagePath;


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_LAT + " TEXT,"
                    + COLUMN_LONG + " TEXT,"
                    + COLUMN_PHOTO_PATH + " TEXT"
                    + ")";

    public Employee() {
    }

    public static String getColumnPhotoPath() {
        return COLUMN_PHOTO_PATH;
    }

    public Employee(int id, String name, String phone, String latitude, String longitude, String imagePath) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;

    }

    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
