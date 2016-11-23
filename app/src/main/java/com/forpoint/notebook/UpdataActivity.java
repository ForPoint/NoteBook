package com.forpoint.notebook;

import android.content.Intent;
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

/**
 * Created by Administrator on 2016/11/11.
 */
public class UpdataActivity extends AppCompatActivity {
    public final static  int REQUEST_CODE=2;
    public static  int RESULT_CODE_OK=1;
    public static  int RESULT_CODE_CANCEL=0;
    private DBManager dbm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(UpdataActivity.RESULT_CODE_CANCEL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String id=intent.getStringExtra("ID");
        dbm = new DBManager(this);
        readData(id);
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

    public boolean readData(String id)
    {
        MaterialEditText EditTitle = (MaterialEditText)findViewById(R.id.title_edit_text);
        MaterialEditText EditNote = (MaterialEditText)findViewById(R.id.note_edit_text);
        TextView TextTime = (TextView)findViewById(R.id.time_text);
        Log.v("readData",id);
        NoteData Note = dbm.querybyid(id);
        EditTitle.setText(Note.getTitle());
        EditNote.setText(Note.getNote());
        TextTime.setText(Note.getTime());
        return true;
    }

    public boolean saveData()
    {
        MaterialEditText EditTitle = (MaterialEditText)findViewById(R.id.title_edit_text);
        MaterialEditText EditNote = (MaterialEditText)findViewById(R.id.note_edit_text);
        TextView TextTime = (TextView)findViewById(R.id.time_text);

        if(!EditTitle.getText().toString().isEmpty())
        {
            try {
                int Id = dbm.getLastId();
                String Title = EditTitle.getText().toString();
                String Note = EditNote.getText().toString();
                String Time = TextTime.getText().toString();
                NoteData NoteD = new NoteData(Id,Title,Note,Time);
                dbm.add(NoteD);
                Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Log.v("Error",e.toString());
                Toast.makeText(this,"无笔记,未保存",Toast.LENGTH_LONG).show();
            }
            setResult(UpdataActivity.RESULT_CODE_OK);
            UpdataActivity.this.finish();
        }else{
            Toast.makeText(this,"无标题",Toast.LENGTH_LONG).show();
        }

        return false;
    }
    public interface OnIsertActivityFinished{
        void onFinished();
    }
}

