package com.litziy.kawalcovid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.litziy.kawalcovid.db.MedicineDatabaseHelper.KEY_DESC;
import static com.litziy.kawalcovid.db.MedicineDatabaseHelper.KEY_ID;
import static com.litziy.kawalcovid.db.MedicineDatabaseHelper.KEY_NAME;
import static com.litziy.kawalcovid.db.MedicineDatabaseHelper.TABLE_MEDICINE;

public class MedicineDatabaseManager {

    private Context mContext;
    private MedicineDatabaseHelper db;

    public MedicineDatabaseManager(Context context) {
        this.mContext = context;
        db = new MedicineDatabaseHelper(mContext);
    }

    SQLiteDatabase getWritableDatabase() {
        return db.getWritableDatabase();
    }

    public int deleteByID(String id) {
        SQLiteDatabase writeableDb = db.getWritableDatabase();
//        return writeableDb.delete(TABLE_MEDICINE, "$KEY_ID = '$id'", null);
        return writeableDb.delete(TABLE_MEDICINE, KEY_ID + "= '" + id + "'", null);
    }

    public void saveData(String name, String desc) {

        SQLiteDatabase writeableDB = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_DESC, desc);

        writeableDB.insert(TABLE_MEDICINE, null, contentValues);

        writeableDB.close();

    }

    Cursor queryAll() {
        SQLiteDatabase readableDb = db.getReadableDatabase();
        Cursor cursor = readableDb.rawQuery(
                "SELECT * FROM table_medicine",
                null
        );
        return cursor;
    }

    public ArrayList<Medicine> getData() {
        ArrayList<Medicine> medList = new ArrayList<>();

        SQLiteDatabase readableDb = db.getReadableDatabase();

        Cursor cursor = readableDb.rawQuery(
                "SELECT * FROM table_medicine",
                null
        );

        while (cursor.moveToNext()) {
            Medicine tempMedList = new Medicine(
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESC)));

            medList.add(tempMedList);
        }

        return medList;
    }


}
