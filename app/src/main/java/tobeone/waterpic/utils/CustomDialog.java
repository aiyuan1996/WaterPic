package tobeone.waterpic.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import tobeone.waterpic.R;

/**
* 自定义对话框
* @author aiyuan
*create at 2017/7/8 skin_5:33
*/
public class CustomDialog extends Dialog {
    /**
     * 定义模板
     * @param context
     * @param layout
     * @param style
     */
    public CustomDialog(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }
    /**
     * 定义属性
     * @param context
     * @param width
     * @param height
     * @param layout
     * @param style
     * @param gravity
     * @param anim
     */
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity, int anim){
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }
    /**
     * 实例
     */
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity){
        this(context,width,height,layout,style,gravity, R.style.pop_anim_style);
    }
}
