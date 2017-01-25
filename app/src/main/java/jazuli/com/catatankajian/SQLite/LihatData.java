package jazuli.com.catatankajian.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jazuli.com.catatankajian.R;

public class LihatData extends AppCompatActivity {

    protected Cursor cursor;
    DB_controler db_controler;
    TextView button2;
    TextView textView1,textView2,textView3,textView4,textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        db_controler = new DB_controler(this);

         textView1 = (TextView)findViewById(R.id.tv_textView1);
         textView2 = (TextView)findViewById(R.id.tv_textView2);
         textView3 = (TextView)findViewById(R.id.tv_textView3);
         textView4 = (TextView)findViewById(R.id.tv_textView4);
         textView5 = (TextView)findViewById(R.id.tv_textView5);

        SQLiteDatabase sqLiteDatabase = db_controler.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            textView1.setText(cursor.getString(0).toString());
            textView2.setText(cursor.getString(1).toString());
            textView3.setText(cursor.getString(2).toString());
            textView4.setText(cursor.getString(3).toString());
            textView5.setText(cursor.getString(4).toString());
        }
        button2 =(TextView) findViewById(R.id.btnButton);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
}
