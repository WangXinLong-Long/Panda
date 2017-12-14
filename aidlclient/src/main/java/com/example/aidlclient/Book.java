package com.example.aidlclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wxl
 * @date on 2017/12/14.
 * @describe:
 */

public class Book implements Parcelable {
    String name;
    int page;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.page);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
