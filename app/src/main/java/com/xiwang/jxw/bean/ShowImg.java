package com.xiwang.jxw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowImg implements Parcelable {
        private static final long serialVersionUID = 3611224074993323709L;
       public  String path;
       public int id;
       public int percent=0;


       public static final Parcelable.Creator<ShowImg> CREATOR = new Creator<ShowImg>() {
           public ShowImg createFromParcel(Parcel source) {
               ShowImg mBook = new ShowImg();
               mBook.path = source.readString();
               mBook.id = source.readInt();
               return mBook;
           }
           public ShowImg[] newArray(int size) {
               return new ShowImg[size];
           }
       };


       @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(path);
            dest.writeInt(id);
        }
    }