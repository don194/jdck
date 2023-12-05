package com.dh.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //是否清cookies
        Intent intent = getIntent();
        //获取传递的值
        System.out.println("!!" +intent);
        //QQ转跳
        String url_q = null;
        if(intent.getData()!=null)url_q = intent.getData().toString();
        System.out.println("qq转跳的链接" + url_q);
        boolean cleancookies;
        if (intent.getBooleanExtra("clean",false)) cleancookies = true;
        else cleancookies = false;

        WebView webView = (WebView) findViewById(R.id.jd_webview);
        if(cleancookies) {
            CookieSyncManager.createInstance(this);
            CookieSyncManager.getInstance().startSync();
            CookieManager.getInstance().removeSessionCookie();
            CookieManager.getInstance().removeAllCookie();
            CookieSyncManager.getInstance().sync();
            webView.clearCache(true);
        }
        //js
        // 启用JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                System.out.println("网址" + url);
                if (url.startsWith("wtloginmqq://ptlogin/qlogin")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    finish();
                }
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // 监听 WebView 页面加载完成事件
                String cookies = CookieManager.getInstance().getCookie(url);
                System.out.println("Cookies: " + cookies);
                if (cookies!=null && cookies.contains("pt_key")) {
                        // 处理获取到的 Cookie
                        // 例如，将其打印到日志中
                    System.out.println("Cookies after login: " + cookies);
                    //登录成功后跳转到下一个 Activity
                    Intent intent = new Intent(MainActivity.this, JDckActivity.class);
                    intent.putExtra("cookies", cookies);
                    startActivity(intent);
                    finish();
                 }
                }
            //访问登录页面
        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) { // 表示按返回键
                        // 时的操作
                        webView.goBack(); // 后退
                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
        if(url_q!=null && url_q.contains("ssl.ptlogin2.qq.com")){
            webView.loadUrl(url_q);
        }
        else
        webView.loadUrl("https://bean.m.jd.com/bean/signIndex.action");
        }

    }
