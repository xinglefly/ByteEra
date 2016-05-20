package com.zhicai.byteera.activity.event;

/**
 * Created by chenzhenxing on 16/1/11.
 */
@SuppressWarnings("unused")
public class DayTaskEvent {

    private boolean isRefresh;

    public DayTaskEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }
}
