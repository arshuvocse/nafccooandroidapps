package com.saidur.creatrix.unicorn.salessolution.UtilityHelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class CameraHelper {
    //here get file path from uri

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
    {
        String ret = "";
        // Query the URI with the condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if(cursor!=null)
        {
            boolean moveToFirst = cursor.moveToFirst();
            if(moveToFirst)
            {
                // Get columns name by URI type.
                String columnName = MediaStore.Images.Media.DATA;
                if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Images.Media.DATA;
                }else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Audio.Media.DATA;
                }else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Video.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
    }
    public static boolean isDocumentUri(Activity ctx, Uri uri)
    {
        boolean ret = false;
        if(ctx != null && uri != null) {
            ret = DocumentsContract.isDocumentUri(ctx, uri);
        }
        return ret;
    }
    public static boolean isContentUri(Uri uri)
    {
        boolean ret = false;
        if(uri != null) {
            String uriSchema = uri.getScheme();
            if("content".equalsIgnoreCase(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }
    public static boolean isFileUri(Uri uri)
    {
        boolean ret = false;
        if(uri != null) {
            String uriSchema = uri.getScheme();
            if("file".equalsIgnoreCase(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }
    /* Check whether this document is provided by ExternalStorageProvider. Return true means the file is saved in external storage. */
    public static boolean isExternalStoreDoc(String uriAuthority)
    {
        boolean ret = "com.android.externalstorage.documents".equals(uriAuthority);
        return ret;
    }
    /* Check whether this document is provided by DownloadsProvider. return true means this file is a downloaded file. */
    public static boolean isDownloadDoc(String uriAuthority)
    {
        boolean ret = "com.android.providers.downloads.documents".equals(uriAuthority);
        return ret;
    }
    /*
    Check if MediaProvider provides this document, if true means this image is created in the android media app.
    */
    public static boolean isMediaDoc(String uriAuthority)
    {
        boolean ret = "com.android.providers.media.documents".equals(uriAuthority);
        return ret;
    }
    /*
    Check whether google photos provide this document, if true means this image is created in the google photos app.
    */
    public static boolean isGooglePhotoDoc(String uriAuthority)
    {
        boolean ret = "com.google.android.apps.photos.content".equals(uriAuthority);
        return ret;
    }
}
