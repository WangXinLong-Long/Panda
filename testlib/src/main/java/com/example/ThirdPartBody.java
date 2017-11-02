package com.example;

public class ThirdPartBody {
    public String unionid;
    public String scope;
    public String expires_in;
    public String access_token;
    public String refresh_token;
    public String openid;


    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        String str =
                     "\"openid\":\""+openid+"\"," +
                     "\"unionid\":\""+unionid+"\"," +
                     "\"access_token\":\""+access_token+"\"," +
                     "\"refresh_token\":\""+refresh_token+"\"," +
                     "\"expires_in\":\""+expires_in+"\"," +
                     "\"scope\":\""+scope+"\"";
        String str2 =   "openid:"+""+"," +
                        "unionid:"+""+"," +
                        "access_token:"+""+"," +
                        "refresh_token:"+""+"," +
                        "expires_in:"+""+"," +
                        "scope:"+""+"";
        return str;
    }
}