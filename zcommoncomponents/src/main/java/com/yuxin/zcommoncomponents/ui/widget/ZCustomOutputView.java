package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
 *@date 2018/12/19 9:45
 *@role
 *****/
public class ZCustomOutputView extends LinearLayout {
    private TextView tvTitle, tvContent;
    private ImageView imgIcon;
    private View bottomView;
    private final int DEFULTCOLOR = 0;


    public ZCustomOutputView(Context context) {
        super(context);
        init(context, null);

    }

    public ZCustomOutputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZCustomOutputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZCustomOutputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.lin_item_style_output, this);
        tvTitle = findViewById(R.id.lin_item_style_output_tv_title);
        tvContent = findViewById(R.id.lin_item_style_output_tv_content);
        imgIcon = findViewById(R.id.lin_item_style_output_img_icon);
        bottomView = findViewById(R.id.lin_item_style_output_view_bottom_lin);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZCustomOutputView);

            String mTitle = array.getString(R.styleable.ZCustomOutputView_zTitle);
            if (!ZTextUtil.isEmpty(mTitle)) {
                tvTitle.setText(mTitle);
            }

            String mContent = array.getString(R.styleable.ZCustomOutputView_zContent);
            if (!ZTextUtil.isEmpty(mContent)) {
                tvContent.setText(mContent);
            }

            Drawable mIcon = array.getDrawable(R.styleable.ZCustomOutputView_zIcon);
            if (mIcon != null) {
                imgIcon.setVisibility(VISIBLE);
                imgIcon.setImageDrawable(mIcon);
            } else {
                imgIcon.setVisibility(GONE);
            }

            boolean showBottom = array.getBoolean(R.styleable.ZCustomOutputView_zShowBottomLine, false);
            int bottomColor = array.getColor(R.styleable.ZCustomOutputView_zBottomLineColor, DEFULTCOLOR);
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

            int outputGravity = array.getInt(R.styleable.ZCustomOutputView_zOutputGravity, 0);
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


    public void setIconResource(int resID) {
        imgIcon.setVisibility(VISIBLE);
        imgIcon.setImageResource(resID);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public String getContent() {
        return tvContent.getText().toString();
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setOnClickIconListener(OnClickListener listener) {
        imgIcon.setOnClickListener(listener);
    }
}
