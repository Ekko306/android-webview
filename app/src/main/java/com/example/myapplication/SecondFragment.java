package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SecondFragment extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private TextView textView;
    private TextView textView123;
    private TextView textViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        Intent intent=getIntent();
        int position= intent.getIntExtra("key",0);
        String[] temp = getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        textView = (TextView) findViewById(R.id.htmlText);
        textView123 = (TextView) findViewById(R.id.return123);
        textViewTitle = (TextView) findViewById(R.id.Title);
        textView123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return123(v);
            }
        });


        try {
//            List a = getFilesAllName("/data/user/0/com.example.myapplication/files");
            List a = getFilesAllName(getFilesDir().toString());
            String acd = temp[position];
            FileInputStream fps = openFileInput(temp[position]);
            String title = temp[position];
            title = title.substring(1, title.length()-1);
            textViewTitle.setText(title);
            InputStreamReader isr = new InputStreamReader(fps, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sBuilder.append(line);
            }
            System.out.println("zmy");
            String c = sBuilder.toString();
            c = c.replace("\\u003C","<");
            c = c.replace("\\n<","<");
            c = c.substring(1,c.length()-1);
            Html.ImageGetter imgGetter = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    Log.i("RG", "source---?>>>" + source);
                    Drawable drawable = null;
                    URL url;
                    try {
                        url = new URL(source);
                        Log.i("RG", "url---?>>>" + url);
                        drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                    Log.i("RG", "url---?>>>" + url);
                    return drawable;
                }
            };
//            textView.setText(c);
            System.out.println(c);

            String arr2 = c.replace("\\\"","\"");
            String arr3 = "<p><img src=\"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3660975586,3586398751&amp;fm=26&amp;gp=0.jpg\" alt=\"\" width=\"500px\" height=\"728px\" /></p>";

            String regex = "\\d+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(arr2);
            ArrayList<String> al=new ArrayList<String>();

            while (m.find()) {
                al.add(m.group(0));
            }
            System.out.println("去除重复值前");
            for (int i=0;i<al.size();i++)
            {
                System.out.println(al.get(i).toString());
            }
            System.err.println(al.toString());

//            textView.setText(Html.fromHtml(arr2));
//            Spanned sp = Html.fromHtml(arr2, new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(String source) {
//                    InputStream is = null;
//                    try {
//                        is = (InputStream) new URL(source).getContent();
//                        Drawable d = Drawable.createFromStream(is, "src");
//                        d.setBounds(0, 0, d.getIntrinsicWidth(),
//                                d.getIntrinsicHeight());
//                        is.close();
//                        return d;
//                    } catch (Exception e) {
//                        return null;
//                    }
//                }
//            }, null);
//            textView.setText(sp);

            int width,height;

            if(arr2.contains("img")) {
                width = (int) (Integer.valueOf(al.get(al.size()-2).toString()) *2);
                height = (int) (Integer.valueOf(al.get(al.size()-1).toString()) * 2);
            } else {
                width = 1400;
                height = 1400;
            }




            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Spanned sp = Html.fromHtml(arr2, new Html.ImageGetter() {
                        @Override
                        public Drawable getDrawable(String source) {
                            InputStream is = null;
                            try {
                                is = (InputStream) new URL(source).getContent();
                                Drawable d = Drawable.createFromStream(is, "src");
                                d.setBounds(0, 0,width,height);
                                is.close();
                                return d;
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    }, null);
//方式一
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_test.setText(sp);
//                        }
//                    });
//方式二
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(sp);
                    }
                }); }
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
// TODO Auto-generated method stub
        String string=(String) parent.getItemAtPosition(position);
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
    public void return123(View v){
        Intent intent = new Intent(this,FirstFragment.class);
        startActivity(intent);
    }
    private String[] getData(){
        List a = getFilesAllName("/data/user/0/com.example.myapplication/files");

        String[] strs1= (String[]) a.toArray(new String[a.size()]);
        for(int i=0;i<strs1.length;i++){
            strs1[i] = strs1[i].substring(45,strs1[i].length());
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