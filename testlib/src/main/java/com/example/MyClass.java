package com.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MyClass {
    public static void main(String[] args) throws IOException {
//        "unionid"       -> "oKpPW01RPpzuJpD1T4u1aY2d8WvY"
//        "scope"         -> "snsapi_base,snsapi_userinfo,"
//        "expires_in"    -> "7200"
//        "access_token"  -> "56FmhHZCWGR55CQYBmG9QOvgHDLPi_as00yP1WE7Qh7JWz8TZTbS5nTwwevCwJ4teppFM4Lprhaapk3tKlPfeH7lezgr8P-i3InncdGUBQo"
//        "refresh_token" -> "_YSYNJcJoWcMqk9ix1fnsgfP-zy963Pea_3K7HdM1hH5OGmVIMKO23lHQxW88XC-oTnOO-_F_h119A0qca6MUUleJEZu6mm9SGOJZGtosVQ"
//        "openid"        -> "o11TYww_rCgB_fSLHc8GFQjVkBlk"

        HashMap<String, String> data = new HashMap<>();
        data.put("unionid", "oKpPW01RPpzuJpD1T4u1aY2d8WvY");
        data.put("scope", "snsapi_base,snsapi_userinfo,");
        data.put("expires_in", "7200");
        data.put("access_token", "56FmhHZCWGR55CQYBmG9QOvgHDLPi_as00yP1WE7Qh7JWz8TZTbS5nTwwevCwJ4teppFM4Lprhaapk3tKlPfeH7lezgr8P-i3InncdGUBQo");
        data.put("refresh_token", "_YSYNJcJoWcMqk9ix1fnsgfP-zy963Pea_3K7HdM1hH5OGmVIMKO23lHQxW88XC-oTnOO-_F_h119A0qca6MUUleJEZu6mm9SGOJZGtosVQ");
        data.put("openid", "o11TYww_rCgB_fSLHc8GFQjVkBlk");

        ThirdPartBody thirdPartBody = new ThirdPartBody();
        thirdPartBody.setUnionid(data.get("unionid"));
        thirdPartBody.setScope(data.get("scope"));
        thirdPartBody.setExpires_in(data.get("expires_in"));
        thirdPartBody.setAccess_token(data.get("access_token"));
        thirdPartBody.setRefresh_token(data.get("refresh_token"));
        thirdPartBody.setOpenid(data.get("openid"));
        System.out.println(thirdPartBody.toString());
        writeToFile();
        readFromFile();
    }

    private static void readFromFile() {
        InputStream inputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File("test.txt")));

            // 判断该输入流是否支持mark操作
            if (!inputStream.markSupported()) {
                System.out.println("mark/reset not supported!");
                return;
            }

            int ch;
            int count = 0;
            boolean marked = false;
            while ((ch = inputStream.read()) != -1) {
                System.out.print("." + ch);

                if ((ch == 4) && !marked) {
                    inputStream.mark(6);
                    marked = true;
                }

                if (ch == 8 && count < 2) {

                    inputStream.reset();
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeToFile() {
        OutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(new File("test.txt")));
            byte[] b = new byte[20];
            for (int i = 0; i < 20; i++)
                b[i] = (byte) i;
            output.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
