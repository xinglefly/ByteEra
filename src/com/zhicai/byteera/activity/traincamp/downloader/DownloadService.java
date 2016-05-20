package com.zhicai.byteera.activity.traincamp.downloader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zhicai.byteera.activity.traincamp.activity.GuessPicActivity;
import com.zhicai.byteera.commonutil.SDLog;

import java.io.File;

public class DownloadService extends Service {
	public static final String TAG = DownloadService.class.getSimpleName();

	public static final int Flag_Init = 0; // 初始状态
	public static final int Flag_Down = 1; // 下载状态
	public static final int Flag_Pause = 2; // 暂停状态
	public static final int Flag_Done = 3; // 完成状态

	String url;

	// 下载进度
	private int progress = 0;

	public int getProgress() {
		return progress;
	}

	// 下载状态标志
	private int flag;

	public int getFlag() {
		return flag;
	}

	DownThread mThread;
	Downloader downloader;

	private static DownloadService instance;

	public static DownloadService getInstance() {
		return instance;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		SDLog.d(TAG, "service.........onCreate");
		instance = this;
		flag = Flag_Init;
		super.onCreate();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String msg = intent.getExtras().getString("flag");
		url = intent.getExtras().getString("url");
		if (mThread == null) {
			mThread = new DownThread();
		}
		if (downloader == null) {
			int lasthash = url.lastIndexOf("/");
			downloader = new Downloader(this, url, url.substring(lasthash));
		}
		downloader.setDownloadListener(downListener);

		if (msg.equals("start")) {
			startDownload();
		} else if (msg.equals("pause")) {
			downloader.pause();
		} else if (msg.equals("resume")) {
			downloader.resume();
		} else if (msg.equals("stop")) {
			downloader.cancel();
			stopSelf();
		}

		//flags表示启动服务的方式：如果你实现onStartCommand()来安排异步工作或者在另一个线程中工作, 那么你可能需要使用START_FLAG_REDELIVERY来让系统重新发送一个intent。这样如果你的服务在处理它的时候被Kill掉, Intent不会丢失.
		return START_FLAG_REDELIVERY;
		//如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
//		return START_STICKY;
//		return super.onStartCommand(intent, flags, startId);
	}

	private void startDownload() {
		if (flag == Flag_Init || flag == Flag_Pause) {
			while(!stopFlag){
				if (mThread != null && !mThread.isAlive()) {
					mThread = new DownThread();
					SDLog.d(TAG,"-thread->"+url);
				}
				mThread.start();
				mThread.stopDown();
			}
		}
	}

	@Override
	public void onDestroy() {
		SDLog.d(TAG, "service...........onDestroy");
		try {
			flag = 0;
			mThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mThread = null;
		super.onDestroy();
	}

	public boolean stopFlag = false;
	class DownThread extends Thread {
		@Override
		public void run() {
				if (flag == Flag_Init || flag == Flag_Done) {
					flag = Flag_Down;
				}
				downloader.start();
		}
		public void stopDown(){
			stopFlag = true;
		}
	}


	private DownloadListener downListener = new DownloadListener() {

		int fileSize;
		Intent intent = new Intent();

		@Override
		public void onSuccess(File file) {
			intent.setAction(GuessPicActivity.ACTION_DOWNLOAD_SUCCESS);
			intent.putExtra("progress", 100);
			intent.putExtra("file", file);
			sendBroadcast(intent);
		}

		@Override
		public void onStart(int fileByteSize) {
			fileSize = fileByteSize;
			flag = Flag_Down;
		}

		@Override
		public void onResume() {
			flag = Flag_Down;
		}

		@Override
		public void onProgress(int receivedBytes) {
			if (flag == Flag_Down) {
				progress = (int) ((receivedBytes / (float) fileSize) * 100);
				intent.setAction(GuessPicActivity.ACTION_DOWNLOAD_PROGRESS);
				intent.putExtra("progress", progress);
				sendBroadcast(intent);

				if (progress == 100) {
					flag = Flag_Done;
				}
			}
		}

		@Override
		public void onPause() {
			flag = Flag_Pause;
		}

		@Override
		public void onFail() {
			intent.setAction(GuessPicActivity.ACTION_DOWNLOAD_FAIL);
			sendBroadcast(intent);
			flag = Flag_Init;
		}

		@Override
		public void onCancel() {
			progress = 0;
			flag = Flag_Init;
		}
	};
}
