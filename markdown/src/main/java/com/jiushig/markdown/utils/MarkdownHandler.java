package com.jiushig.markdown.utils;


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

public class MarkdownHandler {

    private static final String TAG = MarkdownHandler.class.getSimpleName();

    private Parser parser;
    private HtmlRenderer renderer;

    private static MarkdownHandler instance;

    public static MarkdownHandler getInstance() {
        if (instance == null)
            instance = new MarkdownHandler();
        return instance;
    }

    private MarkdownHandler() {
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
        if (markdownText == null)
            return;

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
        return "<html>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/otStyle.css\" />" +
                "<body>" +
                htm +
                "</body></html>";
    }

    public interface Callback {
        void done(String html);
    }
}
