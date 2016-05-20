package com.zhicai.byteera.test;

import android.test.AndroidTestCase;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.activity.bean.GuessDb;
import com.zhicai.byteera.activity.bean.GuessPicLevel;
import com.zhicai.byteera.activity.bean.GuessPicMisson;
import com.zhicai.byteera.activity.bean.GuessSmallMissionInfo;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.SDLog;

import java.util.List;

/**
 * 详解Xutil的db使用
 * @author xinglefly
 *
 */
public class TextDB extends AndroidTestCase {

    private static final String TAG = TextDB.class.getSimpleName();


    public void testRecord()throws DbException{
        DbUtils db = DbUtils.create(getContext(), Constants.GUESSPIC_DB);
        for (int i=0;i<5;i++){
            for (int j=1;j<4;j++){
                GuessDb guessDb = new GuessDb(i+1,j,1);
                db.save(guessDb);
            }
        }

        List<GuessDb> list = db.findAll(Selector.from(GuessDb.class));
        SDLog.d(TAG,"-guessdb->"+list.size());
        for (GuessDb guess :list){
            SDLog.d(TAG,"-guessdb selectall1->"+guess.toString());
        }
    }

    public void testRecordFind()throws DbException{
        DbUtils db = DbUtils.create(getContext(), Constants.GUESSPIC_DB);
        //不用primary_key来查询，通过两个条件来查询一条数据
        GuessDb guess = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", 1).and("mission_id", "=", 2));
        if (guess!=null){
            SDLog.d(TAG,"-guessdb findFirst->"+guess.toString());
        }else{
            SDLog.d(TAG,"- fuck ->");
        }
    }

    public void testRecordGame()throws  DbException{
        DbUtils db = DbUtils.create(getContext(), Constants.GUESSPIC_DB);
//        GuessDb guessDb = new GuessDb(1,1,5);
//        db.saveOrUpdate(guessDb);
//        SDLog.d(TAG, "-guessdb save->" + guessDb.toString());
        List<GuessDb> list = db.findAll(Selector.from(GuessDb.class));
        SDLog.d(TAG,"-guessdb->"+list.size());
        for (GuessDb guess :list){
            SDLog.d(TAG,"-guessdb selectall1->"+guess.toString());
        }
        GuessDb guess = db.findFirst(Selector.from(GuessDb.class).where("level_id", "=", 1).and("mission_id", "=", 2));
            SDLog.d(TAG,"-guessdb findFirst->"+guess.toString());
    }

    public void testSelectGame()throws DbException{
        DbUtils db = DbUtils.create(getContext(), Constants.GUESSPIC_DB);

        List<GuessSmallMissionInfo> list = db.findAll(Selector.from(GuessSmallMissionInfo.class).where("levelId","=",1).and("missionid","=",1));
        SDLog.d(TAG,"-db->"+list.size());
        GuessSmallMissionInfo missionInfo = list.get(list.size()-1);
        SDLog.d(TAG,"-db size-1->"+missionInfo.toString());
        for (GuessSmallMissionInfo info: list){
            SDLog.d(TAG,"-db info->"+info.toString());
            if (info.getMissionstatus()==1){
                SDLog.d(TAG,"-return true");
            }else{
                SDLog.d(TAG,"-return false");
            }
        }
    }

    public void testGetFile(){
        String missionurl = "http://119.254.108.108:16302/caitu/junior_1.zip";
//        int last = missionurl.lastIndexOf("/")+1;
        String filename = missionurl.substring(missionurl.lastIndexOf("/") + 1);
        SDLog.d(TAG,"-url->"+filename);
    }

    public void testSelect() throws DbException {
        DbUtils db = DbUtils.create(getContext(), Constants.GUESSPIC_DB);
        final List<GuessPicLevel> guessPicLevles = db.findAll(GuessPicLevel.class);
        SDLog.d(TAG,"--size>"+guessPicLevles.size());


        GuessPicMisson composite = db.findFirst(Selector.from(GuessPicMisson.class).where("id", "=", 1).and("levelid","=",1));

        SDLog.d(TAG,"--composite>"+composite.toString());

        GuessSmallMissionInfo missionInfo = db.findFirst(Selector.from(GuessSmallMissionInfo.class).where("id","=",1).and("levelId", "=", 1).and("missionid", "=", 1));
        SDLog.d(TAG,"--missionInfo>"+missionInfo.getLevelId()+missionInfo.getMissionid()+missionInfo.getMissionstatus());
    }






    /*************** Xutil db CRUD  *********************/

    public void testinsert() throws DbException {
        DbUtils db = DbUtils.create(getContext(), "VideoInfo");

        // VideoInfo info = new VideoInfo("textdb", 453, 1251);
//        VideoInfo info = new VideoInfo();
//        info.setUrl("testulr");
//        info.setPlayid(454);
//        info.setVideoid(1254);
//        info.setCurrentPosition(0);
//        db.save(info);
//        Log.d(TAG, "--insert-->" + info.toString());
    }

    public void testSelectAll() throws DbException {
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        List<VideoInfo> info = db.findAll(Selector.from(VideoInfo.class));
//        for (VideoInfo VideoInfo : info) {
//            Log.d(TAG, "select all-->"+VideoInfo.toString());
//        }
    }
    
    @SuppressWarnings("unused")
    public void testSelectAllById() throws DbException{
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        List<VideoInfo> info = db.findAll(Selector.from(VideoInfo.class).where("playid", "=", 454).and("videoid","=",1254));
//        VideoInfo VideoInfo2 = info.get(0);
//            Log.d(TAG, "select by playid-->"+VideoInfo2.toString());
//            if(VideoInfo2.getCurrentPosition()==0){
//                //update data
//                VideoInfo2.setCurrentPosition(12345678);
//                db.update(VideoInfo2);
//                Log.d(TAG, "update OK!");
//            }else{
//                //insert data
//                db.save(new VideoInfo(123, 456, 789456));
//                Log.d(TAG, "save OK!");
//            }

    }
     
    public void testfindId() throws DbException{
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        VideoInfo info = db.findById(VideoInfo.class, 0); //通过ID查询
//        Log.d(TAG, "findId-->"+info.toString());
    }

    
    public void testUpdate() throws DbException{
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
////        db.update(VideoInfo.class, WhereBuilder.b("id", "=", 4),"videoid","currentPosition");
//        VideoInfo newinfo = new VideoInfo(452, 1523, 1234569); 
//        db.update(newinfo, "playid","videoid","currentPosition");
//        testSelectAll();
    }
    
    public void testDelete() throws DbException{
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        db.delete(VideoInfo.class, WhereBuilder.b("videoid", "=", 1253));
//        Log.d(TAG, "delete OK!");
    }
    
    /*************** Xutil db *********************/
    
}
