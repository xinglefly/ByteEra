package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
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
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicFragmentItemIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.DynamicFragmentPre;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.StringUtil;

import java.util.List;

/** Created by bing on 2015/4/19. */
public class DynamicListItem extends FrameLayout implements DynamicFragmentItemIV {
    private DynamicItemGridAdapter dynamicItemGridAdapter;
    private ImageView imgHead;
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
    private TextView tvJingcai;
    private TextView tvSource;  //来源于小组或机构

    public DynamicListItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.dynamic_list_item, this, true);
        //共有控件
        imgHead = (ImageView) findViewById(R.id.img_item_head);
        tvTitle = (TextView) findViewById(R.id.tv_item_title);
        tvTime = (TextView) findViewById(R.id.tv_item_time);
        tvSource = (TextView) findViewById(R.id.tv_source);
        imgPraise = (ImageView) findViewById(R.id.img_praise);
        tvJingcai = (TextView) findViewById(R.id.tv_jingcai);  //精彩


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

        tvFenxiang = (TextView) findViewById(R.id.tv_fenxiang);

        dynamicItemGridAdapter = new DynamicItemGridAdapter(getContext());
    }

    @Override public void setIsPraise() {
        imgPraise.setImageResource(R.drawable.know_xihuan);
    }

    @Override public void setUnPraise() {
        imgPraise.setImageResource(R.drawable.heart_detail);
    }

    public void refreshView(final DynamicEntity entity, final int position, final DynamicFragmentPre dynamicFragmentPre) {
        if (position == 1) {
            tvJingcai.setVisibility(View.VISIBLE);
        } else {
            tvJingcai.setVisibility(View.GONE);
        }
        dynamicFragmentPre.addDynamicFragmentItemIV(this, position);
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
                            dynamicFragmentPre.imageBrower(view, position, entity.getImgList());
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
//                            dynamicFragmentPre.imageBrower(position, imgListFromDb);
//                        }
//                    });
//                }
                else {
                    imgGridView.setVisibility(View.GONE);
                }
                hasZan = entity.isHasZan();
                tvAll.setText(entity.getContent());
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvSource.setText(entity.getGroup_name());
                tvSource.setTextColor(Color.parseColor("#157db6"));
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                //  tvReverseCount.setText(entity.getCommentNum() + "");
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                break;
            case 2:
                tvFenxiang.setVisibility(View.VISIBLE);
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.VISIBLE);
                imgGridView.setVisibility(View.GONE);
                tvAll.setText(entity.getContent());
                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvSource.setText(entity.getGroup_name());
                tvSource.setTextColor(Color.parseColor("#157db6"));
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                // tvReverseCount.setText(entity.getCommentNum() + "");
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                ImageLoader.getInstance().displayImage(entity.getLinkImg(), ivInsitution);
                //设置内部链接的图片
                tvInsitution.setText(entity.getLinkContent());        //设置内部链接的标题
                contentLink.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dynamicFragmentPre.intentToKnowWealthDetail(entity);
                    }
                });
                break;
            case 3:
                tvFenxiang.setVisibility(View.GONE);
                contentAll.setVisibility(View.GONE);
                contentPart.setVisibility(View.VISIBLE);
                contentLink.setVisibility(View.GONE);
                imgGridView.setVisibility(View.GONE);
                hasZan = entity.isHasZan();
                ImageLoader.getInstance().displayImage(entity.getAvatarUrl(), imgHead);
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvSource.setText(entity.getGroup_name());
                tvSource.setTextColor(Color.parseColor("#157db6"));
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
                //  tvReverseCount.setText(entity.getCommentNum() + "");
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                break;
        }
        //根据是否点过赞来初始化点赞图标的状态
        if (hasZan) {
            setIsPraise();
        } else {
            setUnPraise();
        }
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dynamicFragmentPre.clickHeadItent(entity);
            }
        });

        ((View) tvPraiseCount.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicFragmentPre.dianZan(entity, position);

            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastText("点击查看更多");
            }
        });

        ((View) tvReverseCount.getParent()).setOnClickListener(new View.OnClickListener() { //进入评论页
            @Override
            public void onClick(View v) {
                dynamicFragmentPre.intentToDynamicCommentDetail(position, entity);
            }
        });

        tvSource.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id", "group_id-->" + entity.getGruop_id());
                if (!TextUtils.isEmpty(entity.getGruop_id())){
                    dynamicFragmentPre.intentToGroupHome(entity.getGruop_id());
                }
            }
        });
    }

    @Override public int getPraiseNum() {
        return Integer.parseInt(tvPraiseCount.getText().toString());
    }

    @Override public void setPraiseNum(int praiseNum) {
        tvPraiseCount.setText(praiseNum + "");
    }
}
