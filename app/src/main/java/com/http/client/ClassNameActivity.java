package com.http.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ClassNameActivity extends AppCompatActivity {
    private static final String TAG = ClassNameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_name);
        Intent intent = getIntent();
        Log.e(TAG, "onCreate: =======" + intent.getComponent());
    }
}
