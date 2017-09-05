package com.panda.pandalibs.utils.utils;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.panda.pandalibs.utils.im.IMService;
import com.panda.pandalibs.utils.utils.tools.Utils;

/**
 * Created by wangxinlong on 2017/9/5.
 */

public class IMClient {
    /**
     * 环信 初始化，自动登录
     *
     * @param context
     */
    public static void imInit(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的
        options.setAcceptInvitationAlways(true);
        // 设置是否需要接受方已读确认
        options.setRequireAck(true);
        // s设置是否需要接受方送达确认,默认false
        options.setRequireDeliveryAck(false);
        //设置退出(主动和被动退出)群组时是否删除聊天消息
        options.setDeleteMessagesAsExitGroup(true);
        //设置自动登录
        options.setAutoLogin(true);
        //自动接收群邀请
        options.setAutoAcceptGroupInvitation(true);
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(Utils.isDebug());

        IMService.start(context);
    }
}
