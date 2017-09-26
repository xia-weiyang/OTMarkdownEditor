package com.jiushig.rich.utils;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

/**
 * Created by zk on 2017/9/13.
 */

public class MarkDownHandler {

    private static final String TAG = MarkDownHandler.class.getSimpleName();

    private Parser parser;
    private HtmlRenderer renderer;

    private static MarkDownHandler instance;

    public static MarkDownHandler getInstance() {
        if (instance == null)
            instance = new MarkDownHandler();
        return instance;
    }

    private MarkDownHandler() {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();

    }

    public String toHtml(String markdownText) {
        Node document = parser.parse(markdownText);
        String htm = renderer.render(document);
        htm = disposeHtml(htm);
        Log.d(TAG, htm);
        return htm;
    }

    public void toHtml(final String markdownText, final Callback callback) {
        new Thread() {
            @Override
            public void run() {
                Node document = parser.parse(markdownText);
                String htm = renderer.render(document);
                htm = disposeHtml(htm);
                final String html = htm;
                Log.d(TAG, htm);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.done(html);
                        }
                    });
                }
            }
        }.start();
    }

    private String disposeHtml(String htm) {
        htm = htm.replaceAll("<img src", "<img style='width:100%' src");
        return htm;
    }

    public interface Callback {
        void done(String html);
    }
}
