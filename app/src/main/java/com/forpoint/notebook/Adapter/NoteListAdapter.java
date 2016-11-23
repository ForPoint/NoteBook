package com.forpoint.notebook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forpoint.notebook.DataBase.NoteData;
import com.forpoint.notebook.R;

import java.util.List;



/**
 * Created by Administrator on 2016/11/9.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private Context mContext;
    private List<NoteData> Notes;
    private OnItemClick onItemClick ;
    public NoteListAdapter(Context mContext, List<NoteData> Notes,OnItemClick itemClickListener) {
        this.mContext = mContext;
        this.Notes = Notes;
        this.onItemClick=itemClickListener;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        Log.v("setOnItemClick",onItemClick.toString());
        this.onItemClick = onItemClick;
    }

    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder viewHolder, int position )
    {
        Log.v("onBindViewHolder","start");
        // 给ViewHolder设置元素
        NoteData Note = Notes.get(position);
        viewHolder.mTextView.setText(Note.getTitle());
        viewHolder.mTimeView.setText(Note.getTime());
        viewHolder.itemView.setTag(Integer.toString(Note.getId()));
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ) {
        // 给ViewHolder设置布局文件
        Log.v("onCreateViewHolder",viewGroup.toString());
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        Log.v("item",v.toString());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                 //   Log.v("onclick","test1");
                    //注意这里使用getTag方法获取数据
                    onItemClick.onItemClick(v,(String)v.getTag());
                }else{
                    Log.v("onclick","null");
               //     Log.v("onclick",onItemClick.toString());
                    Log.v("onclick",v.toString());
                }
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClick != null) {
                    //   Log.v("onclick","test1");
                    //注意这里使用getTag方法获取数据
                    onItemClick.onLongItemClick(v,(String)v.getTag());
                }else{
                    Log.v("onclick","null");
                    //     Log.v("onclick",onItemClick.toString());
                    Log.v("onclick",v.toString());
                    return false;
                }
                return true;
            }
        });
        return vh;
    }



    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return Notes == null ? 0 : Notes.size();
    }


    // 重写的自定义ViewHolder
    public static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public TextView mTimeView;

        public ViewHolder( View v ) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title_label_text);
            mTimeView = (TextView) v.findViewById(R.id.time_label_text);
        }
    }
}


