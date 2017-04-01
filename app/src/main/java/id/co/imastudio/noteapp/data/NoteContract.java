package id.co.imastudio.noteapp.data;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by idn on 3/31/2017.
 */
public final class NoteContract implements Serializable, BaseColumns {

        public static final String TABLE_NAME = "noteapp";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_JUDUL = "judul";
        public static final String COLUMN_ISI = "isi";
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_TANGGAL = "tanggal";

        public static final String SQL_CREATE = "create table "+TABLE_NAME
                + "(_id integer primary key, judul text,isi text,label text, tanggal text)";
        public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
