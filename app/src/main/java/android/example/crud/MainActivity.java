package android.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Map<String, String>> arrayList;
    EditText judul, harga;
    Button tambah;
    DBHelper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        dbcenter = new DBHelper(this);
        tambah = findViewById(R.id.tambah_buku);

        judul = findViewById(R.id.editJudul);
        harga = findViewById(R.id.editHarga);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menambah buku ke database
                SQLiteDatabase db = dbcenter.getWritableDatabase();
                dbcenter.addBuku(
                        judul.getText().toString(),
                        Integer.parseInt(harga.getText().toString()));

                //membarsihkan text dari kolom edittext
                judul.setText(null);
                harga.setText(null);

                //memuat ulang halaman
                onResume();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val = arrayList.get(i).get("id_buku");
                Intent n = new Intent(MainActivity.this, LihatBuku.class);
                n.putExtra("key_extra_id", val);
                startActivity(n);
            }
        });
    }

    //memuat data dari database
    private void loadData(){
        arrayList=dbcenter.getBuku();

        //mengurutkan urutan dari yang terakhir ditambahkan
        Collections.sort(arrayList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> t1, Map<String, String> t2) {
                return t2.get("id_buku").compareTo(t1.get("id_buku"));
            }
        });

        //membuat simple adapter
        SimpleAdapter simpleAdapter=new SimpleAdapter(this, arrayList,
                android.R.layout.simple_expandable_list_item_2,new String[]{"judul","harga"},
                new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    protected void onResume(){
        super.onResume();
        loadData();
    }
}