package com.example.musicplayer;

import static com.example.musicplayer.DatabaseHelper.DB_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    public static DatabaseHelper dbHelper;
    public static SQLiteDatabase db;
    Button btn_Mylike;




    private List<String> namelist = new ArrayList<>();//歌曲名称数组
    private List<String> artistlist = new ArrayList<>();//艺术家名称数组
    private List<String> urllist = new ArrayList<>();//路径数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this,DB_NAME,null,1);
        db = dbHelper.getWritableDatabase();
        //5首歌歌名
        namelist.add("Little Grass Shack");
        namelist.add("Jingle Bells");
        namelist.add("Into the Unknown");
        namelist.add("Buckbreak");
        namelist.add("Oh Radiant One");
        namelist.add("告白气球");
        namelist.add("青花瓷");
        //5个艺术家名
        artistlist.add("Voodoo Suite");
        artistlist.add("Scott Holmes");
        artistlist.add("Marrten");
        artistlist.add("Ken Hamm");
        artistlist.add("Siddhartha");
        artistlist.add("周杰伦");
        artistlist.add("周杰伦");
        //歌曲对应的url
        String url1="https://files.freemusicarchive.org/storage-freemusicarchive-org/music/WFMU/Voodoo_Suite/blissbloodcom/Voodoo_Suite_-_03_-_Little_Grass_Shack.mp3?download=1&name=Voodoo%20Suite%20-%20Little%20Grass%20Shack.mp3";
        String url2="https://files.freemusicarchive.org/storage-freemusicarchive-org/tracks/99BX9qioxvFxilJgANMmPYcuG5I3IxTGchSFa1v8.mp3?download=1&name=Scott%20Holmes%20Music%20-%20Jingle%20Bells.mp3";
        String url3="https://files.freemusicarchive.org/storage-freemusicarchive-org/tracks/JuG1OD1wm6f3XD93haM4qSlwAfyBS4zrOcXstBPb.mp3?download=1&name=Maarten%20Schellekens%20-%20Into%20the%20Unknown.mp3";
        String url4="https://files.freemusicarchive.org/storage-freemusicarchive-org/music/Peppermill_Records/Ken_Hamm/Hi_and_Ho_We_Plant_Trees/Ken_Hamm_-_08_-_Buckbreak.mp3?download=1&name=Ken%20Hamm%20-%20Buckbreak.mp3";
        String url5="https://files.freemusicarchive.org/storage-freemusicarchive-org/tracks/mV3CPsPAboQ1mA4Ji0P2OiKeBNCsy3nPzJGys9l8.mp3?download=1&name=Siddhartha%20Corsus%20-%20Oh%20Radiant%20One.mp3";
        String url6="https://freetyst.nf.migu.cn/public/product9th/product45/2022/04/2700/2022%E5%B9%B404%E6%9C%8825%E6%97%A516%E7%82%B940%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E7%BA%B5%E6%A8%AA%E4%B8%96%E4%BB%A311%E9%A6%96664912/%E6%AD%8C%E6%9B%B2%E4%B8%8B%E8%BD%BD/flac/60054704490004541.flac";
        String url7="https://freetyst.nf.migu.cn/public/ringmaker01/n17/2017/07/%E6%97%A0%E6%8D%9F/2009%E5%B9%B406%E6%9C%8826%E6%97%A5%E5%8D%9A%E5%B0%94%E6%99%AE%E6%96%AF/flac/%E9%9D%92%E8%8A%B1%E7%93%B7-%E5%91%A8%E6%9D%B0%E4%BC%A6.flac";
        //添加url到urllist
        urllist.add(url1);
        urllist.add(url2);
        urllist.add(url3);
        urllist.add(url4);
        urllist.add(url5);
        urllist.add(url6);
        urllist.add(url7);
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,namelist);//数组适配器，显示歌曲名
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);//给listview设置适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = namelist.get(position);
                Intent intent = new Intent(MainActivity.this,MusicPlayer.class);
                //因为intent只能不能传List，只能传String，因此需要转换
                String[] nameList=new String[namelist.size()];
                for(int i=0;i<namelist.size();i++){
                    nameList[i]=namelist.get(i);
                }
                String[] artistList= new String[artistlist.size()];
                for(int i=0;i<artistlist.size();i++){
                    artistList[i]=artistlist.get(i);
                }
                String[] urlStr = new String[urllist.size()];
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
        });
        btn_Mylike = findViewById(R.id.btn_Mylike);
        btn_Mylike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyLikeActivity.class);
                startActivity(intent);
            }
        });

    }
}