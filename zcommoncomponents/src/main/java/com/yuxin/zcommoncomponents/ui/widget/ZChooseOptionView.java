package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.model.ZChooseSectionEntity;
import com.yuxin.zcommoncomponents.util.ZBitmapUtil;
import com.yuxin.zcommoncomponents.util.ZDPUtil;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/3 9:46
 *@role 单选多选View
 *****/
public class ZChooseOptionView extends View {
    private List<ZChooseSectionEntity> mDatas;
    private List<RectF> dataCanvas;
    private Paint mPaint;
    private Context mContext = null;
    private Paint fontPaint, titlePaint;
    private final String TAG = getClass().getSimpleName();
    private int itemWidth = 150;
    private int itemHeight = 70;
    private int itemSpacing = 20;
    private boolean isRadio = false;
    private int mWidth, mHegiht;
    private String mTitle;
    private boolean isRequired;


    public ZChooseOptionView(Context context) {
        super(context);
        init(context, null);
    }

    public ZChooseOptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZChooseOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZChooseOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mDatas = new ArrayList<>();
        dataCanvas = new ArrayList<>();
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZChooseOptionView);
            mTitle = array.getString(R.styleable.ZChooseOptionView_zTitle);
            isRequired = array.getBoolean(R.styleable.ZChooseOptionView_zIsRequired, false);
            isRadio = array.getBoolean(R.styleable.ZChooseOptionView_zIsRadio, false);
        }
        initPaint();
    }


    public void setData(List<ZChooseSectionEntity> mList) {
        mDatas.clear();
        mDatas.addAll(mList);
        invalidate();
    }

    // 2.初始化画笔
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#3399FF"));       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px


        int fontsize = ZDPUtil.sp2px(mContext, 14);
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(Color.WHITE);       //设置画笔颜色
        fontPaint.setStrokeWidth(3);
        fontPaint.setTextSize(fontsize);


        int size = ZDPUtil.sp2px(mContext, 16);
        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setColor(Color.parseColor("#333333"));       //设置画笔颜色
        titlePaint.setStrokeWidth(4);
        titlePaint.setTextSize(size);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHegiht = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetricsInt titleMetrics = titlePaint.getFontMetricsInt();
        float y = getHeight() / 2 + (Math.abs(titleMetrics.ascent) - titleMetrics.descent) / 2;
        fontPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mTitle, 68, y, titlePaint);

        if (isRequired) {
            Bitmap nowBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bitian);
            int bitmapSize = ZDPUtil.dip2px(mContext, 7);
            Bitmap bitmap = ZBitmapUtil.changeBitmapSize(nowBitmap, bitmapSize);
            int imgCenterHeight = mHegiht / 2 - bitmap.getHeight() / 2;
            canvas.drawBitmap(bitmap, 20, imgCenterHeight, new Paint());
        }
        dataCanvas.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            int jianju = itemSpacing * (i + 1);
            int right = mWidth - (itemWidth * (i) + jianju);
            int left = right - itemWidth;
            int top = mHegiht / 2 - itemHeight / 2;
            int bottom = top + itemHeight;
            RectF rectF = new RectF(left, top, right, bottom);
            dataCanvas.add(rectF);
            if (!mDatas.get(i).isSelect) {
                mPaint.setColor(Color.parseColor("#D0DFE6"));
                fontPaint.setColor(Color.BLACK);
            } else {
                fontPaint.setColor(Color.WHITE);
                mPaint.setColor(Color.parseColor("#3399FF"));
            }
            canvas.drawRoundRect(rectF, 30, 30, mPaint);
            Paint.FontMetricsInt fontMetrics = fontPaint.getFontMetricsInt();
            int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            fontPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mDatas.get(i).title, rectF.centerX(), baseline, fontPaint);
        }
    }


    private void clearSelect() {
        for (ZChooseSectionEntity entity : mDatas) {
            entity.isSelect = false;
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


    public List<ZChooseSectionEntity> getDatas() {
        return mDatas;
    }

    public List<ZChooseSectionEntity> getSelectDatas() {
        List<ZChooseSectionEntity> mList = new ArrayList<>();
        for (ZChooseSectionEntity base : mDatas) {
            if (base.isSelect) {
                mList.add(base);
            }
        }
        return mList;
    }

    public ZChooseSectionEntity getOneSelect() {
        for (ZChooseSectionEntity base : mDatas) {
            if (base.isSelect) {
                return base;
            }
        }
        return null;
    }
}