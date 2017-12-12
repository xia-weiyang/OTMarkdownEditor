package com.jiushig.markdown.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.jiushig.markdown.R;
import com.jiushig.markdown.ui.EditorActivity;
import com.jiushig.markdown.utils.EditorHandler;

/**
 * Created by zk on 2017/12/11.
 */

public class EditorFragment extends Fragment {

    private RelativeLayout main;

    public EditText editTitle;
    public EditText editText;

    public HorizontalScrollView horizontalScrollView;

    public EditorHandler editorHandler;

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
            main = (RelativeLayout) inflater.inflate(R.layout.fragment_editor, container, false);
            initView(main);
        }
        return main;
    }

    private void initView(RelativeLayout main) {
        editTitle = (EditText) main.findViewById(R.id.title);
        editText = (EditText) main.findViewById(R.id.content);
        horizontalScrollView = (HorizontalScrollView) main.findViewById(R.id.hScroll_layout);

        editorHandler = new EditorHandler(activity, horizontalScrollView, editText);
    }


}
