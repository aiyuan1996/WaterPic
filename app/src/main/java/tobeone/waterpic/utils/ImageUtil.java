package tobeone.waterpic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by 王特 on 2017/7/9.
 */

public class ImageUtil  {
    private static final String TAG = "ImageUtil";

    public static Bitmap createWaterBitmap(Context context,Bitmap src,String projectName,String companyName,String currentTime,int size,int directionCode,
                              int font_color_code,int background_color_code,int paddingLeft, int paddingTop){
        switch (directionCode){
            case 0:
                return drawTextToLeftTop(context,src,projectName,companyName,currentTime,size,font_color_code,paddingLeft,paddingTop,background_color_code);
            case 1:
                return drawTextToRightTop(context,src,projectName,companyName,currentTime,size,font_color_code,paddingLeft,paddingTop,background_color_code);
            case 2:
                return drawTextToCenter(context,src,projectName,companyName,currentTime,size,font_color_code,background_color_code);
            case 3:
                return drawTextToLeftBottom(context,src,projectName,companyName,currentTime,size,font_color_code,paddingLeft,paddingTop,background_color_code);
            case 4:
                return drawTextToRightBottom(context,src,projectName,companyName,currentTime,size,font_color_code,paddingLeft,paddingTop,background_color_code);
        }
        return null;
    }



    /**
    * 
    * @author aiyuan
    *create at 2017/7/9 15:23
    */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String projectName,String companyName,String currentTime,
                                           int size, int color, int paddingLeft, int paddingTop,int background_color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect project_rect = new Rect();
        paint.getTextBounds(projectName, 0, projectName.length(), project_rect);
        // TODO: 2017/7/9  
//        int startx = bounds.centerX() - bounds.width() / 2;
//        int starty = bounds.centerY() - bounds.height() / 2;
//        Rect back_rect = new Rect(startx,starty,startx + bounds.width(),starty + bounds.height());
        return drawTextToBitmap(context, bitmap, text, paint, bounds,back_rect,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height(),background_color,color);
    }

    /**
     * 绘制文字到右下角
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param
     * @param
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String projectName,String companyName,String currentTime,
                                               int size, int color, int paddingRight, int paddingBottom,int background_color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int startx = bounds.centerX() - bounds.width() / 2;
        int starty = bounds.centerY() - bounds.height() / 2;
        Rect back_rect = new Rect(startx,starty,startx + bounds.width(),starty + bounds.height());
        return drawTextToBitmap(context, bitmap, text, paint, bounds,back_rect,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom),background_color,color);
    }

    /**
     * 绘制文字到右上方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String projectName,String companyName,String currentTime,
                                            int size, int color, int paddingRight, int paddingTop,int background_color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int startx = bounds.centerX() - bounds.width() / 2;
        int starty = bounds.centerY() - bounds.height() / 2;
        Rect back_rect = new Rect(startx,starty,startx + bounds.width(),starty + bounds.height());
        return drawTextToBitmap(context, bitmap, text, paint, bounds,back_rect,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                dp2px(context, paddingTop) + bounds.height(),background_color,color);
    }

    /**
     * 绘制文字到左下方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String projectName,String companyName,String currentTime,
                                              int size, int color, int paddingLeft, int paddingBottom,int background_color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int startx = bounds.centerX() - bounds.width() / 2;
        int starty = bounds.centerY() - bounds.height() / 2;
        Rect back_rect = new Rect(startx,starty,startx + bounds.width(),starty + bounds.height());
        return drawTextToBitmap(context, bitmap, text, paint, bounds,back_rect,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom),background_color,color);
    }

    /**
     * 绘制文字到中间
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String projectName,String companyName,String currentTime,
                                          int size, int color,int background_color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Log.d(TAG, bounds.centerX() + "  --->>>" + bounds.centerY());
        Log.d(TAG, bounds.width() + " -->>>" + bounds.height());
        int startx =  (bitmap.getWidth() - bounds.width()) / 2;
        int starty = (bitmap.getHeight() + bounds.height()) / 2;
        Rect back_rect = new Rect(startx,starty - 10,startx + bounds.width(),starty + bounds.height());
        return drawTextToBitmap(context, bitmap, text, paint, bounds,back_rect,
                startx, starty,background_color,color);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds,Rect back_rect, int paddingLeft, int paddingTop,int background_color,int fonts_color) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(background_color);
        canvas.drawRect(back_rect,paint);
        paint.setColor(fonts_color);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        canvas.drawRect(bounds,paint);
        return bitmap;
    }

    /**
     * 缩放图片
     * @param src
     * @param w
     * @param h
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
