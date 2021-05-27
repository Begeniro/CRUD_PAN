package android.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "book_db";
    private static final String TABLE_BUKU = "book";
    private static final String KEY_ID_BUKU = "id_buku";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_HARGA = "harga";

    private static final String CREATE_DATABASE_BUKU = "CREATE TABLE "+ TABLE_BUKU + "("
            + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_JUDUL + " TEXT,"
            + KEY_HARGA + " INTEGER)";

    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_BUKU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBuku(String judul, int harga){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, judul);
        values.put(KEY_HARGA, harga);

        db.insert(TABLE_BUKU, null, values);
    }

    public ArrayList<Map<String,String>> getBuku(){
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        int id_buku= 0;
        String judul = "";
        int harga = 0;

        String selectQuery = "SELECT * FROM "+ TABLE_BUKU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                id_buku = c.getInt(c.getColumnIndex(KEY_ID_BUKU));
                judul = c.getString(c.getColumnIndex(KEY_JUDUL));
                harga = c.getInt(c.getColumnIndex(KEY_HARGA));

                Map<String, String> itemMap = new HashMap<>();

                itemMap.put(KEY_ID_BUKU, String.valueOf(id_buku));
                itemMap.put(KEY_JUDUL, judul);
                itemMap.put(KEY_HARGA, String.valueOf(harga));

                arrayList.add(itemMap);
            }while (c.moveToNext());
        }
        return arrayList;
    }
}
