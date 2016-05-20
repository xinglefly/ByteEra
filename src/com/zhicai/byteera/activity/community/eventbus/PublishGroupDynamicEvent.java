package com.zhicai.byteera.activity.community.eventbus;

/**
 * Created by chenzhenxing on 15/11/2.
 */
@SuppressWarnings("unused")
public class PublishGroupDynamicEvent {

    private boolean isRefresh;

    public PublishGroupDynamicEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }
}
