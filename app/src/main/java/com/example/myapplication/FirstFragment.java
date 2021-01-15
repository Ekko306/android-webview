package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends AppCompatActivity implements AdapterView.OnItemClickListener {
        private ListView listView;
        private TextView textView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "R123123", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
// TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_first);
            listView=(ListView) findViewById(R.id.listView);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            textView = (TextView)findViewById(R.id.head);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(v);
                }
            });
        }
        public void next(View v){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
// TODO Auto-generated method stub
            String string=(String) parent.getItemAtPosition(position);
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,SecondFragment.class);
            intent.putExtra("key",position);
            startActivity(intent);
        }
        private String[] getData(){
//            List a = getFilesAllName("/data/user/0/com.example.myapplication/files");
            List a = getFilesAllName(getFilesDir().toString());
            System.out.println(a);

            String[] strs1= (String[]) a.toArray(new String[a.size()]);
            for(int i=0;i<strs1.length;i++){
                strs1[i] = strs1[i].substring(46,strs1[i].length()-1);
            }
            return strs1;
        }
    public static List<String> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i].getAbsolutePath());
        }
        return s;
    }
    }