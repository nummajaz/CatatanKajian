package jazuli.com.catatankajian.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jazuli.com.catatankajian.R;

public class BuatBaru extends AppCompatActivity {
    protected Cursor cursor;
    DB_controler db_controler;
    Button button1, button2;
    EditText editText1,editText2,editText3,editText4,editText5;
    private String stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_baru);

        db_controler = new DB_controler(this);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        stringExtra = getIntent().getStringExtra("kesukaan");

        editText1 = (EditText) findViewById(R.id.et_edit_text1);
        editText2 = (EditText) findViewById(R.id.et_edit_text2);
        editText3 = (EditText) findViewById(R.id.et_edit_text3);
        editText4 = (EditText) findViewById(R.id.et_edit_text4);
        editText5 = (EditText) findViewById(R.id.et_edit_text5);

        button1 = (Button) findViewById(R.id.btn_button_1);
        button2 = (Button) findViewById(R.id.btn_button_2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDatabase database = db_controler.getWritableDatabase();
                    database.execSQL("insert into biodata(no, nama, tgl, jk, alamat, kesukaan)values('" +
                            Integer.parseInt(editText1.getText().toString()) + "','" +
                            editText2.getText().toString() + "','" +
                            editText3.getText().toString() + "','" +
                            editText4.getText().toString() + "','" +
                            editText5.getText().toString() + "','" +
                            stringExtra + "')");
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                    MainActivity.mainActivity.RefreshList(stringExtra);
                    //TampilanAwal.tampilanAwal.RefreshTampilan();
                    finish();
                    //  }
                }catch (Exception e){
                    Toast.makeText(BuatBaru.this, "Coba lagi " + e, Toast.LENGTH_SHORT).show();
                }
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }
}

