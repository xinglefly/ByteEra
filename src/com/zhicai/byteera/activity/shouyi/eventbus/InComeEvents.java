package com.zhicai.byteera.activity.shouyi.eventbus;

/**
 * Created by chenzhenxing on 15/11/13.
 */
@SuppressWarnings("unused")
public class InComeEvents {

    private int conditionTime;
    private int conditionRate;
    private boolean conditionBank;
    private boolean conditionP2p;
    private boolean isClick;

    public InComeEvents(int conditionTime, int conditionRate, boolean conditionBank, boolean conditionP2p,boolean isClick) {
        this.conditionTime = conditionTime;
        this.conditionRate = conditionRate;
        this.conditionBank = conditionBank;
        this.conditionP2p = conditionP2p;
        this.isClick = isClick;
    }

    public int getConditionTime() {
        return conditionTime;
    }

    public int getConditionRate() {
        return conditionRate;
    }

    public boolean isConditionBank() {
        return conditionBank;
    }

    public boolean isConditionP2p() {
        return conditionP2p;
    }

    public boolean isClick() {
        return isClick;
    }
}
