package com.jiushig.markdown.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zk on 2017/10/1.
 */

public class RichUtils {

    /**
     * 获取真实文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 得到html标签中的图片地址
     *
     * @param html
     * @return
     */
    public static List<String> getImgUrl(String html) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("<a[^<]+</a>");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            Matcher ms = Pattern.compile("href=['\"].+['\"]").matcher(matcher.group());
            while (ms.find()) {
                list.add(ms.group().replaceAll("['\"0]", "").replace("href=", ""));
            }
        }
        return list;
    }
}
