package com.vdreamers.vcompressor.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vdreamers.vcompressor.sample.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 多媒体选择器结果适配器
 * <p>
 * date 2019-12-11 01:04:04
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public class MediaSelectResultsAdapter extends RecyclerView.Adapter {
    private ArrayList<String> mList;

    private ItemDeleteListener mItemDeleteListener;
    private RecyclerView mRecyclerView;
    private int mImageResize;

    public MediaSelectResultsAdapter(RecyclerView recyclerView) {
        this(null, recyclerView);
    }

    public MediaSelectResultsAdapter(ItemDeleteListener itemDeleteListener,
                                     RecyclerView recyclerView) {
        mList = new ArrayList<>();
        mItemDeleteListener = itemDeleteListener;
        mRecyclerView = recyclerView;
    }

    public void setList(List<String> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<String> getMediaUriList() {
        if (mList == null || mList.size() <= 0) {
            return null;
        }
        return mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_results_item, parent, false);
        int height = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(height);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MediaViewHolder) {
            MediaViewHolder mediaViewHolder = (MediaViewHolder) holder;
            String path = mList.get(position);

            Glide.with(mediaViewHolder.mImageView)
                    .load(path)
                    .placeholder(R.drawable.v_selector_ui_impl_ic_broken_image)
                    .override(getImageResize(mediaViewHolder.mImageView.getContext()),
                            getImageResize(mediaViewHolder.mImageView.getContext()))
                    .centerCrop()
                    .into(mediaViewHolder.mImageView);

            mediaViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemDeleteListener != null) {
                        mItemDeleteListener.onDelete(position);
                    }
                }
            });
        }
    }

    private int getImageResize(Context context) {
        if (mImageResize == 0) {
            RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) lm).getSpanCount();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int availableWidth = screenWidth - context.getResources().getDimensionPixelSize(
                    R.dimen.image_grid_spacing) * (spanCount - 1);
            mImageResize = availableWidth / spanCount;
        }
        return mImageResize;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setItemDeleteListener(ItemDeleteListener itemDeleteListener) {
        mItemDeleteListener = itemDeleteListener;
    }

    private class MediaViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        MediaViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_media_item);
            mTextView = itemView.findViewById(R.id.tv_delete);
        }
    }

    public interface ItemDeleteListener {
        /**
         * 删除
         *
         * @param position 点击位置索引
         */
        void onDelete(int position);
    }
}

