package com.panda.pandalibs.entry;


import com.panda.pandalibs.base.mvp.model.BaseEntity;
import com.panda.pandalibs.entry.bean.UserInfo;
import com.panda.pandalibs.entry.bean.UserToken;

/**
 * Created by Z7Dream on 2017/1/12 16:38.
 * Email:zhangxyfs@126.com
 */

public class LoginUserInfoEntity extends BaseEntity<LoginUserInfoEntity.ResultBean> {

    public static class ResultBean {
        public UserToken userToken;
        public UserInfo user;
        public Thirdparty thirdparty;
        public String toBindMobile;
    }

    public static class Thirdparty {
        public Long id;
        public long createTime;
        public long updateTime;
        public int userId;
        public String type;
        public String thirdpartyId;
        public String token;
        public String expired;
        public String extension;
    }

}
