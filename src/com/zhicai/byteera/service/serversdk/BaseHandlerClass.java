package com.zhicai.byteera.service.serversdk;


public abstract class BaseHandlerClass {
	
	
	public void handle(byte[] buffer){
		onSuccess(buffer);
	}

	public abstract void onSuccess(byte[] buffer);
	

}
