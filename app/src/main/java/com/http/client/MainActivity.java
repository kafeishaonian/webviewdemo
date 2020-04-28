package com.http.client;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();

    }

    static class A {
        protected int value;

        public A(int v) {
            setValue(v);
        }
        public void setValue(int value) {
            this.value = value;
        }
        public int getValue() {
            try {
                value++;
                return value;
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                this.setValue(value);
                System.out.println(value);
            }
            return value;
        }
    }
    static class B extends A {
        public B() {
            super(5);
            setValue(getValue() - 3);
        }
        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }


    private void initView(){
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri number = Uri.parse("qla://www.hongming.com?action=101");
//                Intent intent = new Intent(Intent.ACTION_VIEW, number);
////                intent.setAction("qla://www.qilin99.com?action=101");
//                startActivity(intent);
//                System.out.println(new B().getValue() +"");
                startActivity(new Intent(MainActivity.this, ClassNameActivity.class));
            }
        });
//
//
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
//            }
//        });
    }



    public static MainActivity getInstance() {
        return instance;
    }
}
