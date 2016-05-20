package com.zhicai.byteera.activity.community.eventbus;

/**
 * Created by chenzhenxing on 15/11/1.
 */
@SuppressWarnings("unused")
public class GroupEvent {

    private boolean isAttention;
    private int position;

    public GroupEvent(boolean isAttention, int position) {
        this.isAttention = isAttention;
        this.position = position;
    }

    public GroupEvent(boolean isAttention) {
        this.isAttention = isAttention;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public int getPosition() {
        return position;
    }
}
