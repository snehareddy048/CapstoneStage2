//package com.example.snehaanand.multisearch.model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * Created by snehaanandyeluguri on 7/17/16.
// */
//public class TVClass extends SampleClass implements Parcelable {
//
//
//
//
//    public String getFirst_air_date() {
//        return first_air_date;
//    }
//
//    public void setFirst_air_date(String first_air_date) {
//        this.first_air_date = first_air_date;
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    private TVClass(Parcel in) {
////        id=in.readInt();
////        original_title = in.readString();
////        poster_path = in.readString();
////        overview = in.readString();
//        first_air_date = in.readString();
////        vote_average = in.readFloat();
////        display_image = in.readString();
////        media_type=in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeInt(id);
////        dest.writeString(original_title);
////        dest.writeString(poster_path);
////        dest.writeString(overview);
//        dest.writeString(first_air_date);
////        dest.writeFloat(vote_average);
////        dest.writeString(display_image);
////        dest.writeString(media_type);
//    }
//
//    public static final Parcelable.Creator<TVClass> CREATOR
//            = new Parcelable.Creator<TVClass>()
//    {
//        public TVClass createFromParcel(Parcel in) {
//            return new TVClass(in);
//        }
//
//        public TVClass[] newArray(int size) {
//            return new TVClass[size];
//        }
//    };
//}
