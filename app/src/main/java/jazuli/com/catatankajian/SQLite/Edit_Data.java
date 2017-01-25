package jazuli.com.catatankajian.SQLite;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jazuli.com.catatankajian.R;

public class Edit_Data extends AppCompatActivity //implements View.OnClickListener
{
    private int clicked = 0;
    protected Cursor cursor;
    DB_controler db_controler;
    TextView button1;
    TextView button2;
    EditText etIsi, etNama, etMerk, etHarga, etJajan;
    private String stringExtra;

    KeyListener variable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__data);


        db_controler = new DB_controler(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        stringExtra = getIntent().getStringExtra("kesukaan");

        //inisialisasi variabel
        etIsi = (EditText) findViewById(R.id.et_editText1);
        etNama = (EditText) findViewById(R.id.et_editText2);
        etMerk = (EditText) findViewById(R.id.et_editText3);
        etHarga = (EditText) findViewById(R.id.et_editText4);
        etJajan = (EditText) findViewById(R.id.et_editText5);

        SQLiteDatabase sqLiteDatabase = db_controler.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            etIsi.setText(cursor.getString(0).toString());
            etNama.setText(cursor.getString(1).toString());
            etMerk.setText(cursor.getString(2).toString());
            etHarga.setText(cursor.getString(3).toString());
            etJajan.setText(cursor.getString(4).toString());
        }
        variable = etIsi.getKeyListener();
        variable = etNama.getKeyListener();
        variable = etNama.getKeyListener();
        variable = etHarga.getKeyListener();
        variable = etJajan.getKeyListener();

        etIsi.setKeyListener(null);
        etNama.setKeyListener(null);
        etMerk.setKeyListener(null);
        etHarga.setKeyListener(null);
        etJajan.setKeyListener(null);
        button1 = (TextView) findViewById(R.id.btn_Button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = clicked + 1;
                if (clicked <= 1) {
                    etIsi.setCursorVisible(true);
                    button1.setText("Update");
                    etIsi.setKeyListener(variable);
                    etNama.setKeyListener(variable);
                    etMerk.setKeyListener(variable);
                    etHarga.setKeyListener(variable);
                    etJajan.setKeyListener(variable);
                } else {
                    try {
                        SQLiteDatabase database = db_controler.getWritableDatabase();
                        database.execSQL("update biodata set nama='" +
                                etNama.getText().toString() + "', tgl='" +
                                etMerk.getText().toString() + "', jk='" +
                                etHarga.getText().toString() + "', alamat='" +
                                etJajan.getText().toString() + "' where no='" +
                                etIsi.getText().toString() + stringExtra + "'");
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                        MainActivity.mainActivity.RefreshList(stringExtra);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(Edit_Data.this, "masukkan data dengan lengakap" + e, Toast.LENGTH_SHORT).show();
                        Log.e("error", String.valueOf(e));
                    }
                }
            }

        });
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
//        //buat sambungan baru ke database
//        dataSource = new DBDataSource(this);
//        dataSource.open();
//
//        //ambil data barang dari extras
//        Bundle bun = this.getIntent().getExtras();
//        id = bun.getLong("id");
//
//        harga = bun.getString("harga");
//        merk = bun.getString("merk");
//        nama = bun.getString("nama");
//        isi = bun.getString("isi");
//
//        //masukkan data-data barang tersebut ke field editor
//        tvId.append(String.valueOf(id));
//
//        etNama.setText(nama);
//        etMerk.setText(merk);
//        etHarga.setText(harga);
//        etIsi.setText(isi);
//
//        //set listener pada tombol
//        btnSave = (Button) findViewById(R.id.btn_save_update);
//        btnSave.setOnClickListener(this);
//        btnCancel = (Button) findViewById(R.id.btn_cancel_update);
//        btnCancel.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//
//            //apabila tombol save diklik (update barang)
//            case R.id.btn_save_update:
//                kajian = new Kajian();
//                kajian.setHarga_barang(etHarga.getText().toString());
//                kajian.setMerk_barang(etMerk.getText().toString());
//                kajian.setNama_barang(etNama.getText().toString());
//                kajian.setIsi_barang(etIsi.getText().toString());
//                kajian.setId(id);
//
//                dataSource.updateKajian(kajian);
//
//                startActivity(i);
//                Edit_Data.this.finish();
//                dataSource.close();
//                break;
//
//            //apabila tombol cancel diklik, finish activity
//            case R.id.btn_cancel_update:
//                finish();
//                dataSource.close();
//                break;
//        }
//    }

