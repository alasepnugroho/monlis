package com.example.toothless.monlis.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler_Detail_Listrik extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Detail_Listrik.db";

    // User table name
    private static final String TABLE_DETAIL_LISTRIK = "List_Detail_Listrik";

    // User Table Columns names
    private static final String COLUMN_DETAIL_LISTRIK_ID = "id_detail";
    private static final String COLUMN_DETAIL_LISTRIK_BIAYA = "biaya_bulanan";
    private static final String COLUMN_DETAIL_LISTRIK_ENERGI = "energi_bulanan";
    private static final String COLUMN_DETAIL_LISTRIK_BATAS_ENERGI = "batas_energi";
    private static final String COLUMN_DETAIL_LISTRIK_BATAS_BIAYA = "batas_biaya";

    // create table sql query
    private String CREATE_DETAIL_LISTRIK_TABLE = "CREATE TABLE " + TABLE_DETAIL_LISTRIK + "("
            + COLUMN_DETAIL_LISTRIK_ID + " TEXT," + COLUMN_DETAIL_LISTRIK_BIAYA + " TEXT,"
            + COLUMN_DETAIL_LISTRIK_ENERGI + " TEXT,"+ COLUMN_DETAIL_LISTRIK_BATAS_ENERGI + " TEXT,"+ COLUMN_DETAIL_LISTRIK_BATAS_BIAYA + " TEXT" + ")";
    // drop table sql query
    private String DROP_DETAIL_LISTRIK_TABLE = "DROP TABLE IF EXISTS " + TABLE_DETAIL_LISTRIK;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHandler_Detail_Listrik(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DETAIL_LISTRIK_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_DETAIL_LISTRIK_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param detail_listrik
     */
    public void addUser(Detail_Listrik detail_listrik ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DETAIL_LISTRIK_ID, detail_listrik.getId_detail());
        values.put(COLUMN_DETAIL_LISTRIK_BIAYA, detail_listrik.getBiaya_bulanan());
        values.put(COLUMN_DETAIL_LISTRIK_ENERGI, detail_listrik.getEnergi_bulanan());
        values.put(COLUMN_DETAIL_LISTRIK_BATAS_ENERGI, detail_listrik.getBatas_energi());
        values.put(COLUMN_DETAIL_LISTRIK_BATAS_BIAYA, detail_listrik.getBatas_biaya());

        // Inserting Row
        db.insert(TABLE_DETAIL_LISTRIK, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public ArrayList<Detail_Listrik> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_DETAIL_LISTRIK_ID,
                COLUMN_DETAIL_LISTRIK_BIAYA,
                COLUMN_DETAIL_LISTRIK_ENERGI,
                COLUMN_DETAIL_LISTRIK_BATAS_ENERGI,
                COLUMN_DETAIL_LISTRIK_BATAS_BIAYA
        };
        // sorting orders
        String sortOrder =
                COLUMN_DETAIL_LISTRIK_ID + " ASC";
        ArrayList<Detail_Listrik> detailListrikList = new ArrayList<Detail_Listrik>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_DETAIL_LISTRIK, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               Detail_Listrik detail_listrik = new Detail_Listrik();
                detail_listrik.setId_detail(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_LISTRIK_ID)));
                detail_listrik.setBiaya_bulanan(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_LISTRIK_BIAYA)));
                detail_listrik.setEnergi_bulanan(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_LISTRIK_ENERGI)));
                detail_listrik.setBatas_energi(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_LISTRIK_BATAS_ENERGI)));
                detail_listrik.setBatas_biaya(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_LISTRIK_BATAS_BIAYA)));
                // Adding user record to list
                detailListrikList.add(detail_listrik);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return detailListrikList;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_DETAIL_LISTRIK,null,null);
        db.close();
    }

}
