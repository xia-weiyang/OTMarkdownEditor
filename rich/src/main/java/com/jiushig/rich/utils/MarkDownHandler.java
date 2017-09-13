package com.jiushig.rich.utils;


import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

/**
 * Created by zk on 2017/9/13.
 */

public class MarkDownHandler {

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
        return renderer.render(document);
    }

//    public void toHtml(final String markdownText, final Callback callback) {
//        new Thread() {
//            @Override
//            public void run() {
//                if (callback != null) {
//                    callback.done(processor.markdownToHtml(markdownText));
//                }
//            }
//        }.start();
//    }

    public interface Callback {
        void done(String html);
    }
}
