package com.bftv.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.bftv.myapplication.ParseWebUrlHelper;
import com.bftv.myapplication.PlayActivity;
import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.util.DensityUtil;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class WebCrossActivity extends AppCompatActivity {

    private WebView web;
    private WebView tweb;
    private Intent intent;
    private Button geturl;
    private ParseWebUrlHelper parseWebUrlHelper;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==111){

            }
        }
    };
    private String[] headUrl = {
      "https://beaacc.com/api.php?url=",//万能接口1
      "http://jx.598110.com/duo/index.php?url=",//万能接口2
      "http://jiexi.071811.cc/jx2.php?url=",//万能接口3
      "http://jqaaa.com/jq3/?url=&url=",//万能接口4
      "http://api.91exp.com/svip/?url=",//万能接口5
      "https://jiexi.071811.cc/jx2.php?url=",//万能接口6
      "http://www.82190555.com/index/qqvod.php?url=",//腾讯视频
      "http://api.pucms.com/?url=",//爱奇艺1
      "http://api.baiyug.cn/vip/index.php?url=",//爱奇艺2
      "https://api.flvsp.com/?url=",//爱奇艺3
      "http://api.xfsub.com/index.php?url=",//芒果TV
    };
    private String[] urlName = {
      "万能接口1",
      "万能接口2",
      "万能接口3",
      "万能接口4",
      "万能接口5",
      "万能接口6",
      "腾讯视频",
      "爱奇艺1",
      "爱奇艺2",
      "爱奇艺3",
      "芒果TV"
    };
    private PopSpinnerView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_cross_layout);

        web = findViewById(R.id.web);
        listView = findViewById(R.id.psv_list);

        tweb = findViewById(R.id.testweb);
        geturl = findViewById(R.id.getUrl);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(getIntent().getStringExtra(KeyParam.BASEURL));
        intent = new Intent(WebCrossActivity.this,PlayActivity.class);

        listView.init(headUrl.length, DensityUtil.dip2px(this, 180), new PopSpinnerView.NameFilter() {
            @Override
            public String filter(int position) {
                return urlName[position];
            }
        });
       /* tweb.getSettings().setJavaScriptEnabled(true);
        tweb.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

                if (url.contains("-tt")||url.contains("videos")){
                    Log.e("eeeeeee",url);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           play.setText("资源准备就绪，点击播放");
                       }
                   });
                    intent.putExtra("URL",url);
                }
            }
        });
        tweb.setWebChromeClient(new WebChromeClient());*/
        geturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tweb.loadUrl("http://api.baiyug.cn/vip/index.php?url="+web.getUrl());

                int selectIndex = listView.getSelectIndex();
                if (selectIndex == -1) {
                    Toast.makeText(WebCrossActivity.this, "请选择线路", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(WebCrossActivity.this, LoadHtmlWebview.class);
                intent.putExtra(KeyParam.PLAYURL,headUrl[listView.getSelectIndex()]+web.getUrl());
                startActivity(intent);
            }
        });
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {  //表示按返回键
                        web.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}