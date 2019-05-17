package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.util.ZTextUtil;

/*****
 *@author zzw
 *@date 2018/12/7 17:11
 *@role
 *****/
public class ZCustomInputView extends LinearLayout {
    private final String TAG = getClass().getSimpleName();
    private TextView tvTitile;
    private EditText edContent;
    private ImageView imgIsRequired;
    private View bottomLineView;
    private boolean isRequired = false;
    private boolean showBootmLine = false;
    private int bottomLineColor;
    private final int DEFULTCOLOR = 0;

    public ZCustomInputView(Context context) {
        super(context);
        init(context);
    }

    public ZCustomInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZCustomInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZCustomInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context) {
        init(context, null);

    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.lin_item_style_input, this);
        tvTitile = findViewById(R.id.lin_item_style_input_tv_title);
        edContent = findViewById(R.id.lin_item_style_input_ed_content);
        imgIsRequired = findViewById(R.id.lin_item_style_input_img_isrequired);
        bottomLineView = findViewById(R.id.lin_item_style_input_view_bottom_line);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZCustomInputView);
            String mTitle = array.getString(R.styleable.ZCustomInputView_zTitle);
            String mHint = array.getString(R.styleable.ZCustomInputView_zHint);
            String mContent = array.getString(R.styleable.ZCustomInputView_zContent);
            isRequired = array.getBoolean(R.styleable.ZCustomInputView_zIsRequired, false);
            showBootmLine = array.getBoolean(R.styleable.ZCustomInputView_zShowBottomLine, false);
            bottomLineColor = array.getColor(R.styleable.ZCustomInputView_zBottomLineColor, DEFULTCOLOR);

            if (!ZTextUtil.isEmpty(mTitle)) {
                tvTitile.setText(mTitle);
            }

            if (!ZTextUtil.isEmpty(mHint)) {
                edContent.setHint(mHint);
            }

            if (!ZTextUtil.isEmpty(mContent)) {
                edContent.setText(mContent);
            }

            if (isRequired) {
                imgIsRequired.setVisibility(VISIBLE);
            } else {
                imgIsRequired.setVisibility(GONE);
            }

            int type = array.getInteger(R.styleable.ZCustomInputView_zInputGravity, 0);
            switch (type) {
                case 0:
                    edContent.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case 1:
                    edContent.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case 2:
                    edContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                case 3:
                    edContent.setInputType(InputType.TYPE_CLASS_DATETIME);
                case 4:
                    edContent.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }

            int gravity = array.getInteger(R.styleable.ZCustomInputView_zInputGravity, 1);
            switch (gravity) {
                case 0:
                    edContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    break;
                case 1:
                    edContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    break;
            }

            if (showBootmLine) {
                bottomLineView.setVisibility(VISIBLE);
                if (bottomLineColor != 0) {
                    bottomLineView.setBackgroundColor(bottomLineColor);
                } else {
                    bottomLineView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                }
            } else {
                bottomLineView.setVisibility(GONE);
                if (bottomLineColor != 0) {
                    bottomLineView.setVisibility(VISIBLE);
                    bottomLineView.setBackgroundColor(bottomLineColor);
                }
            }


        }
    }

    public boolean judgeContentIsExist() {
        String str = edContent.getText().toString();
        if (ZTextUtil.isEmpty(str)) {
            return false;
        } else {
            return true;
        }
    }

    public void setTitle(String title) {
        tvTitile.setText(title);
    }

    public void setHint(String hint) {
        edContent.setHint(hint);
    }

    public void setContent(String content) {
        edContent.setText(content);
    }

    public void setRequired(boolean judge) {
        if (judge) {
            isRequired = true;
            imgIsRequired.setVisibility(VISIBLE);
        } else {
            isRequired = false;
            imgIsRequired.setVisibility(GONE);
        }
    }


    public boolean getRequired() {
        return isRequired;
    }

    public String getTitle() {
        String str = tvTitile.getText().toString();
        return str;
    }

    public String getContent() {
        String str = edContent.getText().toString();
        return str;
    }

}
