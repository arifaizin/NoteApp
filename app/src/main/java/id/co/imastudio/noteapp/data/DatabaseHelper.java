package id.co.imastudio.noteapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by idn on 3/31/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NoteApp.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = NoteContract.TABLE_NAME;
    private HashMap hp;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NoteContract.SQL_DELETE);
        onCreate(db);
    }

    public Cursor fetchAll() {
        db = this.getReadableDatabase();
        Cursor mCursor = db.query(TABLE_NAME, new String[] { "_id", "judul","label",
                "tanggal", "isi" }, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean insertNotes(String judul, String tanggal, String isi, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("judul", judul);
        contentValues.put("tanggal", tanggal);
        contentValues.put("isi", isi);
        contentValues.put("label", label);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor z = db.rawQuery("select * from " + TABLE_NAME + " where _id=" + id
                + "", null);
        Log.i("data select", ""+z.toString());
        return z;
    }
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
    public boolean updateNotes(Integer id, String judul, String tanggal, String isi, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("judul", judul);
        contentValues.put("tanggal", tanggal);
        contentValues.put("isi", isi);
        contentValues.put("label", label);
        db.update(TABLE_NAME, contentValues, "_id = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }
    public Integer deleteNotes(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList getAll() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("_id")));
            array_list.add(res.getString(res.getColumnIndex(NoteContract.COLUMN_ISI)));
            array_list.add(res.getString(res.getColumnIndex(NoteContract.COLUMN_TANGGAL)));
            array_list.add(res.getString(res.getColumnIndex(NoteContract.COLUMN_JUDUL)));
            array_list.add(res.getString(res.getColumnIndex(NoteContract.COLUMN_LABEL)));
            res.moveToNext();
        }
        return array_list;
    }
//
//    public void insertNoteContract(String judul, String isi, String label){
//        db = getWritableDatabase();
//        ContentValues values = new ContentValues();
////        values.put(NoteContract.COLUMN_ID, id);
//        values.put(NoteContract.COLUMN_JUDUL, judul);
//        values.put(NoteContract.COLUMN_ISI, isi);
//        values.put(NoteContract.COLUMN_LABEL, label);
//
//        db.insert(NoteContract.TABLE_NAME, null, values);
//    }
//
//    public List<NoteModel> getNoteContracts(){
//        db = getReadableDatabase();
//        List<NoteModel> lihatCatatan = new ArrayList<>();
//
//        Cursor cursor = db.rawQuery("select * from "+NoteContract.TABLE_NAME+
//                " order by "+NoteContract.COLUMN_ID, null);
//
//        NoteContract newTrans;
////        if(cursor.moveToFirst()){
////            while (cursor.moveToNext()){
////                newTrans = new NoteModel(cursor.getString(0), cursor.getString(1),
////                        cursor.getString(2), cursor.getString(3));
////                transactions.add(newTrans);
////            }
////        }
////        cursor.close();
//
//        return lihatCatatan;
//    }
//
//    public void deleteNoteContract(NoteContract transaction){
//        db = getWritableDatabase();
//
//        db.delete(NoteContract.TABLE_NAME, NoteContract._ID+" = ?",
//                new String[]{transaction.getId()});
//    }
}
