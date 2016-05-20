package com.zhicai.byteera.activity.community.dynamic.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.adapter.DynamicItemGridAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.GroupDynamicItemIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.GroupHomeActivityPre;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.StringUtil;

import java.util.List;

/**
 * Created by lieeber on 15/7/7.
 */
public class GroupDynamicItem extends FrameLayout implements GroupDynamicItemIV {

    private DynamicItemGridAdapter dynamicItemGridAdapter;
    private ImageView imgHead;
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvSource;
    private ImageView imgPraise;
    private TextView tvPraiseCount;
    private TextView tvReverseCount;
    private View contentAll;
    private TextView tvAll;
    private View contentPart;
    private TextView tvPart;
    private TextView tvMore;
    private View contentLink;
    private GridView imgGridView;
    private ImageView ivInsitution;
    private TextView tvInsitution;
    private TextView tvJinCai;

    GroupHomeActivityPre organizationHomePre;

    public GroupDynamicItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.dynamic_list_item, this, true);

        //隐藏头部精彩
        tvJinCai = (TextView) findViewById(R.id.tv_jingcai);
        tvJinCai.setVisibility(View.GONE);

        //共有控件
        imgHead = (ImageView) findViewById(R.id.img_item_head);
        tvTitle = (TextView) findViewById(R.id.tv_item_title);
        tvTime = (TextView) findViewById(R.id.tv_item_time);
        imgPraise = (ImageView) findViewById(R.id.img_praise);
        tvSource = (TextView) findViewById(R.id.tv_source);
        tvSource.setVisibility(View.GONE);

        tvPraiseCount = (TextView) findViewById(R.id.tv_item_praisecount);
        tvReverseCount = (TextView) findViewById(R.id.tv_item_revertvalue);

        //单独组件
        contentAll = findViewById(R.id.content_all);
        tvAll = (TextView) findViewById(R.id.tv_all);

        contentPart = findViewById(R.id.content_part);
        tvPart = (TextView) findViewById(R.id.tv_part);
        tvMore = (TextView) findViewById(R.id.tv_more);

        contentLink = findViewById(R.id.content_link);
        imgGridView = (GridView) findViewById(R.id.grid_view);
        ivInsitution = (ImageView) findViewById(R.id.iv_institution);
        tvInsitution = (TextView) findViewById(R.id.tv_institution);
        dynamicItemGridAdapter = new DynamicItemGridAdapter(getContext());
    }


    public void refreshView(final DynamicEntity entity, final int position, GroupHomeActivityPre groupHomePre) {
        boolean hasZan = false;
        this.organizationHomePre = groupHomePre;
        this.organizationHomePre.addGroupDynamicItemIVs(this, position);
        this.setTag(position);
        int type = entity.getType();
        switch (type) {
            case 1:
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.GONE);
                //设置动态图片
//                final List<ImgEntity> imgListFromDb = entity.getImgListFromDb(entity.getId() != 0 ? entity.getId() : -1);
                List<ImgEntity> imgList = entity.getImgList();
                int numColumns;
                if (imgList.size() > 0) {       //从网络加载的时候通过这种方式来取得图片
                    imgGridView.setVisibility(View.VISIBLE);
                    numColumns = entity.getImgList().size() <= 3 ? entity.getImgList().size() : 3;
                    imgGridView.setNumColumns(numColumns);
                    dynamicItemGridAdapter.setData(imgList, numColumns);
                    imgGridView.setAdapter(dynamicItemGridAdapter);
                    imgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            organizationHomePre.imageBrower(position, entity.getImgList());
                        }
                    });
                }
//                else if (imgListFromDb.size() > 0) {       //从数据库查通过这种方式来取得图片
//                    imgGridView.setVisibility(View.VISIBLE);
//                    numColumns = imgListFromDb.size() <= 3 ? imgListFromDb.size() : 3;
//                    imgGridView.setNumColumns(numColumns);
//                    dynamicItemGridAdapter.setData(imgListFromDb, numColumns);
//                    imgGridView.setAdapter(dynamicItemGridAdapter);
//                    imgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            organizationHomePre.imageBrower(position, imgListFromDb);
//                        }
//                    });
//                }
                else {
                    imgGridView.setVisibility(View.GONE);
                }
                hasZan = entity.isHasZan();
                tvAll.setText(entity.getContent());
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                if (!TextUtils.isEmpty(entity.getGroup_name())){
                    tvSource.setText(entity.getGroup_name());
                }
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                tvReverseCount.setText(entity.getCommentNum() + "");
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                break;
            case 2:
                contentAll.setVisibility(View.GONE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.VISIBLE);
                imgGridView.setVisibility(View.GONE);

                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                if (!TextUtils.isEmpty(entity.getGroup_name())){
                    tvSource.setText(entity.getGroup_name());
                }
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                tvReverseCount.setText(entity.getCommentNum() + "");
                ImageLoader.getInstance().displayImage(entity.getLinkImg(), ivInsitution);
                //设置内部链接的图片
                tvInsitution.setText(entity.getLinkContent());        //设置内部链接的标题
                contentLink.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View v) {
                        Intent intent = new Intent(getContext(), KnowWealthDetailActivity.class);
                        intent.putExtra("zixun_id", entity.getZiXunId());
                        ActivityUtil.startActivity((Activity) getContext(), intent);
                    }
                });
                break;
            case 3:
                contentAll.setVisibility(View.GONE);
                contentPart.setVisibility(View.VISIBLE);
                contentLink.setVisibility(View.GONE);
                imgGridView.setVisibility(View.GONE);

                hasZan = entity.isHasZan();
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                if (!TextUtils.isEmpty(entity.getGroup_name())){
                    tvSource.setText(entity.getGroup_name());
                }
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                tvReverseCount.setText(entity.getCommentNum() + "");
                break;
        }
        //根据是否点过赞来初始化点赞图标的状态
        if (hasZan) {
            setIsPraise();
        } else {
            setUnPraise();
        }
        ((View) tvPraiseCount.getParent()).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                organizationHomePre.dianZan(entity, position);

            }
        });

        tvMore.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
               ToastUtil.showToastText("点击查看更多");
            }
        });

        ((View) tvReverseCount.getParent()).setOnClickListener(new OnClickListener() { //进入评论页
            @Override public void onClick(View v) {
                organizationHomePre.intentToDynamicCommentDetial(position, entity);
            }
        });
    }

    @Override public String getPraiseCount() {
        return tvPraiseCount.getText().toString();
    }

    @Override public void setPraiseCount(String count) {
        tvPraiseCount.setText(count);
    }

    @Override public void setIsPraise() {
        imgPraise.setImageResource(R.drawable.know_xihuan);
    }

    @Override public void setUnPraise() {
        imgPraise.setImageResource(R.drawable.heart_detail);
    }

}
