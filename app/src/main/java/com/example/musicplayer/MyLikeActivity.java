package com.example.musicplayer;

import static com.example.musicplayer.DatabaseHelper.TABLE_NAME;
import static com.example.musicplayer.MainActivity.db;
import static com.example.musicplayer.MainActivity.dbHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyLikeActivity extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ImageView btn_return0;
    ArrayAdapter adapter;
    ListView listView;

    public List<String> mylike_list = new ArrayList<>();
    private List<String> namelist = new ArrayList<>();//歌曲名称数组
    private List<String> artistlist = new ArrayList<>();//艺术家名称数组
    private List<String> urllist = new ArrayList<>();//路径数组


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        initial();
        db = dbHelper.getWritableDatabase();
    }
    public void initial(){
        listView = findViewById(R.id.listview_like);
        btn_return0 = findViewById(R.id.btn_return0);
        btn_return0.setOnTouchListener(this);
        Cursor cursor=db.query(TABLE_NAME,null,null,null,"id",null,null);
        if(cursor.moveToFirst()){
            do{
                String mname = cursor.getString(cursor.getColumnIndex("Musicname"));
                String artist = cursor.getString(cursor.getColumnIndex("artist"));
                String url = cursor.getString(cursor.getColumnIndex("urlpath"));
                namelist.add(mname);
                artistlist.add(artist);
                urllist.add(url);
                mylike_list.add(mname);
            }while(cursor.moveToNext());
        }else{
            Toast.makeText(this,"数据库为空",Toast.LENGTH_LONG).show();
        }
        cursor.close();
        adapter = new ArrayAdapter(MyLikeActivity.this, android.R.layout.simple_list_item_1,mylike_list);//数组适配器，显示歌曲名
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view == btn_return0){
            finish();
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String name = namelist.get(position);
        Intent intent = new Intent(MyLikeActivity.this,MusicPlayer.class);
        //因为intent只能不能传List，只能传String，因此需要转换
        String[] nameList= new String[20];
        for(int i=0;i<namelist.size();i++){
            nameList[i]=namelist.get(i);
        }
        String[] artistList = new String[20];
        for(int i=0;i<artistlist.size();i++){
            artistList[i]=artistlist.get(i);
        }
        String[] urlStr = new String[20];
        for(int i=0;i<urllist.size();i++){
            urlStr[i]=urllist.get(i);
        }
        //通过intent把歌曲id、歌曲名、艺术家名和路径传给MusicPlayer
        intent.putExtra("position",position);
        intent.putExtra("artistList",artistList);
        intent.putExtra("nameList",nameList);
        intent.putExtra("urlStr",urlStr);
        startActivity(intent);//开启活动，音乐播放
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        String s = namelist.get(position);
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setTitle(" ")
                .setMessage("是否取消关注？")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(TABLE_NAME, "Musicname=?", new String[]{String.valueOf(s)});
                        Intent intent = new Intent(MyLikeActivity.this,MyLikeActivity.class);
                        startActivity(intent);
                        Toast.makeText(MyLikeActivity.this, "从我喜欢移除", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyLikeActivity.this, "取消！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }).create();
        alertDialog2.show();
        return true;
    }
}