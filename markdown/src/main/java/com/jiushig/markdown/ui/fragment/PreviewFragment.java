package com.jiushig.markdown.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiushig.markdown.R;
import com.jiushig.markdown.ui.EditorActivity;
import com.jiushig.markdown.widget.MarkdownView;

/**
 * Created by zk on 2017/12/11.
 */

public class PreviewFragment extends Fragment {

    private LinearLayout main;

    protected MarkdownView markdownView;

    private EditorActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditorActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (main == null) {
            main = (LinearLayout) inflater.inflate(R.layout.fragment_preview, container, false);
            initViews(main);
        }
        return main;
    }

    private void initViews(LinearLayout main) {
        markdownView = (MarkdownView) main.findViewById(R.id.richView);

        markdownView.setListener(new MarkdownView.LinkClickListener() {
            @Override
            public void click(String url) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    public void load(String text){
        markdownView.setTextInBackground(text);
//        richView.setText(text);
    }
}
