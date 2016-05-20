package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.util.HanziToPinyin;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.event.DayTaskEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.database.ContactsDb;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.register.Register.LoginRequest;
import com.zhicai.byteera.service.register.Register.LoginResponse;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

import static com.zhicai.byteera.service.UserAttribute.FriendUser;
import static com.zhicai.byteera.service.UserAttribute.GetUserFriendReq;
import static com.zhicai.byteera.service.UserAttribute.GetUserFriendResponse;

@SuppressWarnings("unused")
public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_username) EditText et_username;
    @Bind(R.id.et_password) EditText et_pwd;
    @Bind(R.id.title_bar) HeadViewMain mHeadView;
    List<String> list = new ArrayList<>();

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void updateUI() {

    }

    @OnTextChanged(value=R.id.et_username,callback =  OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(){
        et_pwd.setText("");
        if (et_username.getText().length() > 11) {
            ToastUtil.showToastText("手机号长度不能超过11位");
        }
    }


    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @OnClick({R.id.tv_login, R.id.tv_register1, R.id.tv_forget_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Login();
                break;
            case R.id.tv_register1:
                ActivityUtil.startActivity(this, new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_pwd:
                ActivityUtil.startActivity(this, new Intent(this, FindPwdActivity.class));
                break;
            case R.id.img_sina:
                break;
            case R.id.img_qq:
                break;
            case R.id.img_wx:
                wxLogin();
                break;
        }

    }



    private void Login() {
        final String username = et_username.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (checkLoginError(username, pwd))
            return;
        dialog.setMessage("正在登陆...");
        dialog.show();
        LoginRequest login = LoginRequest.newBuilder().setMobilePhone(username).setPassword(pwd).build();
        TiangongClient.instance().send("chronos", "login", login.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    LoginResponse response = LoginResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        getSex(response.getSex().getNumber());
                        //保存用户信息
                        ZCUser user = new ZCUser(response);
                        PreferenceUtils.getInstance(baseContext).setUserInfo(user);

                        PreferenceUtils.getInstance(baseContext).setUserName(response.getNickname().equals("") ? response.getMobilePhone() : response.getNickname());
                        PreferenceUtils.getInstance(baseContext).setUserId(response.getUserId());
                        PreferenceUtils.getInstance(baseContext).setMobilePhone(response.getMobilePhone());
                        PreferenceUtils.getInstance(baseContext).setPwd(et_pwd.getText().toString());
                        MyApp.getInstance().setCoinCnt(response.getCoin());

//                        String easemob_id = user.getChatAccount();
//                        String ease_username = easemob_id.substring(0, easemob_id.lastIndexOf(":"));
//                        String ease_pwd = easemob_id.substring(easemob_id.lastIndexOf(":") + 1);
//                        testGetUserFirend(user.getUser_id());
//                        LoginEase(ease_username, ease_pwd);
                        setUserDeviceId();
                        ToastUtil.showToastText("登录成功");
                        if (dialog.isShowing()) dialog.dismiss();
                        PreferenceUtils.getInstance(baseContext).setLogined(true);
                        setResult(Constants.RESULT_SUCCESS);
                        ActivityUtil.finishActivity(baseContext);
                        EventBus.getDefault().post(new DayTaskEvent(true));
                    } else {
                        ToastUtil.showToastText("用户名或密码不正确");
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUserDeviceId() {
        UserAttribute.SetUserDeviceIdReq user = UserAttribute.SetUserDeviceIdReq.newBuilder().setDeviceId(JPushInterface.getRegistrationID(this)).setUserId(MyApp.getInstance().getUserId()).build();

        TiangongClient.instance().send("chronos", "set_user_device_id", user.toByteArray(), new BaseHandlerClass() {

            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    Common.CommonResponse comm = Common.CommonResponse.parseFrom(buffer);
                    if (comm.getErrorno()==0){
                        Log.d("login","RegistrationID ok,"+JPushInterface.getRegistrationID(baseContext));
                    }else{
                        Log.d("login", "RegistrationID error");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /** 获取用户的朋友 */
    public void testGetUserFirend(String userId) {
        GetUserFriendReq req = GetUserFriendReq.newBuilder().setUserId(userId).build();
        TiangongClient.instance().send("chronos", "get_user_friend_user", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    GetUserFriendResponse res = GetUserFriendResponse.parseFrom(buffer);
                    if (res.getErrorno() == 0) {
                        List<UserAttribute.FriendUser> userList = res.getUserList();
                        for (FriendUser user : userList) {
                            String easemob_id = user.getChatAccount();
                            String ease_username = easemob_id.substring(0, easemob_id.lastIndexOf(":"));
                            String ease_pwd = easemob_id.substring(easemob_id.lastIndexOf(":") + 1);
                            list.add(ease_username);
                            try {
                                db.save(new ZCUser(user.getUserId(), ease_username, user.getNickname(), user.getHeadPortrait()));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ToastUtil.showToastText("获取好友失败");
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getSex(int number) {
        if (number == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    private boolean checkLoginError(String username, String pwd) {
        if (ToastUtil.showNetConnectionError(baseContext)) {
            return true;
        }
        return ToastUtil.showPhoneError(username) || ToastUtil.showPwdError(pwd);
    }


    public void LoginEase(final String username, final String password) {
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(username, password, new EMCallBack() {
            public void onSuccess() {
                // 登陆成功，保存用户名密码
                PreferenceUtils.getInstance(baseContext).setUserName(username);
                PreferenceUtils.getInstance(baseContext).setPwd(password);
                try {
                    // ** 第一次登录或者之前logout后，加载所有本地群和回话
                    // ** manually load all local groups and
                    // conversations in case we are auto login
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();

                    // demo中简单的处理成每次登陆都去获取好友username，开发者自己根据情况而定
//                    List<String> usernames = EMContactManager.getInstance().getContactUserNames();
                    List<String> usernames = list;
                    Map<String, ZCUser> userlist = new HashMap<String, ZCUser>();
                    for (String username : usernames) {
                        ZCUser chat = db.findFirst(Selector.from(ZCUser.class).where("chatAccount", "=", username));
                        ZCUser user = new ZCUser();
                        user.setUsername(username);
                        user.setNick(chat.getNickname());
                        user.setAvatar(chat.getAvatar());
                        setUserHearder(username, user);
                        userlist.put(username, user);
                    }
                    // 添加user"申请与通知"
                    ZCUser newFriends = new ZCUser();
                    newFriends.setUsername(Constants.NEW_FRIENDS_USERNAME);
                    newFriends.setNick("申请与通知");
                    newFriends.setHeader("");
                    userlist.put(Constants.NEW_FRIENDS_USERNAME, newFriends);
                    // 添加"群聊"
                    ZCUser groupUser = new ZCUser();
                    groupUser.setUsername(Constants.GROUP_USERNAME);
                    groupUser.setNick("群聊");
                    groupUser.setHeader("");
                    userlist.put(Constants.GROUP_USERNAME, groupUser);

                    // 存入内存
                    MyApp.getInstance().setContactList(userlist);
                    // 存入db
                    ContactsDb cdb = new ContactsDb(baseContext);
                    List<ZCUser> users = new ArrayList<ZCUser>(userlist.values());
                    cdb.saveContactList(users);

                    // 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
                    EMGroupManager.getInstance().getGroupsFromServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtil.showToastText("登录成功");
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                PreferenceUtils.getInstance(baseContext).setLogined(true);
                setResult(Constants.RESULT_SUCCESS);
                ActivityUtil.finishActivity(baseContext);
            }

            public void onError(final int code, final String message) {
                ToastUtil.showToastText("登录失败");
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override public void onProgress(int i, String s) {

            }
        });
    }


    /** 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人 */
    protected void setUserHearder(String username, ZCUser user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constants.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1).toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }

    private void wxLogin() {

    }
}


