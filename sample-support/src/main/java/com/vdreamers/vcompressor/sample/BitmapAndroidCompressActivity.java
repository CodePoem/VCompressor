package com.vdreamers.vcompressor.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapAndroidCompressActivity extends AppCompatActivity {

    private ImageView ivRaw;
    private TextView tvRaw;
    private ImageView ivCompressQuality;
    private TextView tvCompressQuality;
    private ImageView ivCompressSizeInSampleSize;
    private TextView tvCompressSizeInSampleSize;

    BitmapFactory.Options options;
    private String TAG = BitmapAndroidCompressActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_android_compress);

        ivRaw = findViewById(R.id.iv_raw);
        tvRaw = findViewById(R.id.tv_raw);
        ivCompressQuality = findViewById(R.id.iv_compress_quality);
        tvCompressQuality = findViewById(R.id.tv_compress_quality);
        ivCompressSizeInSampleSize = findViewById(R.id.iv_compress_size_in_sample_size);
        tvCompressSizeInSampleSize = findViewById(R.id.tv_compress_size_in_sample_size);

        options = new BitmapFactory.Options();
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.love, options);

        int loveLength = 0;
        @SuppressLint("ResourceType")
        InputStream is = getResources().openRawResource(R.drawable.love);
        try {
            loveLength = is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivRaw.setImageBitmap(bitmap);
        tvRaw.setText("bitmap = " + bitmap + "\nByteCount = " + bitmap.getByteCount() +
                "\nAllocationByteCount = " + bitmap.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmap.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity +
                "\n物理大小 = " + loveLength);

        File file = compressBitmap(bitmap, BitmapAndroidCompressActivity.this, R.string.compress_file);
        Bitmap bitmapCompressQuality = BitmapFactory.decodeFile(file.getAbsolutePath());

        ivCompressQuality.setImageBitmap(bitmapCompressQuality);
        tvCompressQuality.setText("bitmap = " + bitmapCompressQuality + "\nByteCount = " + bitmapCompressQuality.getByteCount() +
                "\nAllocationByteCount = " + bitmapCompressQuality.getAllocationByteCount() +
                "\n物理大小 = " + file.length());

        options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        final Bitmap bitmapCompressSizeInSampleSize = BitmapFactory.decodeResource(getResources(), R.drawable.love, options);

        ivCompressSizeInSampleSize.setImageBitmap(bitmap);
        tvCompressSizeInSampleSize.setText("bitmap = " + bitmapCompressSizeInSampleSize + "\nByteCount = " + bitmapCompressSizeInSampleSize.getByteCount() +
                "\nAllocationByteCount = " + bitmapCompressSizeInSampleSize.getAllocationByteCount() +
                "\ninMutable = " + options.inMutable + " bitmap.isMutable = " + bitmap.isMutable() +
                "\ninScaled = " + options.inScaled +
                "\ninSampleSize = " + options.inSampleSize +
                "\ninDensity = " + options.inDensity +
                "\ninTargetDensity = " + options.inTargetDensity +
                "\ninScreenDensity = " + options.inScreenDensity);
    }

    private File compressBitmap(Bitmap bitmap, Context context, int resId){
        FileOutputStream out = null;
        try {
            String originalFilePath = context.getResources().getString(resId);
            File originalFile = new File(originalFilePath);
                String fileFullName = originalFile.getName();
            String fileTitle = fileFullName.substring(0, fileFullName.lastIndexOf("."));

            File externalFilesDir = context.getExternalFilesDir(null);
            File outputFile = new File(externalFilesDir, fileTitle + "_generated.png");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            out = new FileOutputStream(outputFile, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            if (out != null) {
                out.close();
            }
            Log.v(TAG, "Write test No." + outputFile.getAbsolutePath() + " to file successfully.");
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
