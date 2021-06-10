package android.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatBuku extends AppCompatActivity {
    TextView txtjudul, txtharga;
    Button edit, delete;

    String id, judul, harga;

    DBHelper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_buku);

        dbcenter = new DBHelper(this);

        txtjudul = findViewById(R.id.viewJudul);
        txtharga  = findViewById(R.id.viewHarga);

        edit = findViewById(R.id.buttonEdit);
        delete = findViewById(R.id.buttonDelete);

        id = getIntent().getStringExtra("key_extra_id");

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM book WHERE id_buku = '" + id + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) //jika hasil query tidak kosong
        {   //isi variabel textview dengan hasil query sesuai indeks
            cursor.moveToPosition(0);
            judul = cursor.getString(1).toString();
            harga = cursor.getString(2).toString();

            txtjudul.setText(cursor.getString(1).toString());
            txtharga.setText(cursor.getString(2).toString());

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(), EditBuku.class);
                //membawa data dari class ini ke class UpdateCatatan
                m.putExtra("id", id);
                m.putExtra("judul", judul);
                m.putExtra("harga", harga);
                startActivity(m);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbcenter.delete(Integer.parseInt(id));
                onBackPressed();
            }
        });
    }

}