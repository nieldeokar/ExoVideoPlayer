package com.niel.exoviewpager.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by nileshdeokar on 05/10/2017.
 */

public class GalleryModel implements Parcelable {

    private String messageUuid;

    private String conversationUuid;

    private String mimeType;

    private String name;

    private long time;

    private String filePath;

    private int resourcePath;

    private Uri fileUri;

    public GalleryModel(){}

    public GalleryModel(String name,String mimeType,int resourcePath){
        this.name = name;
        this.mimeType = mimeType;
        this.resourcePath = resourcePath;
    }

    public GalleryModel(String name,Uri uri,int resourcePath){
        this.name = name;
        this.fileUri = uri;
        this.resourcePath = resourcePath;
    }

    public GalleryModel(String name,String mimeType,String filePath){
        this.name = name;
        this.mimeType = mimeType;
        this.filePath = filePath;
    }

    protected GalleryModel(Parcel in) {
        messageUuid = in.readString();
        conversationUuid = in.readString();
        mimeType = in.readString();
        name = in.readString();
        time = in.readLong();
        filePath = in.readString();
        fileUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<GalleryModel> CREATOR = new Creator<GalleryModel>() {
        @Override
        public GalleryModel createFromParcel(Parcel in) {
            return new GalleryModel(in);
        }

        @Override
        public GalleryModel[] newArray(int size) {
            return new GalleryModel[size];
        }
    };

    public String getMessageUuid() {
        return messageUuid;
    }

    public void setMessageUuid(String messageUuid) {
        this.messageUuid = messageUuid;
    }

    public String getConversationUuid() {
        return conversationUuid;
    }

    public void setConversationUuid(String conversationUuid) {
        this.conversationUuid = conversationUuid;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }


    public boolean isImage() {
        return mimeType.equals(MimeType.JPEG.toString())
                || mimeType.equals(MimeType.PNG.toString())
                || mimeType.equals(MimeType.GIF.toString())
                || mimeType.equals(MimeType.BMP.toString())
                || mimeType.equals(MimeType.WEBP.toString());
    }

    public boolean isGif() {
        return mimeType.equals(MimeType.GIF.toString());
    }

    public boolean isAudio() {
        return mimeType.contains(MimeType.AUDIO.toString());
    }

    public boolean isVideo() {
        return mimeType.equals(MimeType.MPEG.toString())
                || mimeType.equals(MimeType.MP4.toString())
                || mimeType.equals(MimeType.QUICKTIME.toString())
                || mimeType.equals(MimeType.THREEGPP.toString())
                || mimeType.equals(MimeType.THREEGPP2.toString())
                || mimeType.equals(MimeType.MKV.toString())
                || mimeType.equals(MimeType.WEBM.toString())
                || mimeType.equals(MimeType.TS.toString())
                || mimeType.equals(MimeType.AVI.toString());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(messageUuid);
        parcel.writeString(conversationUuid);
        parcel.writeString(mimeType);
        parcel.writeString(name);
        parcel.writeLong(time);
        parcel.writeString(filePath);
        parcel.writeParcelable(fileUri, i);
    }

    public boolean checkIfFileExists(){
        return (new File(getFilePath()).exists());
    }

    public int getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(int resourcePath) {
        this.resourcePath = resourcePath;
    }
}
