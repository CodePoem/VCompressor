package com.vdreamers.vcompressor.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


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
