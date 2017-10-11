package com.niel.exoviewpager.loader;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.CursorLoader;
import android.webkit.MimeTypeMap;

import com.niel.exoviewpager.model.GalleryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nileshdeokar on 11/10/2017.
 */

public class MediaLoader {

    Context mContext ;

    public MediaLoader(Context context){
        this.mContext = context;
    }

    private ArrayList<GalleryModel> galleryModels = new ArrayList<>();

    public List<GalleryModel> getAllMedia(){
        // Get relevant columns for use later.
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        // Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();

        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int imageUriColumn = cursor.getColumnIndex(
                MediaStore.Images.Media.DATA);

        if(cursor.moveToFirst()){

            do{
                String url = cursor.getString(imageUriColumn);
                galleryModels.add(new GalleryModel(cursor.getString(nameIndex),getMimeType(url),url));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return galleryModels;
    }


    public static String getMimeType(String url)
    {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public List<GalleryModel> getAllMediaFiles() {

        Cursor cursor;
        String absolutePathOfImage = null;
        Uri queryUri = MediaStore.Files.getContentUri("external");


        // Get relevant columns for use later.
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        // Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        cursor = mContext.getContentResolver().query(queryUri, projection, selection,
                null, null);

       int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
       int  column_index_mime = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            galleryModels.add(new GalleryModel("ExoViewPager",cursor.getString(column_index_mime),absolutePathOfImage));
        }


        return galleryModels;
    }

}
