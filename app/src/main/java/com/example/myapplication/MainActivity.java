package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {

//    private WebView webview;
//private android.webkit.valuecallback<Uri[]> muploadcallbackabovel;
//    private android.webkit.valuecallback<Uri> muploadcallbackbelow;
//    private Uri imageuri;
//    private int request_code = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        WebView webView= (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage
        webView.setWebChromeClient(new MyWebChromeClient());

        try {//跨域设置
            Class<?> clazz = webView.getSettings().getClass();
            Method method = clazz.getMethod(
                    "setAllowUniversalAccessFromFileURLs", boolean.class);//利用反射机制去修改设置对象
            if (method != null) {
                method.invoke(webView.getSettings(), true);//修改设置
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        webView.loadUrl("http://47.97.77.184:5000/");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
//        webview = new WebView(this);
//        webview.getSettings().setJavaScriptEnabled(true);
//
//        try {
//            webview.loadUrl("http://www.baidu.com");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        setContentView(webview);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        Button btn = findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "R123123", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        Button btn1 = (Button)findViewById(R.id.open_url);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri= Uri.parse("https://www.baidu.com");
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
//            }
//        });

        Button bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                webView.evaluateJavascript("javascript:this.getTitle()",value -> {
                    webView.evaluateJavascript("javascript:this.getContent()",value2 -> {
                        try {
                            FileOutputStream fos = openFileOutput(value, MODE_APPEND);
                            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

                            String origin = value2.toString();
                            showToast(v);
//                            osw.write("Hellow");
                            // Convert from Unicode to UTF-8
                            osw.write(origin);
                            osw.flush();
                            fos.flush();
                            osw.close();
                            fos.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                });
            }
        });



        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("javascript:this.getTitle()",value -> {
                    prew(v);
//                    try {
//                        List a = getFilesAllName(getFilesDir().toString());
//                        String b = getFilesDir().toString();
//                        System.out.println(a);
//                        FileInputStream fps = openFileInput(value);
//                        InputStreamReader isr = new InputStreamReader(fps, "UTF-8");
//                        BufferedReader br = new BufferedReader(isr);
//                        StringBuilder sBuilder = new StringBuilder();
//                        String line = "";
//                        while ((line = br.readLine()) != null) {
//                            sBuilder.append(line);
//                        }
//                        System.out.println(value);
//                        System.out.println(sBuilder.toString());
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                });
            }
        });

    }

    public void showToast(View v){
        Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
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

    public void prew(View v){
        Intent intent = new Intent(this,FirstFragment.class);
        startActivity(intent);
    }

    private ValueCallback<Uri> mUploadMessage;
    private String mTag = "MyWebChromeClient";
    private final static int FILECHOOSER_RESULTCODE = 101;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    class MyWebChromeClient extends WebChromeClient {

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.d(mTag, "openFileChoose(ValueCallback<Uri> uploadMsg)");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            Log.d(mTag, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
                MainActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
        }
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.d(mTag, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
        }
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            return true;
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void handlesyp(View view){
        System.out.println("你好");
    }

}