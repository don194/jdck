package com.dh.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDckActivity extends AppCompatActivity {
    // 获取意图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdck);
        Intent intent = getIntent();
        //获取传递的值
        String cookies = intent.getStringExtra("cookies");
        //处理cookie
        String user = "",key = "",username = "";
        System.out.println("!"+cookies);
        String pattern1 ="(pt_pin=([^ ]*;))";
        String pattern2 ="(pt_key=[^ ]*;)";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher u1 = r1.matcher(cookies);
        if(u1.find())  {
            username = " "+u1.group(2);
            System.out.println(username+"???");
            user = u1.group(0);
        }
        Pattern r2 = Pattern.compile(pattern2);
        Matcher u2 = r2.matcher(cookies);
        if(u2.find())  key = u2.group(0);
        String scookie = user + "\n" + key;
        //现实到前端
        TextView usertext = findViewById(R.id.textView10);
        usertext.setText(username);
        TextView cookiestext = findViewById(R.id.textView12);
        cookiestext.setText(scookie);
        TextView ruletext = findViewById(R.id.textView13);
        ruletext.setMovementMethod(ScrollingMovementMethod.getInstance());

        //复制cookies按钮
        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器
                ClipboardManager ck = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", scookie);
                // 将ClipData内容放到系统剪贴板里
                ck.setPrimaryClip(mClipData);
                Toast.makeText(JDckActivity.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
            }
        });
        // 跳转qq
        //QQ群代码
        String GroupKey = "hW9qoWAsYdD8jNh1p30zxUj_N_RrQKPc";
        Button button2 = (Button) findViewById(R.id.button5);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器
                ClipboardManager ck = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", scookie);
                // 将ClipData内容放到系统剪贴板里
                ck.setPrimaryClip(mClipData);
                Toast.makeText(JDckActivity.this, "已复制到剪切板正在转跳QQ", Toast.LENGTH_SHORT).show();

                try {
                    String url = "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + GroupKey;
                    //uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(JDckActivity.this, "转跳失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //清除cookie登录下一个帐号
        Button button3 = (Button) findViewById(R.id.button6);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JDckActivity.this, MainActivity.class);
                intent.putExtra("clean", true);
                startActivity(intent);
                finish();
                // Do something in response to button click
            }
        });

    }
}