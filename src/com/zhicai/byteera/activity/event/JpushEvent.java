package com.zhicai.byteera.activity.event;

/**
 * Created by chenzhenxing on 15/12/30.
 */
@SuppressWarnings("unused")
public class JpushEvent {
    private boolean isRefreshUI;
    private int type;

    public JpushEvent(boolean isRefreshUI) {
        this.isRefreshUI = isRefreshUI;
    }

    public JpushEvent(int type) {
        this.type = type;
    }

    public boolean isRefreshUI() {
        return isRefreshUI;
    }

    public void setIsRefreshUI(boolean isRefreshUI) {
        this.isRefreshUI = isRefreshUI;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
