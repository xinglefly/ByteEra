package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
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
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserHOmeDynamicItemIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserHomeActivityPre;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.StringUtil;

import java.util.List;

/** Created by bing on 2015/4/19. */
public class UserHomeDynamicItem extends FrameLayout implements UserHOmeDynamicItemIV {
    private DynamicItemGridAdapter dynamicItemGridAdapter;
    private TextView tvTitle;
    private TextView tvTime;
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
    private TextView tvFenxiang;

    public UserHomeDynamicItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.userhome_dynamic_item, this, true);
        //共有控件
        tvTitle = (TextView) findViewById(R.id.tv_item_title);
        tvTime = (TextView) findViewById(R.id.tv_item_time);
        imgPraise = (ImageView) findViewById(R.id.img_praise);

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
        tvFenxiang = (TextView) findViewById(R.id.tv_fenxiang); //分享一个链接

        dynamicItemGridAdapter = new DynamicItemGridAdapter(getContext());
    }

    public void refreshView(final int position, final DynamicEntity entity, final UserHomeActivityPre userHomeActivityPre) {
        userHomeActivityPre.addUserHomeDynamicItemIV(this, position);
        boolean hasZan = false;
        int type = entity.getType();
        switch (type) {
            case 1:
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.GONE);
                tvFenxiang.setVisibility(View.GONE);
                //设置动态图片
                // final List<ImgEntity> imgListFromDb = entity.getImgListFromDb(entity.getId() != 0 ? entity.getId() : -1);
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
                            userHomeActivityPre.imageBrower(position, entity.getImgList());
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
//                            userHomeActivityPre.imageBrower(position, imgListFromDb);
//                        }
//                    });
//                }
                else {
                    imgGridView.setVisibility(View.GONE);
                }
                hasZan = entity.isHasZan();
                tvAll.setText(entity.getContent());
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                tvReverseCount.setText(entity.getCommentNum() + "");
                break;
            case 2:
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.VISIBLE);
                imgGridView.setVisibility(View.GONE);
                tvFenxiang.setVisibility(View.VISIBLE);

                tvAll.setText(entity.getContent());
                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                tvReverseCount.setText(entity.getCommentNum() + "");
                ImageLoader.getInstance().displayImage(entity.getLinkImg(), ivInsitution);
                //设置内部链接的图片
                tvInsitution.setText(entity.getLinkContent());        //设置内部链接的标题
                contentLink.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        userHomeActivityPre.intentToKnowWealthDetail(entity);
                    }
                });
                break;
            case 3:
                contentAll.setVisibility(View.GONE);
                contentPart.setVisibility(View.VISIBLE);
                contentLink.setVisibility(View.GONE);
                imgGridView.setVisibility(View.GONE);
                tvFenxiang.setVisibility(View.GONE);
                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
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

        ((View) tvPraiseCount.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                userHomeActivityPre.dianZan(entity, position);
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
               ToastUtil.showToastText("点击查看更多");
            }
        });

        ((View) tvReverseCount.getParent()).setOnClickListener(new View.OnClickListener() { //进入评论页
            @Override public void onClick(View v) {
                userHomeActivityPre.intentToDynamicCommentDetail(position, entity);

            }
        });
    }

    @Override public int getPraiseNum() {
        return Integer.parseInt(tvPraiseCount.getText().toString());
    }

    @Override public void setPraiseNum(int praiseNum) {
        tvPraiseCount.setText(praiseNum + "");
    }

    @Override public void setIsPraise() {
        imgPraise.setImageResource(R.drawable.know_xihuan);
    }

    @Override public void setUnPraise() {
        imgPraise.setImageResource(R.drawable.heart_detail);
    }
}
