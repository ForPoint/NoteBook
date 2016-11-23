package com.forpoint.notebook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add Notes
     * @param Note
     */
    public void add(NoteData Note) {
        db.beginTransaction();  //开始事务
        try {
                Log.v("Insert",Note.toString());
                db.execSQL("INSERT INTO NoteData VALUES(?, ?, ?, ?)", new Object[]{Note.getId(),Note.getTitle(),Note.getNote(),Note.getTime()});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * delete Note
     * @param id
     */
    public boolean delete(String id){
        db.delete("NoteData", "id = ?" , new String[]{id});
        return true;
    }

    /**
     * update Note
     * @param Note
     */
    public void update(NoteData Note) {
        ContentValues cv = new ContentValues();
        cv.put("title",Note.getTitle());
        cv.put("note", Note.getNote());
        String id = Integer.toString(Note.getId());
        db.update("NoteData", cv, "id = ?", new String[]{id});
    }
    /**
     * query Note
     * @param Title
     * @return List<Note>
     */
    public List<NoteData> querybytitle(String Title){
        List<NoteData> Notes = null;
        Cursor cursor = db.rawQuery("SELECT * FROM NoteData WHERE title like ？",new String[]{"%"+Title+"%"});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String note = cursor.getString(2);
            String time = cursor.getString(3);
            NoteData Note = new NoteData(id,title,note,time);
            Notes.add(Note);
        }
        return Notes;
    }

    public NoteData querybyid(String id){
        NoteData Note = new NoteData();
        Cursor cursor = db.rawQuery("SELECT * FROM NoteData WHERE id = ?",new String[]{id});
        while (cursor.moveToNext()){
           // int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String note = cursor.getString(2);
            String time = cursor.getString(3);
            NoteData N = new NoteData(Integer.parseInt(id),title,note,time);
            Note = N;
        }
        return Note;
    }
    public List<NoteData> getall(){
        Log.v("getall","getall start");
        List<NoteData> Notes =new ArrayList<NoteData>();
        Cursor cursor = db.query("NoteData", null,  null,  null, null, null, null);
        if(cursor == null)
            Log.v("getall","DataBase is null");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String note = cursor.getString(2);
            String time = cursor.getString(3);
            NoteData Note = new NoteData(id,title,note,time);
            Log.v("getall",Note.toString());
            try {
                Notes.add(Note);
            }catch (Exception e){
                Log.v("getall",e.toString());
            }
        }
        if(Notes == null)
            Log.v("getall","Notes is null");
        return Notes;
    }
    /**
     * get Last Id
     * @return id
     */
    public int getLastId()
    {
        //SELECT * FROM NoteData
        int id = 0;
        Cursor cursor = db.query("NoteData", null,  null,  null, null, null, null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                id++;
            }
        }
        return id;
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
