package android.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText judul, harga;
    Button tambah;

    DBHelper dbcenter;

    ArrayList<Map<String, String>> arrayList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah = findViewById(R.id.button);
        judul = findViewById(R.id.editJudul);
        harga = findViewById(R.id.editHarga);

        tambah.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SQLiteDatabase db = dbcenter.getWritableDatabase();
                dbcenter.addBuku(
                        judul.getText().toString(),
                        Integer.parseInt(harga.getText().toString())
                );
                onResume();
            }
        });
    }

    private void loadData(){
        arrayList = dbcenter.getBuku();

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,arrayList,
                android.R.layout.simple_expandable_list_item_2, new String[]{"judul, harga"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    protected void onResume(){
        super.onResume();
        loadData();
    }
}