package jazuli.com.catatankajian.SQLite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import jazuli.com.catatankajian.R;
import layout.SQLite.Main2Activit;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {

    String [] daftar;
    ListView listView01;
    Menu menu;

    SearchView searchView;
    ArrayAdapter<String> adapter;
    protected Cursor cursor;
    DB_controler db_controler;
    public static MainActivity mainActivity;
    private String stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTambah = (Button) findViewById(R.id.btn_button2);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BuatBaru.class);
                intent.putExtra("kesukaan", stringExtra);
                startActivity(intent);
            }
        });
        mainActivity = this;
        db_controler = new DB_controler(this);
        stringExtra = getIntent().getStringExtra("kesukaan");

        getSupportActionBar().setTitle(stringExtra);
        RefreshList(stringExtra);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void RefreshList(final String kesukaan){
        SQLiteDatabase database = db_controler.getReadableDatabase();

        //sql
        // select * from siswa where nama=12
        cursor = database.rawQuery("SELECT * FROM biodata where kesukaan = '"+kesukaan+"' ", null);
//        cursor = database.rawQuery("SELECT * FROM siswa WHERE hobi = '" +
//                getIntent().getStringExtra("nama") + "'", null);

        daftar = new String[cursor.getCount()];

        cursor.moveToFirst();

        for (int cc = 0 ; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        listView01 = (ListView) findViewById(R.id.id_list);
       adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar);
        listView01.setAdapter(adapter);
        listView01.setSelected(true);
        ((ArrayAdapter)listView01.getAdapter()).notifyDataSetInvalidated();
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int args2, long l) {
                final String selection = daftar[args2];
                Intent intent = new Intent(getApplicationContext(), LihatData.class);
                intent.putExtra("nama", selection);
                startActivity(intent);
            }
        });
        listView01.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int args, long l) {

                final String selection = daftar[args];
                final CharSequence[] dialofItem = {"Update Biodata", "Hapus Bidodata"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialofItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item){
                            case 0 :
                                Intent inten = new Intent(getApplicationContext(), Edit_Data.class);
                                inten.putExtra("nama", selection);
                                startActivity(inten);
                                break;
                            case 1 :
                                SQLiteDatabase database1 = db_controler.getWritableDatabase();
                                database1.execSQL("delete from biodata where nama='"+selection+"'");
                                RefreshList(kesukaan);
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
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
