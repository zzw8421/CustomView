package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.util.ZTextUtil;


/*****
 *@author zzw
 *@date 2018/12/10 9:41
 *@role 选择内容
 *****/
public class ZCustomChooseView extends LinearLayout {
    private ImageView imgDel, imgChoose;
    private TextView tvTitle, tvContent;
    private ImageView imgIsRequired;
    private String mHint = "请选择";
    private boolean isRequired;
    private Object selectObject;
    private View bottomView;
    private final int DEFULTCOLOR = 0;

    public ZCustomChooseView(Context context) {
        super(context);
        init(context, null);
    }

    public ZCustomChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZCustomChooseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZCustomChooseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.lin_item_style_choose, this);
        tvTitle = findViewById(R.id.lin_item_style_choose_tv_title);
        tvContent = findViewById(R.id.lin_item_style_choose_tv_content);
        imgDel = findViewById(R.id.lin_item_style_choose_img_del);
        imgChoose = findViewById(R.id.lin_item_style_choose_img_choose);
        imgIsRequired = findViewById(R.id.lin_item_style_choose_img_isrequired);
        bottomView = findViewById(R.id.lin_item_style_choose_view_bottom_line);
        imgChoose.setVisibility(VISIBLE);
        setImgDelOnClickListener(null);
        imgDel.setVisibility(GONE);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZCustomChooseView);
            String mTitle = array.getString(R.styleable.ZCustomChooseView_zTitle);
            mHint = array.getString(R.styleable.ZCustomChooseView_zHint);
            isRequired = array.getBoolean(R.styleable.ZCustomChooseView_zIsRequired, false);

            if (!ZTextUtil.isEmpty(mTitle)) {
                tvTitle.setText(mTitle);
            }
            if (!ZTextUtil.isEmpty(mHint)) {
                tvContent.setHint(mHint);
            }
            if (isRequired) {
                imgIsRequired.setVisibility(VISIBLE);
            } else {
                imgIsRequired.setVisibility(GONE);
            }

            boolean showBottom = array.getBoolean(R.styleable.ZCustomChooseView_zShowBottomLine, false);
            int bottomColor = array.getColor(R.styleable.ZCustomChooseView_zBottomLineColor, DEFULTCOLOR);
            if (showBottom) {
                bottomView.setVisibility(VISIBLE);
                if (bottomColor == 0) {
                    bottomView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                } else {
                    bottomView.setBackgroundColor(bottomColor);
                }
            } else {
                bottomView.setVisibility(GONE);
                if (bottomColor != 0) {
                    bottomView.setVisibility(VISIBLE);
                    bottomView.setBackgroundColor(bottomColor);
                }
            }

            int outputGravity = array.getInt(R.styleable.ZCustomOutputView_zOutputGravity, 1);
            switch (outputGravity) {
                case 0:
                    tvContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    break;
                case 1:
                    tvContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    break;
            }

        }
    }


    public void setContent(String content) {
        tvContent.setText(content);
        imgChoose.setVisibility(GONE);
        imgDel.setVisibility(VISIBLE);
    }

    public void clearSelect() {
        tvContent.setText("");
        tvContent.setHint(mHint);
        imgChoose.setVisibility(VISIBLE);
        imgDel.setVisibility(GONE);
        selectObject = null;
    }

    public void setSelect(Object selectObject) {
        this.selectObject = selectObject;
    }


    public Object getSelect() {
        return this.selectObject;
    }

    public boolean judgeContentIsExist() {
        String str = tvContent.getText().toString();
        if (ZTextUtil.isEmpty(str) || selectObject == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setHint(String hint) {
        tvContent.setHint(hint);
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
        String str = tvTitle.getText().toString();
        return str;
    }

    public String getContent() {
        String str = tvContent.getText().toString();
        return str;
    }


    public void setImgDelOnClickListener(OnClickListener listener) {
        imgDel.setOnClickListener(listener);
    }

}
