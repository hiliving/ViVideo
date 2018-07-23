package com.bftv.myapplication.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.webview.X5WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Helloworld on 2018/7/21.
 */

public class LoadHtmlWebview extends AppCompatActivity {


    private X5WebView mWebView;
    private WebView root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_html);
        final  String url = getIntent().getStringExtra(KeyParam.PLAYURL);


//        final String url = "http://jqaaa.com/jq3/?url=https://v.youku.com/v_show/id_XMzY2MDI5NDM0MA==.html?spm=a2h0j.11185381.listitem_page1.5~A&&s=dca053efbfbd36efbfbd";

        mWebView = findViewById(R.id.root);

        com.tencent.smtt.sdk.WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		webSetting.setSupportZoom(true);
//		webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36");
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);
        mWebView.setInitialScale(-150);
        mWebView.loadUrl(url);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    Element title = document.select("title").first();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public class NoAdWebViewClient extends WebViewClient {

        private Context context;

        public NoAdWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            blockAd(url);
        }

    }

    private void blockAd(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("javascript: ");
        String[] alltag = url.split(",");
        for (int i = 0; i < alltag.length; i++) {
            String adTag = alltag[i];
            if (adTag.trim().length() > 0) {
                adTag = adTag.trim();
                if (adTag.contains("#")) {
                    adTag = adTag.substring(adTag.indexOf("#") + 1);
                    if (Build.VERSION.SDK_INT <  Build.VERSION_CODES.KITKAT) {//19
                        sb.append("document.getElementById(\'").append(adTag).append("\').innerHTML=\'\';");
                    } else {
                        sb.append("document.getElementById(\'").append(adTag).append("\').remove();");
                    }

                } else if (adTag.contains(".")) {
                    adTag = adTag.substring(adTag.indexOf(".") + 1);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        sb.append("var esc=document.getElementsByClassName(\'").append(adTag).append("\');for (i in esc){esc[i].innerHTML=\'\';};");

                    } else {
                        sb.append("var esc=document.getElementsByClassName(\'").append(adTag).append("\');for (var i = esc.length - 1; i >= 0; i--){esc[i].remove();};");

                    }
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    sb.append("var esc=document.getElementsByTagName(\'").append(adTag).append("\');for (i in esc){esc[i].innerHTML=\'\';};");
                } else {
                    sb.append("var esc=document.getElementsByTagName(\'").append(adTag).append("\');for (var i = esc.length - 1; i >= 0; i--){esc[i].remove();};");
                }
            }
        }
    }

}
