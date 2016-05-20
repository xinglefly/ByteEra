/**
 * 
 */
package com.zhicai.byteera.activity.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
@SuppressWarnings("unused")
@Table(name = "guess_smallmission")
public class GuessSmallMissionInfo implements Serializable {

	private int id;
	private int gridviewId; //主要是为了让每一个列表里面显示的数据都是从1——length-1

	private int levelId;
	public int missionid;
	public String missionjson;
	public int missionstatus;


	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getGridviewid() {
		return gridviewId;
	}

	public void setGridviewid(int gridviewId) {
		this.gridviewId = gridviewId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMissionid() {
		return missionid;
	}
	public String getMissionjson() {
		return missionjson;
	}
	public int getMissionstatus() {
		return missionstatus;
	}
	public void setMissionid(int missionid) {
		this.missionid = missionid;
	}
	public void setMissionjson(String missionjson) {
		this.missionjson = missionjson;
	}
	public void setMissionstatus(int missionstatus) {
		this.missionstatus = missionstatus;
	}


	@Override
	public String toString() {
		return "GuessSmallMissionInfo{" +
				"id=" + id +
				", gridviewId=" + gridviewId +
				", levelId=" + levelId +
				", missionid='" + missionid + '\'' +
				", missionjson='" + missionjson + '\'' +
				", missionstatus=" + missionstatus +
				'}';
	}

	public GuessSmallMissionInfo() {
	}


	public GuessSmallMissionInfo(int gridviewId,int levelId, int missionid, String missionjson, int missionstatus) {
		this.gridviewId = gridviewId;
		this.levelId = levelId;
		this.missionid = missionid;
		this.missionjson = missionjson;
		this.missionstatus = missionstatus;
	}



	//guess game
	public GuessSmallMissionInfo(int missionid, String missionjson, int missionstatus) {
		this.missionid = missionid;
		this.missionjson = missionjson;
		this.missionstatus = missionstatus;
	}


	public GuessSmallMissionInfo(int id, int levelId, int missionid, int missionstatus) {
		this.id = id;
		this.levelId = levelId;
		this.missionid = missionid;
		this.missionstatus = missionstatus;
	}

}
