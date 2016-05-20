package com.zhicai.byteera.activity.community.eventbus;

/**
 * Created by chenzhenxing on 15/10/30.
 */
public class OrganizationEvent {

    private int position;
    private boolean isAttention;

    public OrganizationEvent(boolean isAttention) {
        this.isAttention = isAttention;
    }

    public OrganizationEvent(int position, boolean isAttention) {
        this.position = position;
        this.isAttention = isAttention;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public int getPosition() {
        return position;
    }
}
