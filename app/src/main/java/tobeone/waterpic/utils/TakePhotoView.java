package tobeone.waterpic.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 王特 on 2017/7/8.
 */

public class TakePhotoView  extends android.support.v7.widget.AppCompatImageView{
    public TakePhotoView(Context context) {
        super(context);
    }


    //是否释放了
    private boolean isReleased;
    //计数器，防止多次点击导致最后一次形成longpress的时间变短
    private int mCounter;
    //长按的runnable
    private Runnable mLongPressRunnable;
    //定义相应时间 以此为依据判断是长按还是短按 默认为300毫秒
    private int minTime = 300;

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public OnTakePhotoClickListener getShortClickListener() {
        return shortClickListener;
    }

    public void setShortClickListener(OnTakePhotoClickListener shortClickListener) {
        this.shortClickListener = shortClickListener;
    }

    private OnTakePhotoClickListener shortClickListener;


    private OnLongTakePhotoClickListener longClickListener;

    public OnLongTakePhotoClickListener getLongClickListener() {
        return longClickListener;
    }

    public void setLongClickListener(OnLongTakePhotoClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    public TakePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TakePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                mCounter--;
                //计数器大于0，说明当前执行的Runnable不是最后一次down产生的 判断为短按。
                if(mCounter>0 || isReleased) {
                    if (shortClickListener != null) {
                        shortClickListener.onClick(getRootView());
                    }
                    return;
                }
                //否则判断为长按操作
                if(longClickListener!=null){
                    longClickListener.onLongClick(getRootView());
                }
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TakePhotoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCounter++;
                isReleased = false;
                postDelayed(mLongPressRunnable, minTime);
                break;

            case MotionEvent.ACTION_UP:
                //释放了
                isReleased = true;
                break;
        }
        return true;
    }

    /**
     * 为长按拍照设置一个监听事件
     */
    public interface OnLongTakePhotoClickListener {
        void onLongClick(View view);
    }

    /**
     * 为短按拍照设置一个监听事件
     */
    public interface OnTakePhotoClickListener {
        void onClick(View view);
    }
}
