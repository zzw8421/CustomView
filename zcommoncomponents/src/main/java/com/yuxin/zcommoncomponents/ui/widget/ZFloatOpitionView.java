package com.yuxin.zcommoncomponents.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.model.ZChooseSectionEntity;
import com.yuxin.zcommoncomponents.util.ZDPUtil;
import com.yuxin.zcommoncomponents.util.ZTextUtil;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/3/6 15:05
 *@role
 *****/
public class ZFloatOpitionView extends View {
    private Paint itemBgPaint, fontPaint, headPaint;
    private int normalBgColor, normalTextColor;
    private int selectBgColor, selectTextColor;
    private Context mContext;
    private List<ZChooseSectionEntity> mDatas;
    private List<RectF> dataCanvas;
    private int mWidth, mHgight;
    private int itemWidthSpace = 30, itemHeightSpace = 20;
    private int rowNumber = 2;
    private int itemHeight, itemWidth;
    private int itemRaduis;
    private String mTitle;
    private boolean isRadio = true;
    private int fontSize = 15;
    private final String TAG = getClass().getSimpleName();


    public ZFloatOpitionView(Context context) {
        super(context);
        init(context);
    }

    public ZFloatOpitionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZFloatOpitionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZFloatOpitionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    @SuppressLint({"CustomViewStyleable", "Recycle"})
    private void init(Context context, AttributeSet attrs) {
        init(context);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZFloatOpitionView);
            mTitle = array.getString(R.styleable.ZFloatOpitionView_zTitle);
            isRadio = array.getBoolean(R.styleable.ZFloatOpitionView_zIsRadio, true);
            itemHeight = (int) array.getDimension(R.styleable.ZFloatOpitionView_ZItemHeight, ZDPUtil.dip2px(mContext, 15));
            itemRaduis = array.getInt(R.styleable.ZFloatOpitionView_zItemRaduis, 15);
            normalBgColor = array.getColor(R.styleable.ZFloatOpitionView_zNormalBgColor, 0x111);
            normalTextColor = array.getColor(R.styleable.ZFloatOpitionView_zNormalTextColor, 0x111);
            selectBgColor = array.getColor(R.styleable.ZFloatOpitionView_zSelectBgColor, 0x111);
            selectTextColor = array.getColor(R.styleable.ZFloatOpitionView_zSelectTextColor, 0x111);
            fontSize = array.getDimensionPixelSize(R.styleable.ZFloatOpitionView_ZTextSize, 15);
            if (ZTextUtil.isEmpty(mTitle)) {
                mTitle = "类型";
            }
        }
    }

    private void init(Context context) {
        this.mContext = context;
        dataCanvas = new ArrayList<>();
        initPaint();
    }

    private void initPaint() {
        itemBgPaint = new Paint();
        itemBgPaint.setStyle(Paint.Style.FILL);
        itemBgPaint.setStrokeWidth(10);

        fontPaint = new Paint();
        fontPaint.setStyle(Paint.Style.FILL);
        fontPaint.setTextSize(ZDPUtil.dip2px(mContext, fontSize));

        headPaint = new Paint();
        headPaint.setStyle(Paint.Style.FILL);
        headPaint.setStrokeWidth(10);
        headPaint.setColor(Color.BLACK);
        headPaint.setTextSize(ZDPUtil.dip2px(mContext, fontSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHgight = h;
        itemWidth = (w - ((rowNumber + 1) * itemWidthSpace)) / rowNumber;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setDatas(List<ZChooseSectionEntity> list) {
        mDatas = list;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        itemBgPaint.setColor(selectTextColor);
        canvas.drawCircle(30, 30, 12, itemBgPaint);
        Paint.FontMetrics titleMetrics = headPaint.getFontMetrics();
        float fontHeight = titleMetrics.descent - titleMetrics.ascent;
        canvas.drawText(mTitle, 50, 30 + fontHeight / 3, headPaint);
        int tmpWidth = 0, tmpHegiht = (int) fontHeight * 2;
        dataCanvas.clear();
        for (int i = 1; i <= mDatas.size(); i++) {
            int tmpRight, tmpBottom;
            if (i % (rowNumber + 1) == 0) {
                tmpHegiht += itemHeight + itemHeightSpace;
                tmpWidth = 0;
            }
            tmpWidth += itemWidthSpace;
            tmpRight = tmpWidth + itemWidth;
            tmpBottom = tmpHegiht + itemHeight;
            RectF rectF = new RectF(tmpWidth, tmpHegiht, tmpRight, tmpBottom);
            dataCanvas.add(rectF);
            if (mDatas.get(i - 1).isSelect) {
//                Color.parseColor("#DFF4EF")
                itemBgPaint.setColor(selectBgColor);
//                Color.parseColor("#4BC195")
                fontPaint.setColor(selectTextColor);
            } else {
//                Color.parseColor("#F4F4F4")
                itemBgPaint.setColor(normalBgColor);
//                Color.parseColor("#565656")
                fontPaint.setColor(normalTextColor);
            }
            canvas.drawRoundRect(rectF, itemRaduis, itemRaduis, itemBgPaint);
            Paint.FontMetricsInt fontMetrics = fontPaint.getFontMetricsInt();
            int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            fontPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mDatas.get(i - 1).title, rectF.centerX(), baseline, fontPaint);
            tmpWidth += itemWidth;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < dataCanvas.size(); i++) {
                    RectF rectF = dataCanvas.get(i);
                    if (rectF.contains(x, y)) {
                        if (isRadio) {
                            clearSelect();
                        }
                        mDatas.get(i).changeIsSelect();
                        invalidate();
                        return true;
                    }
                }
                break;
        }
        return true;
    }

    private void clearSelect() {
        for (ZChooseSectionEntity entity : mDatas) {
            entity.isSelect = false;
        }
    }

}
