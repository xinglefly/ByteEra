package com.zhicai.byteera.activity.community.topic.actiivty;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.topic.adapter.MoreTopicAdapter;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.topic.Topic;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.ZhiCaiLRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreTopicActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.list_view) ZhiCaiLRefreshListView mListView;
    private MoreTopicAdapter mAdapter;
    private int totalNum;
    private List<TopicEntity> topicEntities;
    private int num;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_more_topic);
        ButterKnife.bind(this);
        initListView();
    }

    private void initListView() {
        num = getIntent().getIntExtra("num", -1);
        mAdapter = new MoreTopicAdapter(baseContext);
        mListView.setDivider(getResources().getDrawable(R.drawable.more_topic_divider));
        mListView.getListView().setAdapter(mAdapter);
        mListView.setLoadMoreDataListener(new ZhiCaiLRefreshListView.LoadMoreDataListener() {
            @Override public void loadMore() {
                mListView.loadComplete();
            }
        });
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                mListView.refreshFinish();
            }
        });

    }

    @Override protected void initData() {
        final Topic.GetAllTopicReq req = Topic.GetAllTopicReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "get_all_topic", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Topic.GetAllTopicResponse response = Topic.GetAllTopicResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                topicEntities = ModelParseUtil.TopicEntityParse(response.getItemList());
                                for (int i = 0; i < topicEntities.size(); i++) {
                                    TopicEntity topicEntity = topicEntities.get(i);
                                    if (topicEntity.getTopicNum() == num) {
                                        topicEntity.setIsSelected(true);
                                    }
                                }
                                mAdapter.freshItem(topicEntities);
                                mAdapter.notifyDataSetChanged();
                                totalNum = response.getTotalNum();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mListView.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("topicList", (ArrayList<? extends Parcelable>) topicEntities);
                intent.putExtra("totalNum", totalNum);
                intent.putExtra("position", topicEntities.get(position).getTopicNum());
                setResult(11, intent);
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }
}
