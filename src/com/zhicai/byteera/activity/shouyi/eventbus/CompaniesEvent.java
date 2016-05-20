package com.zhicai.byteera.activity.shouyi.eventbus;

/**
 * Created by chenzhenxing on 15/11/26.
 */
@SuppressWarnings("unused")
public class CompaniesEvent {
    private boolean isRefresh;

    public CompaniesEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }
}
