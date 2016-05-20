package com.zhicai.byteera.activity.event;

/**
 * Created by chenzhenxing on 15/12/23.
 */
@SuppressWarnings("unused")
public class NetWorkEvent {
    private boolean isNetwork;

    public NetWorkEvent(boolean isNetwork) {
        this.isNetwork = isNetwork;
    }

    public boolean isNetwork() {
        return isNetwork;
    }

    public void setIsNetwork(boolean isNetwork) {
        this.isNetwork = isNetwork;
    }
}
