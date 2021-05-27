package android.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        dbcenter = new DBHelper(this);
        tambah = findViewById(R.id.button);

        judul = findViewById(R.id.editJudul);
        harga = findViewById(R.id.editHarga);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbcenter.getWritableDatabase();
                dbcenter.addBuku(
                        judul.getText().toString(),
                        Integer.parseInt(harga.getText().toString()));
                onResume();

            }
        });
    }

    private void loadData(){
        arrayList=dbcenter.getBuku();

        Collections.sort(arrayList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> t1, Map<String, String> t2) {
                return t2.get("id_buku").compareTo(t1.get("id_buku"));
            }
        });

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