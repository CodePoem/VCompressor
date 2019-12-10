package com.vdreamers.vcompressor.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompressActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_bitmap_factory_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BitmapFactoryOptionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_bitmap_android_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BitmapAndroidCompressActivity.class);
                startActivity(intent);
            }
        });
    }
}
