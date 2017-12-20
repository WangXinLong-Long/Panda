package com.example.aidlservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contentProvider.PropertiesActivity;
import com.example.messager.MessageActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final int ON_NEW_BOOK_ARRIVED = 1;
    MyHandler myHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Messenger
//        Intent intent = new Intent(this, MessageActivity.class);
//        startActivity(intent);

//        AIDL
//        Intent intent = new Intent(this, BookManagerService.class);
//        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        Intent intent = new Intent(this, PropertiesActivity.class);
        startActivity(intent);

    }

    private IBookManager iBookManager;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);

            Book newBook = new Book(3, "任宁西");
            try {
                iBookManager.asBinder().linkToDeath(deathRecipient, 0);
                iBookManager.addBook(newBook);
                iBookManager.registerListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iBookManager = null;
        }


    };
    IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            myHandler.obtainMessage(ON_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    @Override
    protected void onDestroy() {
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()) {
            try {
                iBookManager.unregisterListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private static final String TAG = "MainActivity";

    public void getBookList(View view) {
        Log.i(TAG, "getBookList: ---->");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> bookList = null;
                try {
                    bookList = iBookManager.getBookList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "getBookList: " + bookList);
            }
        });

    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "receive notification: " + msg.obj);
                    break;

                default:
                    super.handleMessage(msg);
                    break;

            }

        }
    }


    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iBookManager == null) return;
            iBookManager.asBinder().unlinkToDeath(deathRecipient, 0);
            iBookManager = null;
        }
    };
}
