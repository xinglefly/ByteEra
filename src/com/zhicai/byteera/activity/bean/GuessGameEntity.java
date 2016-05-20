package com.zhicai.byteera.activity.bean;

import java.io.Serializable;

@SuppressWarnings("unused")
public class GuessGameEntity implements Serializable {

	private static final long serialVersionUID = 3677302286132843794L;

	public String image;
	public String[] answer;
	public String[] option;
	public String intro;
	public String tip1;
	public String tip2;
}
