package com.zhicai.byteera.activity.community.topic.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.vg.list.CustomAdapter;
import com.custom.vg.list.CustomListView;
import com.custom.vg.list.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.topic.entity.NormalUserEntity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionCommentEntity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionEntity;
import com.zhicai.byteera.activity.community.topic.presenter.TopicDetailPre;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/** Created by lieeber on 15/7/6. */
public class TopicItemView extends FrameLayout {
    private static final String TAG = TopicItemView.class.getSimpleName();

    private ViewGroup commentContainer;
    private CustomListView flowListView;
    private TextView tvName;
    private TextView tVContent;
    private TextView tvTime;
    private TextView tvPraiseCount;
    private TextView tvCommentCount1;
    private ImageView ivAvatar;
    private ImageView ivInstitution;
    private TextView tvInstitution;
    private ImageView ivRightBack;
    private View llContainer;
    private View rlComment;
    private TextView tvShowMoreComment;

    private TopicDetailPre topicDetailPre;
    private ImageView ivPraise;

    String nickName;
    int type = 0;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.topic_detail_item, this, true);
        commentContainer = ButterKnife.findById(view,R.id.comment_container);
        flowListView = ButterKnife.findById(view,R.id.flow_list_view);
        flowListView.setDividerHeight(UIUtils.dip2px(getContext(), 10));
        flowListView.setDividerWidth(UIUtils.dip2px(getContext(), 10));
        tvName = ButterKnife.findById(view,R.id.tv_item_title);
        tVContent = ButterKnife.findById(view,R.id.tv_content);
        tvTime = ButterKnife.findById(view,R.id.tv_item_time);
        tvPraiseCount = ButterKnife.findById(view,R.id.tv_item_praisecount);
        tvCommentCount1 = ButterKnife.findById(view,R.id.tv_item_revertvalue);
        ivAvatar = ButterKnife.findById(view,R.id.iv_avatar);
        ivInstitution = ButterKnife.findById(view,R.id.iv_institution);
        tvInstitution = ButterKnife.findById(view,R.id.tv_institution);
        ivRightBack = ButterKnife.findById(view,R.id.iv_rightback);
        tvShowMoreComment = ButterKnife.findById(view,R.id.tv_show_more_comment);
        ivPraise = ButterKnife.findById(view,R.id.img_praise);
        llContainer = findViewById(R.id.ll_cotainer);
        rlComment = findViewById(R.id.rl_comment);
    }

    public TopicItemView(Context context, TopicDetailPre topicDetailPre) {
        super(context);
        this.topicDetailPre = topicDetailPre;
        initView();
    }

    public void freshView(final OpinionEntity entity, final int position) {
        tvName.setText(entity.getOpinionName());
        tVContent.setText(entity.getContent());
        tvTime.setText(StringUtil.checkTime(((long) entity.getPublish_time()) * 1000));
        tvPraiseCount.setText("热度支持 " + String.valueOf(entity.getHotHum()));
        tvCommentCount1.setText(String.valueOf(entity.getCommentNum()));
        ImageLoader.getInstance().displayImage(entity.getOpinionAvatar(), ivAvatar);
        ivRightBack.setImageResource(entity.getInstitutionUserEntity()!=null?R.drawable.into:R.color.transparent);

        if (entity.getFinancingCompanyEntity()!=null){
            ImageLoader.getInstance().displayImage(entity.getFinancingCompanyEntity().getCompany_image(), ivInstitution);
            nickName = entity.getFinancingCompanyEntity().getCompany_name();
        }


        if (TextUtils.isEmpty(nickName)) {        //没有添加机构的时候机构不显示
            ((ViewGroup) tvInstitution.getParent()).setVisibility(View.GONE);
            flowListView.setVisibility(View.GONE);  //如果没有添加机构 也要把同样推荐的人的信息给隐藏
        } else {
            ((ViewGroup) tvInstitution.getParent()).setVisibility(View.VISIBLE);
            tvInstitution.setText(nickName);

            //显示又箭头，表示可以跳转机构详情页
            ((ViewGroup) tvInstitution.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //跳转到机构详情页
                    if (entity.getInstitutionUserEntity()!=null){
                        Intent intent = new Intent(getContext(), OrganizationHomeActivity.class);
                        intent.putExtra("other_user_id", entity.getInstitutionUserEntity().getUserId());
                        ActivityUtil.startActivity((Activity) getContext(), intent);
                    }else{
                        ToastUtil.showToastText("亲，该平台暂未开通主页，请持续关注！");
                    }
                }
            });
            final List<NormalUserEntity> normalUserEntities = entity.getNormalUserEntities();
            if (normalUserEntities.size() > 0) {
                flowListView.setVisibility(View.VISIBLE);
                ImageAdapter mAdapter = new ImageAdapter(normalUserEntities);
                flowListView.setAdapter(mAdapter);
                flowListView.setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            String userId = normalUserEntities.get(i - 1).getUserId();
                            Intent intent = new Intent(getContext(), UserHomeActivity.class);
                            intent.putExtra("other_user_id", userId);
                            ActivityUtil.startActivity((Activity) getContext(), intent);
                        }
                    }
                });
            } else {
                flowListView.setVisibility(View.GONE);
            }
        }
        //添加评论的信息,父布局中添加了同样人推荐信息 也添加了评论信息
        if (entity.getCommentNum() > 0 || entity.getNormalUserEntities().size() > 0) {
            llContainer.setVisibility(View.VISIBLE);
            if (entity.getCommentNum() > 0) {
                final List<OpinionCommentEntity> opinionCommentEntities = entity.getOpinionCommentEntities();
                commentContainer.removeAllViews();  //必须要清理掉以前的
                int num;
                if (!entity.isShowMoreComment()) {
                    if (opinionCommentEntities.size() <= 5) {   //最多显示5条评论，超过后显示为点击查看更多
                        tvShowMoreComment.setVisibility(View.GONE);
                        num = opinionCommentEntities.size();
                    } else {
                        tvShowMoreComment.setVisibility(View.VISIBLE);
                        num = 5;
                    }
                } else {
                    tvShowMoreComment.setVisibility(View.GONE);
                    num = opinionCommentEntities.size();
                }
                for (int i = 0; i < num; i++) {
                    final int finalI = i;
                    //需要在这里面判断出来是评论还是回复
                    TextView tvCommentContent = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.topic_comment_item, null);
                    if (TextUtils.isEmpty(opinionCommentEntities.get(i).getToUser().getUserId())) {   //如果为空则是评论
                        SpannableStringBuilder comment = StringUtil.formatTopicComment(opinionCommentEntities.get(i).getNickName() +
                                ": " + entity.getOpinionCommentEntities().get(i).getContent());
                        tvCommentContent.setText(comment);
                        tvCommentContent.setOnClickListener(new View.OnClickListener() {   //给评论添加回复
                            @Override public void onClick(View v) {
                                topicDetailPre.openEditCommentView(entity.getOpinionId(), opinionCommentEntities.get(finalI).getUserId(), position,
                                        opinionCommentEntities.get(finalI).getNickName());
                            }
                        });
                    } else {          //不为空说明是回复
                        String name1 = opinionCommentEntities.get(i).getNickName();
                        String name2 = opinionCommentEntities.get(i).getToUser().getNickName();
                        String content = opinionCommentEntities.get(i).getContent();
                        SpannableStringBuilder response = StringUtil.formatTopicCommentResponse(name1, name2, content);
                        tvCommentContent.setText(response);
                        tvCommentContent.setOnClickListener(new View.OnClickListener() {   //给评论的回复添加回复
                            @Override public void onClick(View v) {
                                topicDetailPre.openEditCommentView(entity.getOpinionId(), opinionCommentEntities.get(finalI).getUserId(), position,
                                        opinionCommentEntities.get(finalI).getNickName());
                            }
                        });
                    }
                    commentContainer.addView(tvCommentContent);
                }
            }
        } else {
            llContainer.setVisibility(View.GONE);
        }

        //设置点赞状态
        if (entity.isZaning()) {
            ivPraise.setImageResource(R.drawable.like);
        } else {
            ivPraise.setImageResource(R.drawable.nolike);
        }

        /****************设置事件响应************************************/
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //跳转到用户详情界面
                Intent intent = new Intent(getContext(), UserHomeActivity.class);
                intent.putExtra("other_user_id", entity.getUserId());
                ActivityUtil.startActivity((Activity) getContext(), intent);
            }
        });

        rlComment.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                topicDetailPre.openEditCommentView(entity.getOpinionId(), "", position, entity.getOpinionName());
            }
        });
        ((ViewGroup) tvPraiseCount.getParent()).setOnClickListener(new View.OnClickListener() {  //点赞
            @Override public void onClick(View v) {
                topicDetailPre.dianZan(entity.getOpinionId(), position);  //点赞
            }
        });

        tvShowMoreComment.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                entity.setShowMoreComment(true);
                topicDetailPre.freshView();
            }
        });
    }

    private class ImageAdapter extends CustomAdapter {
        private List<NormalUserEntity> normalUserEntities;

        public ImageAdapter(List<NormalUserEntity> normalUserEntities) {
            this.normalUserEntities = normalUserEntities;
        }

        @Override public int getCount() {
            return normalUserEntities.size() + 1;
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.round_img, parent, false);
            CircleImageView imgView = ViewHolder.get(view,R.id.round_img);
            TextView tvTitle = ViewHolder.get(view,R.id.tv_title);
            if (position == 0) {
                tvTitle.setVisibility(View.VISIBLE);
                imgView.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.GONE);
                imgView.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(normalUserEntities.get(position - 1).getHeadPorait(), imgView);
            }
            return view;
        }
    }
}
