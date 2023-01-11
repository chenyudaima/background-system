package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局 （表示当前屏幕组件设置为activity_main布局）

        setContentView(R.layout.activity_main);

        //获取当前布局里面的控件TextView
        //注意:只能拿到当前布局里面的控件
        TextView textView = findViewById(R.id.TextView);

        //设置控件的text
        textView.setText("love wujilin");

        //获取当前布局里面的控件Button
        Button button = findViewById(R.id.button);

        //绑定点击事件
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this,MainActivity2.class);
            startActivity(intent);
        });


    }

}