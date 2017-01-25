package jazuli.com.catatankajian.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.name;
import static android.R.attr.version;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by jazuli on 10/01/17.
 */

public class DB_controler extends SQLiteOpenHelper {
    /** deklrasi konstanta-konstanta yang digunakan pada database, seperti nama label
     nama-nama kolom, nama database, dan versi database **/
//
//    public static final String TABLE_NAME = "data_inventori";
//    public static final String COLOUMN_ID = "_id";
//    public static final String COLOUMN_ISI = "isi_barang";
//    public static final String COLOUMN_NAME = "nama_barang";
//    public static final String COLOUMN_MERK = "merk_barang";
//    public static final String COLOUMN_HARGA = "harga_barang";
    private static final String db_name = "biodatadiri.db";
    private static final int db_version = 1;

    //perintah SQL untuk membuat tabel database baru

//            + TABLE_NAME + "("
//            + COLOUMN_ID + " no integer primary key, "
//            + COLOUMN_ISI + " varchar(50) not null, "
//            + COLOUMN_NAME + " varchar(50) not null, "
//            + COLOUMN_MERK + " varchar(50) not null, "
//            + COLOUMN_HARGA + " varchar(50) not null);";



    public DB_controler(Context context) {
        super(context, db_name, null, db_version);
    }

    //mengeskusi perintah SQL diatas untuk membuat data tabel database baru
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbcreate = "create table biodata(no integer primary key, nama text null, tgl text null, jk text null, alamat text null, kesukaan text null);";
        sqLiteDatabase.execSQL(dbcreate);
        dbcreate = "INSERT INTO biodata(no, nama, tgl, jk, alamat, kesukaan) VALUES ('1001','Fathur','1994-02-03','Laki-laki','Jakarta','makan');";
        sqLiteDatabase.execSQL(dbcreate);

        String dbmake = "create table siswa(nomor integer primary key, hobi text null, belajar text null, kesukaan text null);";
        sqLiteDatabase.execSQL(dbmake);
        dbmake = "INSERT INTO siswa(nomor, hobi, belajar, kesukaan) VALUES ('101','Memancing','bahasa','makan');";
        sqLiteDatabase.execSQL(dbmake);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       String database;
        database = "DROP TABLE IF EXIST siswa";
        sqLiteDatabase.execSQL(database);
        onCreate(sqLiteDatabase);

    }


    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM siswa";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("nomor", cursor.getString(0));
                map.put("hobi", cursor.getString(1));
                map.put("belajar", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void insert(String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO siswa " + " (hobi, belajar) " +
                "VALUES ('" + name + "', '" + address + "')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void update(int id, String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE siswa "  + " SET hobi"
                + "='" + name + "', belajar"
                + "='" + address + "'"
                + " WHERE nomor" + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM siswa"+ " WHERE nomor" + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
