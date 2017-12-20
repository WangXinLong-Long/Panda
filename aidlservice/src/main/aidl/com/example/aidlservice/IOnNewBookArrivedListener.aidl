// IOnNewBookArrivedListener.aidl
package com.example.aidlservice;
import com.example.aidlservice.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
