package com.vdreamers.vcompressor.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class BitmapFactoryOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_factory_option);

        findViewById(R.id.tv_inbitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BitmapFactoryOptionActivity.this,
                        InBitmapActivity.class);
                startActivity(intent);
            }
        });
    }
}
