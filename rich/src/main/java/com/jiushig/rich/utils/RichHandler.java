package com.jiushig.rich.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jiushig.rich.R;
import com.jiushig.rich.ui.EditorActivity;


/**
 * Created by zk on 2017/9/9.
 */

public class RichHandler implements View.OnClickListener, View.OnLongClickListener {

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
        view.findViewById(R.id.format_bold).setOnClickListener(this);
        view.findViewById(R.id.format_bold).setOnLongClickListener(this);
        view.findViewById(R.id.format_italic).setOnClickListener(this);
        view.findViewById(R.id.format_italic).setOnLongClickListener(this);
        view.findViewById(R.id.format_header_1).setOnClickListener(this);
        view.findViewById(R.id.format_header_1).setOnLongClickListener(this);
        view.findViewById(R.id.format_header_2).setOnClickListener(this);
        view.findViewById(R.id.format_header_2).setOnLongClickListener(this);
        view.findViewById(R.id.format_header_3).setOnClickListener(this);
        view.findViewById(R.id.format_header_3).setOnLongClickListener(this);
        view.findViewById(R.id.format_header_4).setOnClickListener(this);
        view.findViewById(R.id.format_header_4).setOnLongClickListener(this);
        view.findViewById(R.id.format_header_5).setOnClickListener(this);
        view.findViewById(R.id.format_header_5).setOnLongClickListener(this);
        view.findViewById(R.id.format_quote).setOnClickListener(this);
        view.findViewById(R.id.format_quote).setOnLongClickListener(this);
        view.findViewById(R.id.edit_img).setOnClickListener(this);
        view.findViewById(R.id.edit_img).setOnLongClickListener(this);
        view.findViewById(R.id.link).setOnClickListener(this);
        view.findViewById(R.id.link).setOnLongClickListener(this);
        view.findViewById(R.id.list).setOnClickListener(this);
        view.findViewById(R.id.list).setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_img) {
            if (!Permission.storage(activity)) return;

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activity.startActivityForResult(intent, EditorActivity.REQUEST_CODE_IMG);
        } else if (v.getId() == R.id.format_bold) {
            addFormatBold();
        } else if (v.getId() == R.id.format_italic) {
            addFormatItalic();
        } else if (v.getId() == R.id.format_header_1) {
            addFormatHeader1();
        } else if (v.getId() == R.id.format_header_2) {
            addFormatHeader2();
        } else if (v.getId() == R.id.format_header_3) {
            addFormatHeader3();
        } else if (v.getId() == R.id.format_header_4) {
            addFormatHeader4();
        } else if (v.getId() == R.id.format_header_4) {
            addFormatHeader5();
        } else if (v.getId() == R.id.format_quote) {
            addFormatQuote();
        } else if (v.getId() == R.id.link) {
            addLink();
        } else if (v.getId() == R.id.list) {
            addList();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.edit_img) {
            Toast.makeText(activity, R.string.edit_img, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_bold) {
            Toast.makeText(activity, R.string.format_bold, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_italic) {
            Toast.makeText(activity, R.string.format_italic, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_header_1) {
            Toast.makeText(activity, R.string.format_header_1, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_header_2) {
            Toast.makeText(activity, R.string.format_header_2, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_header_3) {
            Toast.makeText(activity, R.string.format_header_3, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_header_4) {
            Toast.makeText(activity, R.string.format_header_4, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_header_5) {
            Toast.makeText(activity, R.string.format_header_5, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.format_quote) {
            Toast.makeText(activity, R.string.format_quote, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.link) {
            Toast.makeText(activity, R.string.link, Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.list) {
            Toast.makeText(activity, R.string.list, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void addImg(Uri uri) {
        String path = RichUtils.getRealFilePath(activity, uri);
        if (path == null) {
            Toast.makeText(activity, "获取图片失败", Toast.LENGTH_LONG).show();
        } else {
            String string = "\n![](file://" + path + ")";
            editText.getText().insert(editText.getSelectionStart(), string);
        }
    }

    public void addFormatBold() {
        editText.getText().insert(editText.getSelectionStart(), "****");
        editText.setSelection(editText.getSelectionStart() - 2);
    }

    public void addFormatItalic() {
        editText.getText().insert(editText.getSelectionStart(), "**");
        editText.setSelection(editText.getSelectionStart() - 1);
    }

    public void addFormatHeader1() {
        editText.getText().insert(editText.getSelectionStart(), "\n# ");
    }

    public void addFormatHeader2() {
        editText.getText().insert(editText.getSelectionStart(), "\n## ");
    }

    public void addFormatHeader3() {
        editText.getText().insert(editText.getSelectionStart(), "\n### ");
    }

    public void addFormatHeader4() {
        editText.getText().insert(editText.getSelectionStart(), "\n#### ");
    }

    public void addFormatHeader5() {
        editText.getText().insert(editText.getSelectionStart(), "\n##### ");
    }

    public void addFormatQuote() {
        editText.getText().insert(editText.getSelectionStart(), "\n>");
    }

    public void addLink() {
        editText.getText().insert(editText.getSelectionStart(), "[]()");
        editText.setSelection(editText.getSelectionStart() - 3);
    }

    public void addList() {
        editText.getText().insert(editText.getSelectionStart(), "\n-");
    }
}
