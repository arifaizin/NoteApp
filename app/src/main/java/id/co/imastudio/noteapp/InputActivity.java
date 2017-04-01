package id.co.imastudio.noteapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import id.co.imastudio.noteapp.data.DatabaseHelper;
import id.co.imastudio.noteapp.data.NoteContract;

public class InputActivity extends AppCompatActivity {

//    EditText judul, isi, label;
//    String teksJudul, teksIsi, teksLabel;
//    Button simpan;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input);
//
//        judul = (EditText) findViewById(R.id.editTextJudul);
//        isi = (EditText) findViewById(R.id.editTextIsi);
//        label = (EditText) findViewById(R.id.editTextLabel);
//        simpan = (Button) findViewById(R.id.buttonSimpan);
//        simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                simpanCatatan();
//            }
//        });
//    }

//    private void simpanCatatan() {
//        teksJudul = judul.getText().toString();
//        teksIsi = isi.getText().toString();
//        teksLabel = label.getText().toString();
//
//        DatabaseHelper database = new DatabaseHelper(this);
//        database.insertNoteContract(teksJudul, teksIsi, teksLabel);
//
//        Toast.makeText(this, "Catatan "+teksJudul+" berhasil disimpan", Toast.LENGTH_SHORT).show();
//        finish();
//    }

    private DatabaseHelper dbHelper;
    EditText judul, isi, label;
    String teksJudul, teksIsi, teksLabel;
    Button simpan;
//    EditText name;
//    EditText content;
    private CoordinatorLayout coordinatorLayout;
    Bundle extras;
    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
//        name = (EditText) findViewById(R.id.editTextJudul);
//        content = (EditText) findViewById(R.id.editTextIsi);

        judul = (EditText) findViewById(R.id.editTextJudul);
        isi = (EditText) findViewById(R.id.editTextIsi);
        label = (EditText) findViewById(R.id.editTextLabel);
        simpan = (Button) findViewById(R.id.buttonSimpan);

//        teksJudul=judul.getText().toString();
//        teksIsi=isi.getText().toString();
        teksLabel=label.getText().toString();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanCatatan();
            }
        });


        //tampilkan data
        dbHelper = new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int nilaiId = extras.getInt("id");
            if (nilaiId > 0) {
                Toast.makeText(InputActivity.this, "Note Id : "+String.valueOf(nilaiId), Toast.LENGTH_SHORT).show();

                Cursor cursor = dbHelper.getData(nilaiId);
                id_To_Update = nilaiId;
                cursor.moveToFirst();
                String dataNama = cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_JUDUL));
                String dataIsi = cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_ISI));
                String dataLabel = cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_LABEL));
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                judul.setText((CharSequence) dataNama);
                isi.setText((CharSequence) dataIsi);
                label.setText((CharSequence) dataLabel);
            }
        }
    }

    private void simpanCatatan() {
        //ambil tanggal
        Calendar calendar = Calendar.getInstance();
        Log.i("Tgl","Current time => " + calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = sdf.format(calendar.getTime());
        String tanggalSimpan = sdf.format(calendar.getTime());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int nilaiId = extras.getInt("id");
            if (nilaiId > 0) {
                if (isi.getText().toString().trim().equals("") || judul.getText().toString().trim().equals("")) {
                    Toast.makeText(InputActivity.this, "Isi dulu judul dan isinya", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.updateNotes(id_To_Update, judul.getText().toString(), tanggalSimpan, isi.getText().toString(), label.getText().toString() )) {
                        Toast.makeText(InputActivity.this, "Data berhasil disimpan!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InputActivity.this, "Error. Gagal menyimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (isi.getText().toString().trim().equals("") || judul.getText().toString().trim().equals("")) {
                    Toast.makeText(InputActivity.this, "Tolong judul dan isinya diisi terlebih dahulu", Toast.LENGTH_SHORT).show();

                } else {
                    if (dbHelper.insertNotes(judul.getText().toString(), tanggalSimpan, isi.getText().toString(), label.getText().toString())) {
                        Toast.makeText(InputActivity.this, "Sukses tersimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InputActivity.this, "Maafkan. kamu gagal menyimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int nilaiId = extras.getInt("id");
            getMenuInflater().inflate(R.menu.display_menu, menu);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin mau delete?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dbHelper.deleteNotes(id_To_Update);
                                        Toast.makeText(InputActivity.this, "Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(
                                                getApplicationContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                    }
                                });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}

