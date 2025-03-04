/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhicai.byteera.activity.myhome;

import com.easemob.chat.EMContact;
import com.zhicai.byteera.commonutil.StringUtil;

import java.io.Serializable;

public class User extends EMContact implements Serializable, Comparable<User> {
    private int unreadMsgCount;
    private String header;
    private String avatar;
    private String userId ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof User)) && getUsername().equals(((User) o).getUsername());
    }

    @Override
    public String toString() {
        return nick == null ? username : nick;
    }

    @Override
    public int compareTo(User another) {
        return StringUtil.getPinYinHeadChar(this.getUsername()).charAt(0) - StringUtil.getPinYinHeadChar(another.getUsername()).charAt(0);
    }
}
