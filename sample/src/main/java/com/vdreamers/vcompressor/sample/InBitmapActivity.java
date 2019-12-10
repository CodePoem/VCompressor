package com.vdreamers.vcompressor.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class InBitmapActivity extends AppCompatActivity {

    private ImageView ivRaw;
    private TextView tvRaw;
    private ImageView ivInBitmap;
    private TextView tvInBitmap;
    private ImageView ivNoInBitmap;
    private TextView tvNoInBitmap;
    private ImageView ivFreeInBitmap;
    private TextView tvFreeInBitmap;

    RadioGroup rgInMutable;
    RadioGroup rgInScaled;
    EditText etInSampleSize;
    EditText etInDensity;
    EditText etInTargetDensity;
    EditText etInScreenDensity;
    TextView tvTryInBitmap;

    BitmapFactory.Options options;

    /**
     * 默认inSampleSize
     */
    public static final int DEFAULT_FREE_IN_SAMPLE_SIZE = 4;
    /**
     * 默认inDensity
     */
    public static final int DEFAULT_FREE_IN_DENSITY = 160;
    /**
     * 默认inTargetDensity
     */
    public static final int DEFAULT_FREE_IN_TARGET_DENSITY = 160;
    /**
     * 默认inScreenDensity
     */
    public static final int DEFAULT_FREE_IN_SCREEN_DENSITY = 180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_bitmap);

        ivRaw = findViewById(R.id.iv_raw);
        tvRaw = findViewById(R.id.tv_raw);
        ivInBitmap = findViewById(R.id.iv_in_bitmap);
        tvInBitmap = findViewById(R.id.tv_in_bitmap);
        ivNoInBitmap = findViewById(R.id.iv_no_in_bitmap);
        tvNoInBitmap = findViewById(R.id.tv_no_in_bitmap);
        ivFreeInBitmap = findViewById(R.id.iv_free_in_bitmap);
        tvFreeInBitmap = findViewById(R.id.tv_free_in_bitmap);

        options = new BitmapFactory.Options();
        // 设置解码后bitmap可变性 复用bitmap要求为可变
        options.inMutable = true;
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.love, options);

        ivRaw.setImageBitmap(bitmap);
        tvRaw.setText("bitmap = " + bitmap + "\nByteCount = " + bitmap.getByteCount() +
                "\nAllocationByteCount = " + bitmap.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmap.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity);


        // 设置复用的bitmap
        options.inBitmap = bitmap;
        // 设置解码后bitmap可变性 被复用的bitmap要求为可变 如果复用了bitmap 不管inMutable设置为什么 解码后的bitmap都是可变的
        options.inMutable = false;
        options.inSampleSize = 2;
        Bitmap bitmapReuse = BitmapFactory.decodeResource(getResources(), R.drawable.love, options);

        ivInBitmap.setImageBitmap(bitmapReuse);
        tvInBitmap.setText("bitmap = " + bitmapReuse + "\nByteCount = " + bitmapReuse.getByteCount() +
                "\nAllocationByteCount = " + bitmapReuse.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmapReuse.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity);


        // 设置复用的bitmap
        options.inBitmap = null;
        // 设置解码后bitmap可变性 复用bitmap要求为可变
        options.inMutable = false;
        Bitmap bitmapImmutable = BitmapFactory.decodeResource(getResources(), R.drawable.love,
                options);

        // 设置复用的bitmap
        options.inBitmap = bitmapImmutable;
        // 设置解码后bitmap可变性 复用bitmap要求为可变
        options.inMutable = false;
        options.inSampleSize = 2;
        Bitmap bitmapNoReuse = BitmapFactory.decodeResource(getResources(), R.drawable.love,
                options);

        ivNoInBitmap.setImageBitmap(bitmapNoReuse);
        tvNoInBitmap.setText("bitmap = " + bitmapNoReuse + "\nByteCount = " + bitmapNoReuse.getByteCount() +
                "\nAllocationByteCount = " + bitmapNoReuse.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmapNoReuse.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity);

        rgInMutable = findViewById(R.id.rg_in_mutable);
        rgInMutable.check(R.id.rb_in_mutable_true);
        rgInScaled = findViewById(R.id.rg_in_scaled);
        rgInScaled.check(R.id.rb_in_scaled_true);
        etInSampleSize = findViewById(R.id.et_in_sample_size);
        etInDensity = findViewById(R.id.et_in_density);
        etInTargetDensity = findViewById(R.id.et_in_target_density);
        etInScreenDensity = findViewById(R.id.et_in_screen_density);

        tvTryInBitmap = findViewById(R.id.tv_try_in_bitmap);

        etInSampleSize.setText(String.valueOf(DEFAULT_FREE_IN_SAMPLE_SIZE));
        etInDensity.setText(String.valueOf(DEFAULT_FREE_IN_DENSITY));
        etInTargetDensity.setText(String.valueOf(DEFAULT_FREE_IN_TARGET_DENSITY));
        etInScreenDensity.setText(String.valueOf(DEFAULT_FREE_IN_SCREEN_DENSITY));

        tvTryInBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryInBitmap(bitmap);
            }
        });
    }

    private void tryInBitmap(Bitmap bitmap) {
        String inSampleSizeNum = etInSampleSize.getText().toString();
        String inDensityNum = etInDensity.getText().toString();
        String inTargetDensityNum = etInTargetDensity.getText().toString();
        String inScreenDensityNum = etInScreenDensity.getText().toString();

        // 设置复用的bitmap
        options.inBitmap = bitmap;
        // 设置解码后bitmap可变性 被复用的bitmap要求为可变 如果复用了bitmap 不管inMutable设置为什么 解码后的bitmap都是可变的
        options.inMutable = rgInMutable.getCheckedRadioButtonId() == R.id.rb_in_mutable_true;
        options.inScaled = rgInScaled.getCheckedRadioButtonId() == R.id.rb_in_scaled_true;
        options.inSampleSize = Integer.valueOf(TextUtils.isEmpty(inSampleSizeNum) ? "0" :
                inSampleSizeNum);
        options.inDensity = Integer.valueOf(TextUtils.isEmpty(inDensityNum) ? "0" : inDensityNum);
        options.inTargetDensity = Integer.valueOf(TextUtils.isEmpty(inTargetDensityNum) ? "0" :
                inTargetDensityNum);
        options.inScreenDensity = Integer.valueOf(TextUtils.isEmpty(inScreenDensityNum) ? "0" :
                inScreenDensityNum);
        Bitmap bitmapFreeReuse = BitmapFactory.decodeResource(getResources(), R.drawable.love,
                options);

        ivFreeInBitmap.setImageBitmap(bitmapFreeReuse);
        tvFreeInBitmap.setText("bitmap = " + bitmapFreeReuse + "\nByteCount = " + bitmapFreeReuse.getByteCount() +
                "\nAllocationByteCount = " + bitmapFreeReuse.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmapFreeReuse.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity);
    }
}
