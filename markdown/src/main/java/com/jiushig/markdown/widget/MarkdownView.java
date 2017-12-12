package com.jiushig.markdown.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiushig.markdown.utils.MarkdownHandler;

/**
 * Created by zk on 2017/9/14.
 */

public class MarkdownView extends WebView {

    private static final String TAG = MarkdownView.class.getSimpleName();

    private String text;

    private LinkClickListener listener;

    public MarkdownView(Context context) {
        super(context);
        addClient();
    }

    public MarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addClient();
    }

    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addClient();
    }

    public void setTextInBackground(String text) {
        this.text = text;
        MarkdownHandler.getInstance().toHtml(text, new MarkdownHandler.Callback() {
            @Override
            public void done(String html) {
                if (html != null) {
                    loadDataWithBaseURL(null, html, "text/html", "utf8mb4", null);
                }
            }
        });
    }

    public void setText(String text){
        this.text = text;
        loadDataWithBaseURL(null, MarkdownHandler.getInstance().toHtml(text), "text/html", "utf8mb4", null);
    }

    public String getText() {
        return text;
    }

    private void addClient() {
        setWebViewClient(new MyWebClient());

        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);

        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    public void setListener(LinkClickListener listener) {
        this.listener = listener;
    }

    private final class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (listener != null) {
                listener.click(url);
            }
            return true;
        }
    }

    public interface LinkClickListener {
        void click(String url);
    }
}
