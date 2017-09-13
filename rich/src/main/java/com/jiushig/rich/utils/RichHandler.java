package com.jiushig.rich.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jiushig.rich.R;
import com.jiushig.rich.ui.EditorActivity;


/**
 * Created by zk on 2017/9/9.
 */

public class RichHandler implements View.OnClickListener,View.OnLongClickListener {

    private Activity activity;

    private EditText editText;
    private View view;

    public RichHandler(Activity activity, View view, EditText editText) {
        this.editText = editText;
        this.view = view;
        this.activity = activity;

        initView();
    }

    private void initView() {
        view.findViewById(R.id.edit_img).setOnClickListener(this);
        view.findViewById(R.id.edit_img).setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_img) {
            activity.startActivityForResult((new Intent("android.intent.action.GET_CONTENT")).setType("image/*"), EditorActivity.REQUEST_CODE_IMG);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.edit_img) {
            Toast.makeText(activity,R.string.edit_img,Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
