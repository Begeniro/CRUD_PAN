package android.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBuku extends AppCompatActivity {
    EditText judul, harga;
    Button batal, simpan;

    DBHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku);

        dbcenter = new DBHelper(this);

        judul = findViewById(R.id.editJudul);
        harga = findViewById(R.id.editHarga);

        simpan = findViewById(R.id.buttonSimpan);
        batal = findViewById(R.id.buttonBatal);

        //mengatur isi dari text view sesuai data
        judul.setText(getIntent().getStringExtra("judul"));
        harga.setText(getIntent().getStringExtra("harga"));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data hasil update catatan
                String id = getIntent().getStringExtra("id");
                dbcenter.updateBuku(Integer.parseInt(id), judul.getText().toString(), Integer.parseInt(harga.getText().toString()));
                Intent n = new Intent(EditBuku.this, MainActivity.class);
                startActivity(n);
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(EditBuku.this, MainActivity.class);
                startActivity(n);
            }
        });
    }
}