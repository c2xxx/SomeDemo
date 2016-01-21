package com.basedamo.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basedamo.R;
import com.basedamo.data.VoiceTalk;

import java.util.List;

/**
 * Created by hui on 2016/1/20.
 */
public class VoiceTalkAdapter extends BaseAdapter {

    private Context mContext;
    private List<VoiceTalk> mList;
    private VoickeItemClickListener voickeItemClickListener;
    private int playingPosition = -1;//当前播放的位置，用于播放时播放动画

    public VoiceTalkAdapter(Context mContext, List<VoiceTalk> mList, VoickeItemClickListener voickeItemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.voickeItemClickListener = voickeItemClickListener;
    }

    /**
     * @param playingPosition 播放时播放动画的位置
     */
    public void setPlayingPosition(int playingPosition) {
        this.playingPosition = playingPosition;
    }

    /**
     * 清除播放时播放动画的位置
     */
    public void clearPlayingPosition() {
        this.playingPosition = -1;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_voicetalk, null);
            convertView.setTag(vh);
            vh.initView(convertView);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final VoiceTalk voiceTalk = mList.get(position);
        vh.tvTime.setText(voiceTalk.getTime() + "''");
        if (playingPosition == position) {
            vh.ivImage.setBackgroundResource(R.drawable.bg_play_voice);
            AnimationDrawable drawable = (AnimationDrawable) vh.ivImage.getBackground();
            drawable.start();
        } else {
            vh.ivImage.setBackgroundResource(R.drawable.adj);
        }
        vh.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voickeItemClickListener.onClick(vh.ivImage, voiceTalk, position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tvTime;
        ImageView ivImage;
        LinearLayout llLayout;

        public void initView(View view) {
            tvTime = (TextView) view.findViewById(R.id.tv_item_voicetalk_time);
            ivImage = (ImageView) view.findViewById(R.id.iv_item_voicetalk_image);
            llLayout = (LinearLayout) view.findViewById(R.id.ll_item_voicetalk);
        }
    }

    public interface VoickeItemClickListener {
        void onClick(ImageView imageView, VoiceTalk voiceTalk, int position);
    }
}
