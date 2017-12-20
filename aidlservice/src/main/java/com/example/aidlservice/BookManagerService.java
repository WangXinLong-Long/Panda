package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    public BookManagerService() {
    }

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> callbackList = new RemoteCallbackList<>();
    private AtomicBoolean isDestroy = new AtomicBoolean(false);
    private static final String TAG = "BookManagerService";

    @Override
    public IBinder onBind(Intent intent) {
        int result = checkCallingOrSelfPermission("bookservice.permission");
        if (result == PackageManager.PERMISSION_DENIED)
            return null;
        return iBinder;
    }

    IBinder iBinder = new IBookManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = checkCallingOrSelfPermission("bookservice.permission");
            if (i == PackageManager.PERMISSION_DENIED)
                return false;
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages!=null&&packages.length>0){
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.example.aidlservice")){
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            callbackList.register(listener);
            Log.i(TAG, "callbackList: "+"register success");
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            int N = callbackList.beginBroadcast();
            callbackList.finishBroadcast();
            if (callbackList != null && N > 0) {
                callbackList.unregister(listener);
                Log.i(TAG, "callbackList: "+"unregiste success");
            } else {
                Log.i(TAG, "callbackList size is: " + N);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book first = new Book(1, "Android");
        Book second = new Book(2, "IOS");
        bookList.add(first);
        bookList.add(second);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ServiceWork());

    }

    public class ServiceWork implements Runnable {

        @Override
        public void run() {
            while (!isDestroy.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = bookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        bookList.add(newBook);
        int N = callbackList.beginBroadcast();

        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener broadcastItem = callbackList.getBroadcastItem(i);
            if (broadcastItem!=null){
                broadcastItem.onNewBookArrived(newBook);

            }
        }
        callbackList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        isDestroy.set(true);
        super.onDestroy();

    }
}
