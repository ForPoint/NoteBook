package com.forpoint.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.forpoint.notebook.Adapter.NoteListAdapter;
import com.forpoint.notebook.Adapter.OnItemClick;
import com.forpoint.notebook.DataBase.DBManager;
import com.forpoint.notebook.DataBase.NoteData;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private NoteListAdapter myAdapter;

    private List<NoteData> Notes = null;

    private DBManager dbm ;

    private OnItemClick onItemClickForInsert=new OnItemClick() {
        @Override
        public void onItemClick(View view, String data) {
            Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            Log.v("onClick", data);
            String id = data;
            UpdataDialog(id);
            //   DeleteDialog(id);
        }
        @Override
        public void onLongItemClick(View view, String data) {
            Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            Log.v("onLongClick", data);
            String id = data;
            DeleteDialog(id);
            //   DeleteDialog(id);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case InsertActivity.REQUEST_CODE :
                if(resultCode==InsertActivity.RESULT_CODE_OK){
                    refresh();
                }
                break;
            case UpdataActivity.REQUEST_CODE :
                if(resultCode==UpdataActivity.RESULT_CODE_OK){
                    refresh();
                }
                break;
            case SettingActivity.REQUEST_CODE :
                if(resultCode==SettingActivity.RESULT_CODE_OK){
                    refresh();
                }
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //新增一条笔记
                InsertDialog();
            }
        });

        dbm = new DBManager(MainActivity.this);
        try {
            Notes = dbm.getall();
        } catch (Exception e) {
            Log.v("Error", e.toString());
        }


        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.base_list);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(layout);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 初始化自定义的适配器
        myAdapter = new NoteListAdapter(this, Notes,onItemClickForInsert);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
        Log.v("Adapter",myAdapter.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void InsertDialog()
    {
        Intent intent=new Intent(MainActivity.this,InsertActivity.class);
        startActivityForResult(intent,InsertActivity.REQUEST_CODE);
    }

    public void UpdataDialog(String id)
    {
        Intent intent=new Intent(MainActivity.this,UpdataActivity.class);
        intent.putExtra("ID",id);
        startActivityForResult(intent,UpdataActivity.REQUEST_CODE);
    }

    public void DeleteDialog(String d_id)
    {
        final String delete_id = d_id;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete")
                .setMessage("确认要删除么？")
                .setPositiveButton("删除",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(dbm.delete(delete_id)){
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            refresh();
                        }
                    }
                })
                .show();
    }
    public void refresh()
    {
        myAdapter=new NoteListAdapter(this,dbm.getall(),onItemClickForInsert);
        mRecyclerView.setAdapter(myAdapter);
    }

}
