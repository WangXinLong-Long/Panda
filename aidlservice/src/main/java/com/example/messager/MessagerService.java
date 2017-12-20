package com.example.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import static com.example.messager.MyConstant.MSG_FROM_CLIENT;

public class MessagerService extends Service {
    public MessagerService() {
    }

    private static final String TAG = "MessagerService";

    private static class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstant.MSG_FROM_CLIENT:
                    Log.i(TAG, "receive msg from client: " + msg.getData().getString("msg"));
                    Messenger replyTo = msg.replyTo;
                    Message message = Message.obtain(null, MyConstant.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "咳咳！！");
                    message.setData(bundle);
                    try {
                        replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }


        }
    }

    Messenger messenger = new Messenger(new MessageHandler());

    @Override
    public IBinder onBind(Intent intent) {
//        throw new IllegalArgumentException("md");
        return messenger.getBinder();
    }


}
