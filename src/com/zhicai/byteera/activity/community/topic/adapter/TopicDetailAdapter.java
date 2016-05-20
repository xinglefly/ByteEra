package com.zhicai.byteera.activity.community.topic.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.topic.entity.OpinionCommentEntity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionEntity;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.activity.community.topic.entity.TopicItemTitleEntity;
import com.zhicai.byteera.activity.community.topic.presenter.TopicDetailPre;
import com.zhicai.byteera.activity.community.topic.view.TopicItemView;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.service.Common;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/5/28. */
public class TopicDetailAdapter extends BaseAdapter {
    private static final int TOPIC_TYPE = 0;
    private static final int LIST_TYPE = 1;
    private static final int TITLE_TYPE = 2;
    private Context mContext;
    private List mList;
    private TopicDetailPre topicDetailPre;
    public TopicDetailAdapter(Context mContext, TopicDetailPre topicDetailPre) {
        this.mContext = mContext;
        mList = new ArrayList();
        this.topicDetailPre = topicDetailPre;
    }

    public void setData(List list) {
        this.mList = list;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    public void addComment(Common.Comment comment, int position) {  //把评论内容添加进去 没有重新请求网络进行数据的刷新
        if (mList.get(position) instanceof OpinionEntity) {
            OpinionEntity opinionEntity = (OpinionEntity) mList.get(position);
            OpinionCommentEntity opinionCommentEntity = ModelParseUtil.OpinionCommentEntityParse(comment);
            opinionEntity.getOpinionCommentEntities().add(opinionCommentEntity);
            opinionEntity.setCommentNum(opinionEntity.getCommentNum() + 1);
            this.notifyDataSetChanged();
        }
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        mList.get(position);
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case TOPIC_TYPE:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_detail_content, null);
                    break;
                case LIST_TYPE:
                    convertView = new TopicItemView(mContext, topicDetailPre);
                    break;
                case TITLE_TYPE:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_item_title, null);
            }
        }
        switch (getItemViewType(position)) {
            case TOPIC_TYPE:
                setTopicContent(position, convertView);
                break;
            case LIST_TYPE:
                OpinionEntity opinionEntity = (OpinionEntity) mList.get(position);
                ((TopicItemView) convertView).freshView(opinionEntity, position);
                break;
            case TITLE_TYPE:
                setItemTitleContent(position, convertView);
        }
        return convertView;
    }

    private void setItemTitleContent(int position, View convertView) {
        TopicItemTitleEntity entity = (TopicItemTitleEntity) mList.get(position);
        final boolean shouqi = topicDetailPre.IsShouqi();
        ImageView ivRedu = ViewHolder.get(convertView, R.id.iv_redu);
        TextView tvShouqi = ViewHolder.get(convertView, R.id.tv_shouqi);
        if (entity.getItemType() == 0) {
            tvShouqi.setVisibility(View.VISIBLE);
            ivRedu.setImageResource(R.drawable.hot_recommend);
            if (shouqi) {
                tvShouqi.setText("展开");
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.iconfont_zhankai);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                }
                tvShouqi.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.iconfont_shouqi);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                }
                tvShouqi.setCompoundDrawables(drawable, null, null, null);
                tvShouqi.setText("收起");
            }
        } else {
            tvShouqi.setVisibility(View.GONE);
            ivRedu.setImageResource(R.drawable.new_recommend);
        }
        tvShouqi.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (shouqi) {
                    topicDetailPre.setShouqi(false);
                } else {
                    topicDetailPre.setShouqi(true);
                }
            }
        });
    }


    private void setTopicContent(int position, View convertView) {
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_item_title);
        TextView tvContent = ViewHolder.get(convertView, R.id.tv_content);
        GridView mGridView = ViewHolder.get(convertView, R.id.grid_view);

        TopicEntity data = (TopicEntity) mList.get(position);
        tvTitle.setText(data.getTopicName());
        tvContent.setText(data.getTopicContent());
        List<String> imgList = data.getImgList();
        mGridView.setAdapter(new MyGridViewAdapter(imgList));
    }

    @Override public int getItemViewType(int position) {
        if (mList.get(position) instanceof TopicEntity) {
            return TOPIC_TYPE;
        } else if (mList.get(position) instanceof OpinionEntity) {
            return LIST_TYPE;
        } else {
            return TITLE_TYPE;
        }
    }

    @Override public int getViewTypeCount() {
        return 3;
    }

    public void removeAllViews() {
        mList.clear();
    }

    public void addItem(Object item) {
        mList.add(item);
    }

    public void addAllItem(List<Object> items) {
        mList.addAll(items);
    }

    public void setZaninig(boolean zaning, int position) {      //改变点赞状态,只是修改了内存数据 并没有重新请求网络
        if (mList.get(position) instanceof OpinionEntity) {
            OpinionEntity opinionEntity = (OpinionEntity) mList.get(position);
            opinionEntity.setIsZaning(zaning);
            if (zaning) {
                opinionEntity.setHotHum(opinionEntity.getHotHum() + 1);
            } else {
                opinionEntity.setHotHum(opinionEntity.getHotHum() - 1 >= 0 ? opinionEntity.getHotHum() - 1 : 0);
            }
            this.notifyDataSetChanged();
        }
    }


    private class MyGridViewAdapter extends BaseAdapter {
        private List<String> mList;

        public MyGridViewAdapter(List<String> imgList) {
            this.mList = imgList;
        }

        @Override public int getCount() {
            return mList.size();
        }

        @Override public Object getItem(int position) {
            return null;
        }

        @Override public long getItemId(int position) {
            return 0;
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, null);
            }

            ImageLoader.getInstance().displayImage(mList.get(position), (ImageView) convertView);
            return convertView;
        }
    }


}
