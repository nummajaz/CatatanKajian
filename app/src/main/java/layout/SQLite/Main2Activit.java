package layout.SQLite;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import jazuli.com.catatankajian.R;
import jazuli.com.catatankajian.SQLite.DB_controler;
import jazuli.com.catatankajian.SQLite.Edit_Data;
import jazuli.com.catatankajian.SQLite.MainActivity;

public class Main2Activit extends AppCompatActivity {
    SQLiteDatabase data;

    DB_controler controler;
    String[] strings;
    protected Cursor cursor_tampilan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        controler = new DB_controler(this);

        tampilan();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final LayoutInflater inflater = getLayoutInflater();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                View alertLayout = inflater.inflate(R.layout.alertdialog, null);

                final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username);
                final EditText etAlamat = (EditText) alertLayout.findViewById(R.id.et_useralamat);

                alert.setTitle("Write Theme");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            data = controler.getWritableDatabase();
                            data.execSQL("insert into siswa(hobi, belajar)values('" +
                                    etAlamat.getText().toString() + "','" +
                                    etUsername.getText().toString() + "')");
                            dialog.dismiss();
                            tampilan();


                        } catch (Exception e) {
                            Toast.makeText(Main2Activit.this, "tidak berhasil" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Catatan Kajian");
        actionBar.setSubtitle(Html.fromHtml("<font color='#FFFFFF'>Ini Adalah Catatan Kajian</font>"));

    }


        public void tampilan() {

        SQLiteDatabase datas = controler.getWritableDatabase();
        cursor_tampilan = datas.rawQuery("SELECT  * FROM siswa", null);

            strings = new String[cursor_tampilan.getCount()];

            cursor_tampilan.moveToFirst();

        for (int cc = 0; cc < cursor_tampilan.getCount(); cc++) {
            cursor_tampilan.moveToPosition(cc);
            strings[cc] = cursor_tampilan.getString(1).toString();

        }
        ArrayAdapter tadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strings);
        ListView view1 = (ListView) findViewById(R.id.id_list_tampilan);
        view1.setAdapter(tadapter);
        view1.setSelected(true);
        view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String  selection = strings[position];
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("kesukaan", selection);
                startActivity(intent);
            }
        });
            view1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                    final String selec = strings[position];
                    final CharSequence[] dialofItem = {"Update Biodata", "Hapus Bidodata"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activit.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(dialofItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            switch (item){
                                case 0 :
                                    update(strings[item]);
                                    break;
                                case 1 :
                                    SQLiteDatabase database1 = controler.getWritableDatabase();
                                    database1.execSQL("delete from siswa where hobi='"+selec+"'");
                                  tampilan();
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
        int id = item.getItemId();
        if (id == R.id.action_bar_title) {
            SQLiteDatabase database1 = controler.getWritableDatabase();
            database1.execSQL("delete from siswa where hobi='"+strings+"'");
            tampilan();
        }
        return super.onOptionsItemSelected(item);
    }
public void update(final String string){
    final AlertDialog.Builder builderses = new AlertDialog.Builder(this);

    final LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.dialogupdate, null);

    final EditText etDialogUpdate = (EditText) alertLayout.findViewById(R.id.et_update_dialog);
    final EditText etDialogUpdate2 = (EditText) alertLayout.findViewById(R.id.et_update_dialog2);

    etDialogUpdate.setText(string);
    builderses.setTitle("Update Theme");
    // this is set the view from XML inside AlertDialog
    builderses.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    builderses.setCancelable(false);
    builderses.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
        }
    });

    builderses.setPositiveButton("ok", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            try {
                data = controler.getWritableDatabase();
                data.execSQL("update siswa set hobi='" +
                        etDialogUpdate.getText().toString() + "', belajar='" +
                        etDialogUpdate2.getText().toString() + "' where"+strings);
                dialog.dismiss();
                tampilan();
            } catch (Exception e) {
                Toast.makeText(Main2Activit.this, "tidak berhasil" + e, Toast.LENGTH_SHORT).show();
                Log.e("error", String.valueOf(e));
            }
        }
    });
    AlertDialog dialog = builderses.create();
    dialog.show();

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

}
