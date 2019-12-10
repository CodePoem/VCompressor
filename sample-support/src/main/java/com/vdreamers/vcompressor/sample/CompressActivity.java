package com.vdreamers.vcompressor.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vdreamers.vcompressor.CompressUtils;
import com.vdreamers.vcompressor.listener.CompressSimpleListener;
import com.vdreamers.vcompressor.sample.adapter.MediaGridInset;
import com.vdreamers.vcompressor.sample.adapter.MediaSelectResultsAdapter;
import com.vdreamers.vcompressor.sample.custom.DefaultMediaSelectorImpl;
import com.vdreamers.vmediaselector.MediaSelectFilesCallback;
import com.vdreamers.vmediaselector.MediaSelectorUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompressActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MediaSelectResultsAdapter mAdapter;
    private ArrayList<String> mImagePathList;
    private ArrayList<Uri> mImageUris;

    private static final long MAX_GIF_SIZE = 2 * 1024 * 1024L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);

        mRecyclerView = findViewById(R.id.rv_image);
        mAdapter = new MediaSelectResultsAdapter(mRecyclerView);
        mAdapter.setItemDeleteListener(new MediaSelectResultsAdapter.ItemDeleteListener() {
            @Override
            public void onDelete(int position) {
                if (mImageUris != null) {
                    mImageUris.remove(position);
                }
                if (mImagePathList != null) {
                    mImagePathList.remove(position);
                    if (mAdapter != null) {
                        mAdapter.setList(mImagePathList);
                    }
                }
            }
        });
        int spacing = getResources().getDimensionPixelSize(R.dimen.image_grid_spacing);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MediaGridInset(3, spacing, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.tv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择图片
                selectImage();
            }
        });
    }

    private void selectImage() {
        MediaSelectorUtils.of(new DefaultMediaSelectorImpl())
                .setNeedCamera(true)
                .setMultiSelectable(true)
                .setNeedGif(true)
                .setMaxGifSize(MAX_GIF_SIZE)
                .selectImage(CompressActivity.this, new MediaSelectFilesCallback() {
                    @Override
                    public void onSuccess(int resultCode, Intent data, List<Uri> uris,
                                          List<File> files) {
                        if (mAdapter == null || mRecyclerView == null) {
                            return;
                        }
                        mImageUris = (ArrayList<Uri>) uris;
                        CompressUtils.of()
                                .setCompressListener(new CompressSimpleListener() {
                                    @Override
                                    public void onSuccess(List<File> compressedFiles) {
                                        if (mImagePathList == null) {
                                            mImagePathList = new ArrayList<>();
                                        }
                                        mImagePathList.clear();
                                        for (File compressedFile : compressedFiles) {
                                            mImagePathList.add(compressedFile.getAbsolutePath());
                                        }
                                        mAdapter.setList(mImagePathList);
                                    }

                                    @Override
                                    public void onFailed(Throwable throwable) {

                                    }
                                })
                                .compress(CompressActivity.this, files);
                    }

                    @Override
                    public void onFailed(Throwable mediaSelectError) {

                    }
                });
    }
}
