package com.example.messager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aidlservice.R;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";

    private static class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "receive msg from server: " + msg.getData().getString("reply"));
            super.handleMessage(msg);
        }
    }

    private Messenger service;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = new Messenger(iBinder);
            Message message = Message.obtain(null, MyConstant.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "this is client msg!!!");
            message.setData(bundle);
            message.replyTo = messenger;
            try {
                service.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = new Intent(this, MessagerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    Messenger messenger = new Messenger(new MessageHandler());

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
