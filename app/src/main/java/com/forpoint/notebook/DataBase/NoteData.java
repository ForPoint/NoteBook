package com.forpoint.notebook.DataBase;

/**
 * Created by Administrator on 2016/11/8.
 */
public class NoteData {

    private int id;

    private String title;

    private String note;

    private String time;

    public NoteData(){

    }

    public NoteData(int id,String title,String note,String time){
        this.id = id;
        this.title = title;
        this.note = note;
        this.time = time;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime()
    {
        return time;
    }

    @Override
    public String toString(){
        return "Id:"+id+",title:"+title+",note:"+note+",time:"+time;
    }

}
