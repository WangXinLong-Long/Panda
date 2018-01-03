package com.example.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerService extends Service {
    public TCPServerService() {
    }

    private boolean mIsServiceDestroy = false;

    private String[] mDefinedMessages = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new TCPServer());
    }

    private static final String TAG = "TCPServerService";
    public class TCPServer implements Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8686);
            } catch (IOException e) {
                Log.e(TAG, "run: establish tcp server failed,port :8686" );
                System.err.print("establish tcp server failed,port :8686");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestroy) try {
                Socket client = serverSocket.accept();
                Log.i(TAG, "run: accept");
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
//                        responseClient(client);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
