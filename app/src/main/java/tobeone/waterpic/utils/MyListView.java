package tobeone.waterpic.utils;

import android.content.Context;
import android.widget.ListView;
import android.util.AttributeSet;

/**
 * Created by 王特 on 2017/7/7.
 */

public class MyListView extends ListView{
    public MyListView(Context context) {
        super(context);
    }
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }



}
