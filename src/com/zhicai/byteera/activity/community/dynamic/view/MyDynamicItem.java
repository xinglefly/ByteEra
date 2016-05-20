package com.zhicai.byteera.activity.community.dynamic.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.ImagePagerActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.DynamicItemGridAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import org.litepal.crud.DataSupport;

import java.util.List;

/** Created by bing on 2015/4/19. */
public class MyDynamicItem extends FrameLayout implements View.OnClickListener {
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
    private boolean hasZan;
    private String msgId;
    private TextView tvFenXiang;

    public MyDynamicItem(Context context) {
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
        tvFenXiang = (TextView) findViewById(R.id.tv_fenxiang); //分享一个链接

        dynamicItemGridAdapter = new DynamicItemGridAdapter(getContext());
    }


    @Override public void onClick(View v) {

    }

    private void imageBrower(int position, List<ImgEntity> imgList) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        String images[] = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            images[i] = imgList.get(i).getImgUrl();
        }
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, images);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        ActivityUtil.startActivity((Activity) getContext(), intent);
    }

    public void setIsPraise(ImageView imgPraise) {
        imgPraise.setImageResource(R.drawable.know_xihuan);
    }

    public void setUnpraise(ImageView imgPraise) {
        imgPraise.setImageResource(R.drawable.heart_detail);
    }


    public void refreshView(final DynamicEntity entity, final int position) {
        int type = entity.getType();
        switch (type) {
            case 1:
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.GONE);
                tvFenXiang.setVisibility(View.GONE);
                //设置动态图片
                //  final List<ImgEntity> imgListFromDb = entity.getImgListFromDb(entity.getId() != 0 ? entity.getId() : -1);
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
                            imageBrower(position, entity.getImgList());
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
//                            imageBrower(position, imgListFromDb);
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
                //  tvReverseCount.setText(entity.getCommentNum() + "");  //通过总数获得的有点不准确
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                msgId = entity.getMsgId();
                break;
            case 2:
                contentAll.setVisibility(View.VISIBLE);
                contentPart.setVisibility(View.GONE);
                contentLink.setVisibility(View.VISIBLE);
                imgGridView.setVisibility(View.GONE);
                tvFenXiang.setVisibility(View.VISIBLE);

                tvAll.setText(entity.getContent());
                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
               // tvReverseCount.setText(entity.getCommentNum() + "");
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                ImageLoader.getInstance().displayImage(entity.getLinkImg(), ivInsitution);
                //设置内部链接的图片
                tvInsitution.setText(entity.getLinkContent());        //设置内部链接的标题
                msgId = entity.getMsgId();

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
                tvFenXiang.setVisibility(View.GONE);
                hasZan = entity.isHasZan();
                tvTime.setText(StringUtil.checkTime(entity.getPublishTime() * 1000));
                tvPart.setText(entity.getContent());
                tvTitle.setText(entity.getNickName());
                tvPraiseCount.setText(entity.getZanCount() + "");
             //   tvReverseCount.setText(entity.getCommentNum() + "");
                tvReverseCount.setText(entity.getCommmentItemList().size() + "");
                break;
        }
        //根据是否点过赞来初始化点赞图标的状态
        if (hasZan) {
            setIsPraise(imgPraise);
        } else {
            setUnpraise(imgPraise);
        }

        ((View) tvPraiseCount.getParent()).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                final String userId = PreferenceUtils.getInstance(getContext()).readUserInfo().getUser_id();
                if (!hasZan) {
                    //点赞
                    Dynamic.DoZan msg = Dynamic.DoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
                    TiangongClient.instance().send("chronos", "licaiquan_dozan", msg.toByteArray(),
                            new BaseHandlerClass() {
                                @Override public void onSuccess(byte[] buffer) {
                                    try {
                                        final Dynamic.DoZanResponse response = Dynamic.DoZanResponse.parseFrom(buffer);
                                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                            @Override public void run() {
                                                if (response.getErrorno() == 0) {
                                                    //TODO 每日任务  点赞一次

                                                    tvPraiseCount.setText(Integer.parseInt(tvPraiseCount.getText().toString()) + 1 + "");
                                                    setIsPraise(imgPraise);
                                                    //自己封装一个赞的人的集合，然后点赞的时候就把自己加进去
                                                    entity.setHasZan(true);
                                                    entity.setZanCount(entity.getZanCount() + 1);
                                                    hasZan = true;
                                                    //更新数据库
                                                    ContentValues values = new ContentValues();
                                                    values.put("zancount", entity.getZanCount());
                                                    values.put("haszan", true);
                                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                                }
                                            }
                                        });
                                    } catch (InvalidProtocolBufferException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    //取消点赞
                    Dynamic.UndoZan msg = Dynamic.UndoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
                    TiangongClient.instance().send("chronos", "licaiquan_undozan", msg.toByteArray(),
                            new BaseHandlerClass() {
                                @Override public void onSuccess(byte[] buffer) {
                                    try {
                                        final Dynamic.UndoZanResponse response = Dynamic.UndoZanResponse.parseFrom(buffer);
                                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (response.getErrorno() == 0) {
                                                    tvPraiseCount.setText(Integer.parseInt(tvPraiseCount.getText().toString()) - 1 + "");
                                                    setUnpraise(imgPraise);
                                                    hasZan = false;
                                                    entity.setHasZan(false);
                                                    entity.setZanCount(entity.getZanCount() - 1);
                                                    //更新数据库
                                                    ContentValues values = new ContentValues();
                                                    values.put("zancount", entity.getZanCount());
                                                    values.put("haszan", false);
                                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                                }
                                            }
                                        });

                                    } catch (InvalidProtocolBufferException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        tvMore.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
               ToastUtil.showToastText("点击查看更多");
            }
        });
        ((View) tvReverseCount.getParent()).setOnClickListener(new View.OnClickListener() { //进入评论页
            @Override public void onClick(View v) {
                Intent intent = new Intent(getContext(), DynamicCommentDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("entity", entity);
                ActivityUtil.startActivityForResult((Activity) getContext(), intent, Constants.REQUEST_DYNAMIC_FRAGMENT);
            }
        });
    }
}
