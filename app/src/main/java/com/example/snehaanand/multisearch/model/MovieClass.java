//package com.example.snehaanand.multisearch.model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * Created by snehaanandyeluguri on 9/9/15.
// */
//public class MovieClass extends SampleClass implements Parcelable {
//
//
//
//    public String getRelease_date() {
//        return release_date;
//    }
//
//    public void setRelease_date(String release_date) {
//        this.release_date = release_date;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    private MovieClass(Parcel in) {
////        id=in.readInt();
////        original_title = in.readString();
////        poster_path = in.readString();
////        overview = in.readString();
////
////        display_image = in.readString();
////        media_type=in.readString();
//        release_date = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeInt(id);
////        dest.writeString(original_title);
////        dest.writeString(poster_path);
////        dest.writeString(overview);
//        dest.writeString(release_date);
////        dest.writeFloat(vote_average);
////        dest.writeString(display_image);
////        dest.writeString(media_type);
//    }
//
//    public static final Parcelable.Creator<MovieClass> CREATOR
//            = new Parcelable.Creator<MovieClass>() {
//        public MovieClass createFromParcel(Parcel in) {
//            return new MovieClass(in);
//        }
//
//        public MovieClass[] newArray(int size) {
//            return new MovieClass[size];
//        }
//    };
//}
