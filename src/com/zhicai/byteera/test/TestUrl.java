package com.zhicai.byteera.test;

import android.os.Environment;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.easemob.util.HanziToPinyin;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.commonutil.SDLog;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.goodsexchange.Exchange;
import com.zhicai.byteera.service.information.Information.MainPage;
import com.zhicai.byteera.service.information.Information.MainPageReq;
import com.zhicai.byteera.service.information.InformationSecondary.DetailsPage;
import com.zhicai.byteera.service.information.InformationSecondary.DetailsPageReq;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.register.Register;
import com.zhicai.byteera.service.register.Register.LoginRequest;
import com.zhicai.byteera.service.register.Register.LoginResponse;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.train_camp.AchieveReward;
import com.zhicai.byteera.service.train_camp.GuessPic;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * junit
 * @author xinglefly
 */

public class TestUrl extends InstrumentationTestCase {

    private static final String TAG = TestUrl.class.getSimpleName();


    public void testJava() {
        int i = 12;
        float y = (float)i;
        Log.d(TAG,"y-->"+y);
        Log.d(TAG,"y-->"+y/100);
    }


    private void initConn() {
        //ip 端口119.254.108.108   port:9998  ；  本地调试192.168.0.192
        TiangongClient.instance().init("dev.zijieshidai.com", 9998);
//		TiangongClient.instance().connect("119.254.108.108", 9998);
    }

    public void testRegister() {
        initConn();
        Register.RegisterReq user = Register.RegisterReq.newBuilder().setMobilePhone("18612951368").setPassword("123456").setMobileValidateCode("1234").build();

        TiangongClient.instance().send("chronos", "register", user.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    Common.CommonResponse comm = Common.CommonResponse.parseFrom(buffer);
                    SDLog.d(TAG, "register-->" + comm.getErrorno() + "," + comm.getErrorDescription());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }


    public void testLogin() {
        initConn();
        LoginRequest login = LoginRequest.newBuilder().setMobilePhone("18612951361").setPassword("111111").build();
        byte[] request = login.toByteArray();
        TiangongClient.instance().send("chronos", "login", request, new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    LoginResponse c = LoginResponse.parseFrom(buffer);
                    SDLog.d(TAG, "login-->" + c.getErrorno() + ",-description-->" + c.getErrorDescription() + ",uid-->" + c.getUserId() + ",-obj->" + c.toString());
                    //uid-->553db9f4fa78153a87c7511c
                    //uid-->55307cb9fa781508e5e1ad95  chat_account: "0:123456"
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }


    /**
     * 关注好友
     */
    public void testWathUser() {
        initConn();
        UserAttribute.WatchUserReq req = UserAttribute.WatchUserReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").setOtherUserId("55307cb9fa781508e5e1ad95").build();
        TiangongClient.instance().send("chronos", "watch_user", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.WatchUserResponse res = UserAttribute.WatchUserResponse.parseFrom(buffer);
                    SDLog.d(TAG, "get user_friend-->" + res.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取用户的朋友
     */
    public void testGetUserFirend() {
        initConn();
        UserAttribute.GetUserFriendReq req = UserAttribute.GetUserFriendReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "get_user_friend_user", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.GetUserFriendResponse res = UserAttribute.GetUserFriendResponse.parseFrom(buffer);
                    SDLog.d(TAG, "get user_friend-->" + res.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void testInformation() {
        initConn();
        MainPageReq page = MainPageReq.newBuilder().setProductType(Common.ProductType.valueOf(1)).build();

        TiangongClient.instance().send("chronos", "get_main_page", page.toByteArray(), new BaseHandlerClass() {


            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    MainPage page = MainPage.parseFrom(buffer);
                    SDLog.d(TAG, "info-->" + page.getZixunList().get(1).getZixunId() + "," + page.getErrorno() + ",-description-->" + page.getErrorDescription() +
                            ",image-->" + page.getMainImageCount() + page.getMainImage(0).getImageUrl() + ",zixun-->" + page.getZixunCount() + "," + page.getZixun(0).getPublishTime());
                    SDLog.d(TAG, "info ADImg-->" + page.getMainImageList().get(0).toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }


    public void testInforSecondary() {
        initConn();
        DetailsPageReq sec = DetailsPageReq.newBuilder().setZixunId("552d2343dde71b9de7f8b71b").build();
        TiangongClient.instance().send("chronos", "get_info", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    System.out.println("second");
                    DetailsPage page = DetailsPage.parseFrom(buffer);
                    SDLog.d(TAG, "infosecondary-->" + page.getErrorno() + "," + page.getErrorDescription() + ",comment-->");
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testProducts() {
        initConn();
        FinancingProduct.ProductListReq sec = FinancingProduct.ProductListReq.newBuilder().
                setProductType(Common.ProductType.valueOf(2)).setUserId("552d2343dde71b9de7f8b71b").build();

        TiangongClient.instance().send("chronos", "financing_get_product", sec.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    FinancingProduct.ProductListResponse response = FinancingProduct.ProductListResponse.parseFrom(buffer);
                    Log.d(TAG, "infosecondary-->" + response.getErrorno() + "," + response.getErrorDescription() + ",comment-->");
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testDynamic() {
        final Dynamic.GetMsgResponse[] response = new Dynamic.GetMsgResponse[1];
        initConn();
        Dynamic.GetMsg msg = Dynamic.GetMsg.newBuilder().build();

        TiangongClient.instance().send("chronos", "licaiquan_get_msg", msg.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    response[0] = Dynamic.GetMsgResponse.parseFrom(buffer);
                    //Log.d(TAG, "count-->" + response.getItemCount() + "," + response.getItem(0).getUserId());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
        assertTrue(response[0] != null);
    }

    public void testGuess() {
        initConn();
        GuessPic.MissionPageReq req = GuessPic.MissionPageReq.newBuilder().build();

        TiangongClient.instance().send("chronos", "mission", req.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    GuessPic.MissionPage res = GuessPic.MissionPage.parseFrom(buffer);
                    SDLog.d(TAG, "guess-->" + res.getErrorno() + ",size：" + res.getMissionLevelList().size() + res.getMissionLevel(0).getLevelName() + ",1-->" + res.getMissionLevel(1).getLevelName()
                            + ",2-->" + res.getMissionLevel(2).getLevelName() + ",3-->");
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void testCoin() {
        initConn();
        UserAttribute.CoinDeltaReq req = UserAttribute.CoinDeltaReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").setValue(-30).build();
        TiangongClient.instance().send("chronos", "coindelta", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.CoinDeltaResponse res = UserAttribute.CoinDeltaResponse.parseFrom(buffer);
                    SDLog.d(TAG, "coin-->" + res.toString() + "," + res.getCoinCount());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testAchieve() {
        initConn();
        AchieveReward.AchieveReq req = AchieveReward.AchieveReq.newBuilder().build();
//        cluster : goumang
//        optype:   获取成就列表  get_achieve_list
//        领取奖励   get_reward
        TiangongClient.instance().send("chronos", "get_achieve_list", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    AchieveReward.AchieveResponse res = AchieveReward.AchieveResponse.parseFrom(buffer);
                    SDLog.d(TAG, "achieve-->" + res.toString());
                    for (AchieveReward.AchieveLevel task : res.getAchieveList()) {
                        final String point = task.getJumpPoint().toString();
                        if (task.hasJumpUrl()) {
                            SDLog.d(TAG, "achieve jumpurl-->" + task.getJumpUrl());
                        }
                        if (task.hasJumpPoint()) {
                            SDLog.d(TAG, "jumppoint-->" + task.getJumpPoint());
                        }
                        SDLog.d(TAG, "achieve task-->" + task.toString());
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //获取机构列表
    public void testInstitution() {
        initConn();
        InstitutionAttribute.FinancingCompanyListReq sec = InstitutionAttribute.FinancingCompanyListReq.newBuilder().build();

        TiangongClient.instance().send("chronos", "get_financing_company_list", sec.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    InstitutionAttribute.FinancingCompanyListResponse response = InstitutionAttribute.FinancingCompanyListResponse.parseFrom(buffer);
                    Log.d(TAG, "institution -->" + response.getErrorno() + "," + response.getErrorDescription() + ",company-->" + response.getFinancingCompanyCount());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //获取虚拟道具
    public void testXUni() {
        initConn();
        Exchange.GetExchangeItemListReq sec = Exchange.GetExchangeItemListReq.newBuilder().setItemType(2).setUserId("553db9f4fa78153a87c7511c").build();

        TiangongClient.instance().send("chronos", "get_exchange_item_list", sec.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    Exchange.GetExchangeItemListResponse response = Exchange.GetExchangeItemListResponse.parseFrom(buffer);
                    Log.d(TAG, "xuni -->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void testDoExchange() {
        initConn();
        Exchange.DoExchangeItemReq sec = Exchange.DoExchangeItemReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").setItemId("5600e9b6eea6ef63efc6a899").build();
        TiangongClient.instance().send("chronos", "do_exchange_item", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    Exchange.DoExchangeItemResponse response = Exchange.DoExchangeItemResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**所有的理财圈小组**/
    public void testLicaiquan() {
        initConn();
        DynamicGroupV2.LicaiquanGroupGetAllReq sec = DynamicGroupV2.LicaiquanGroupGetAllReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "licaiquan_group_get_all", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    DynamicGroupV2.LicaiquanGroupGetAllResponse response = DynamicGroupV2.LicaiquanGroupGetAllResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**join group list**/
    public void testJoinLicaiquan() {
        initConn();
        UserAttribute.GetUserWatchInfoReq sec = UserAttribute.GetUserWatchInfoReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "get_user_watch_groups", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    UserAttribute.GetUserWatchGroupsResponse response = UserAttribute.GetUserWatchGroupsResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**已加入的小组**/
    public void testJoinGroup() {
        initConn();
        DynamicGroupV2.LicaiquanGroupGetJoinedReq sec = DynamicGroupV2.LicaiquanGroupGetJoinedReq.newBuilder().setUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "licaiquan_group_get_joined", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    DynamicGroupV2.LicaiquanGroupGetJoinedResponse response = DynamicGroupV2.LicaiquanGroupGetJoinedResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**获取机构页**/
    public void testOrganization() {
        initConn();
        InstitutionAttribute.InstitutionGetByTypeReq sec = InstitutionAttribute.InstitutionGetByTypeReq.newBuilder().setInstituteType(Common.InstituteType.Zhongchou).setUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "institution_get_by_type", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    InstitutionAttribute.InstitutionGetByTypeResponse response = InstitutionAttribute.InstitutionGetByTypeResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**关注**/
    public void testAddition() {
        initConn();
        final InstitutionAttribute.WatchInstitutionReq req = InstitutionAttribute.WatchInstitutionReq.newBuilder().setUserId("55307727fa781508e5e1ad94").setOtherUserId("553db9f4fa78153a87c7511c").build();
        TiangongClient.instance().send("chronos", "watch_institution", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.WatchInstitutionResponse response = InstitutionAttribute.WatchInstitutionResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**关注小组**/
    public void testJoinGruop() {
        initConn();
        final DynamicGroupV2.LicaiquanGroupJoinReq req = DynamicGroupV2.LicaiquanGroupJoinReq.newBuilder().setMyUserId("55307727fa781508e5e1ad94").setLicaiquanGroupId("555408ab421aa9d21322db72").build();
        TiangongClient.instance().send("chronos", "licaiquan_group_join", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupJoinResponse response = DynamicGroupV2.LicaiquanGroupJoinResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void testLeaveGruop() {
        initConn();
        final DynamicGroupV2.LicaiquanGroupLeaveReq req = DynamicGroupV2.LicaiquanGroupLeaveReq.newBuilder().setMyUserId("55307727fa781508e5e1ad94").setLicaiquanGroupId("555408ab421aa9d21322db72").build();
        TiangongClient.instance().send("chronos", "licaiquan_group_leave", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupLeaveResponse response = DynamicGroupV2.LicaiquanGroupLeaveResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    /**收益list**/
    public void testShouyList() {
        initConn();
        final FinancingProduct.IncomeComparisionGetReq req = FinancingProduct.IncomeComparisionGetReq.newBuilder().build();
        TiangongClient.instance().send("chronos", "income_comparision_get", req.toByteArray(), new BaseHandlerClass() {
            @Override public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.IncomeComparisionGetResponse response = FinancingProduct.IncomeComparisionGetResponse.parseFrom(buffer);
                    Log.d(TAG, "res-->" + response.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }






    public void testpath() {
        String Path = Environment.getExternalStorageDirectory().getAbsolutePath();
        SDLog.d(TAG, "--getfile-->" + Path);
    }

    public void testStr() {
        /*String str = "上海银行";
        SDLog.d(TAG, "str 1-->" + str.substring(0, 1));
        SDLog.d(TAG, "str 1-->" + str.substring(1, 2));

        SDLog.d(TAG, "-->");

        SDLog.d(TAG, "-path->" + s.substring(s.lastIndexOf("/") + 1));

        List<String> argList = Arrays.asList(s);
        SDLog.d(TAG, "str convert list-->" + argList.size() + ",-->" + argList.toArray());*/

        StringBuffer sb = new StringBuffer();
        sb.append("1").append(",");
        sb.append("3").append(",");
        sb.append("5").append(",");
        sb.append("6").append(",");
        sb.append("6").append(",");
        sb.append("7");
        sb.append("10");
        sb.append("\"");
//        SDLog.d(TAG, "compare-->" + sb.toString().contains("3"));
//        SDLog.d(TAG, "compare-->" + sb.toString().matches("3"));
        SDLog.d(TAG, "sb-->" + sb.toString());
//        SDLog.d(TAG,"sb index-->" + sb.substring(0, sb.indexOf(",")));
//        for (int i=0;i<sb.length();i++){
////            i=sb.indexOf(",");
//        }

       /* sb.insert(2, "xx");
        SDLog.d(TAG, "sb insert-->" + sb.toString() + ",size->" + sb.length());*/
//        sb.delete(0, sb.toString().length());


      /*  if (sb.toString().contains("3")){
            SDLog.d(TAG,"-sb->3");
        }else if (sb.toString().contains("5")){
            SDLog.d(TAG,"-sb->5");
        }else if (sb.toString().contains("6")){
            SDLog.d(TAG,"-sb->6");
        }else{
                SDLog.d(TAG,"-sb->other");
        }
*/

//        int i1 = sb.indexOf("6");

       /* if (sb.toString().contains("67")){
//            sb.delete(4,6);
            sb.deleteCharAt(6);
            sb.deleteCharAt(5);
        }*/


//        SDLog.d(TAG, "sb-->" + sb.toString());

       /* String s = "细化你你";
        char[] rand = new char[4];
        for (int i=0;i<s.length();i++){
            SDLog.d(TAG,"-iterator str->"+s.charAt(i));
            rand[i] = s.charAt(i);
        }*/

       /* int[] ran =  new int[4];
        for (int i=0;i<4;i++){
            ran[i] = new Random().nextInt(9);
            SDLog.d(TAG,"-- ran->"+ran[i]);
        }*/

       /* for (int i=0;i<4;i++){
            Random ran = new Random();
            SDLog.d(TAG,"--random->"+i+",j->"+ran.nextInt(9));
            SDLog.d(TAG,"--math->"+Math.round(Math.random()*10)+",i->"+i);
        }*/


     /*   for (int i=1;i<55;i+=6){
            SDLog.d(TAG,"i-->"+i);
        }
    */
        //过滤字母
        String number = "我360田鸡";
//        number = number.replaceAll("[^(0-9)]", "");
//        number = number.replaceAll("[^(A-Za-z)]", "");
        if(Character.isDigit(number.charAt(0))){

        }
        number = HanziToPinyin.getInstance().get(number.substring(0, 1)).get(0).target.substring(0,1).toUpperCase();


        Log.d(TAG,"filter letter-->"+number);
    }


    public void testList() {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        String pp = "";

        SDLog.d(TAG, "-list->" + list.toString());
        String substring = list.toString().substring(1, list.toString().length() - 1);
        SDLog.d(TAG, "-list split->" + substring);
        String[] split = substring.split(",");
        SDLog.d(TAG, "-list sp->" + split.toString());

        for(String s: list){
            pp += s+",";
        }
        Log.d(TAG,"-str->"+pp);
        /*for (String s : list) {
            SDLog.d(TAG, "-list 未处理->" + s);
        }

        list.set(2, "王");
        list.add("f");
        for (String s : list) {
            SDLog.d(TAG, "-list 替换->" + s);
        }

        list.clear();
        list.remove(3);
        list.remove(4);
        for (String s : list) {
            SDLog.d(TAG, "-list 移除->" + s);
        }*/

    }

    public void testMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "王");
        map.put(2, "李");
        map.put(3, "郑");
        map.put(4, "陈");

        //效率高的原理是entryset遍历一次，key和value都是放在entry
        for (Map.Entry<Integer, String> m : map.entrySet()) {
            SDLog.d(TAG, "-map entry->" + m.getValue() + "--key>" + m.getKey());
            if (m.getValue().equals("陈")) {
                SDLog.d(TAG, "-map --key>" + m.getKey());
            }
        }

        //效率慢的原理：keyset先转换为iterator之后，然后在keyset里面取出key所对应的value.
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = map.get(key);
            SDLog.d(TAG, "-map keyset->" + key + "--key>" + value);
        }

    }


    public void testToDay() {
        //1443206843
        Date date = new Date(1443206843*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SDLog.d(TAG, "--today->" + format.format(date));
    }


    /**************** Xutil db CRUD*********************/
    public void testinsert() throws DbException {
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");

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
    public void testSelectAllById() throws DbException {
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

    public void testfindId() throws DbException {
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        VideoInfo info = db.findById(VideoInfo.class, 0); //通过ID查询
//        Log.d(TAG, "findId-->"+info.toString());
    }


    public void testUpdate() throws DbException {
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
////        db.update(VideoInfo.class, WhereBuilder.b("id", "=", 4),"videoid","currentPosition");
//        VideoInfo newinfo = new VideoInfo(452, 1523, 1234569); 
//        db.update(newinfo, "playid","videoid","currentPosition");
//        testSelectAll();
    }

    public void testDelete() throws DbException {
//        DbUtils db = DbUtils.create(getContext(), "VideoInfo");
//        db.delete(VideoInfo.class, WhereBuilder.b("videoid", "=", 1253));
//        Log.d(TAG, "delete OK!");
    }

    /*************** Xutil db *********************/


}
