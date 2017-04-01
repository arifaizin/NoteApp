package id.co.imastudio.noteapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import id.co.imastudio.noteapp.data.DatabaseHelper;
import id.co.imastudio.noteapp.data.NoteContract;

//Thanks to : http://www.parallelcodes.com/creating-a-notepad-application-in-android/

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    FloatingActionButton btnTambah;
    ListView listViewNote;
    Menu menu;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        btnTambah = (FloatingActionButton) findViewById(R.id.fab);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });

        Cursor cursor = dbHelper.fetchAll();
        String[] dataDariDatabase = new String[]{
                NoteContract.COLUMN_JUDUL,
                NoteContract.COLUMN_ID,
                NoteContract.COLUMN_TANGGAL,
                NoteContract.COLUMN_ISI,
                NoteContract.COLUMN_LABEL};
        int[] tampilKeTextview = new int[]{
                R.id.txtJudul,
                R.id.txtId,
                R.id.txtTanggal,
                R.id.txtIsi,
                R.id.txtLabel};
        adapter = new SimpleCursorAdapter(this, R.layout.note_list_item, cursor, dataDariDatabase, tampilKeTextview, 0);

        listViewNote = (ListView) findViewById(R.id.listView);
        listViewNote.setAdapter(adapter);
        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayoutParent = (LinearLayout) view;
                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(0);
                TextView nomerId = (TextView) linearLayoutChild.getChildAt(1);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", Integer.parseInt(nomerId.getText().toString()));
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
//    ArrayAdapter adapter;
//    ListView listNote;
//    private ArrayList<NoteModel> noteList = new ArrayList<>();
//
////    String[] listArray={"Asus","Acer","Apple","Samsung","Thoshiba","Sony","Xiomi","Motorola"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        listNote = (ListView) findViewById(R.id.listView);
//
////        dummyData();
////        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listArray);
////        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteList);
////
////        listNote.setAdapter(adapter);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent pindah = new Intent(MainActivity.this, InputActivity.class);
//                startActivity(pindah);
//            }
//        });
//    }
//
//    private void dummyData(){
//        noteList.add(new NoteModel("1", "Catatan 1", "Bla bla bla bla", "keluarga"));
//        noteList.add(new NoteModel("2", "Catatan 2", "Bla bla bla bla", "kampus"));
//        noteList.add(new NoteModel("3", "Catatan 3", "Bla bla bla bla", "hutang"));
//        noteList.add(new NoteModel("4", "Catatan 4", "Bla bla bla bla", "kampus"));
//        noteList.add(new NoteModel("5", "Catatan 5", "Bla bla bla bla", "usaha"));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        noteList = (ArrayList<NoteModel>) dbHelper.getNoteContracts();
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteList);
//        listNote.setAdapter(adapter);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
