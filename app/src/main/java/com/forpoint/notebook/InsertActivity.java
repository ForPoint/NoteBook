package com.forpoint.notebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.forpoint.notebook.DataBase.DBManager;
import com.forpoint.notebook.DataBase.NoteData;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;

public class InsertActivity extends AppCompatActivity {
    public final static  int REQUEST_CODE=1;
    public static int RESULT_CODE_OK=1;
    public static int RESULT_CODE_CANCEL=0;

    private DBManager dbm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(InsertActivity.RESULT_CODE_CANCEL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbm = new DBManager(this);
        TextView TextTime = (TextView)findViewById(R.id.time_text);
        //2015-5-25:14:01
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        TextTime.setText(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ok, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.done) {
            return saveData();
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean saveData()
    {
        MaterialEditText EditTitle = (MaterialEditText)findViewById(R.id.title_edit_text);
        MaterialEditText EidtNote = (MaterialEditText)findViewById(R.id.note_edit_text);
        TextView TextTime = (TextView)findViewById(R.id.time_text);

        if(!EditTitle.getText().toString().isEmpty())
        {
            try {
                int Id = dbm.getLastId();
                String Title = EditTitle.getText().toString();
                String Note = EidtNote.getText().toString();
                String Time = TextTime.getText().toString();
                NoteData NoteD = new NoteData(Id,Title,Note,Time);
                dbm.add(NoteD);
                Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Log.v("Error",e.toString());
                Toast.makeText(this,"无笔记,未保存",Toast.LENGTH_LONG).show();
            }
            setResult(InsertActivity.RESULT_CODE_OK);
            finish();
        }else{
            Toast.makeText(this,"无标题",Toast.LENGTH_LONG).show();
        }

        return true;
    }
    public interface OnIsertActivityFinished{
        void onFinished();
    }
}
