package tobeone.waterpic.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import tobeone.waterpic.R;
import tobeone.waterpic.utils.BuilderManager;
import tobeone.waterpic.utils.OperationUtils;

import static tobeone.waterpic.R.id.fonts_seek;


/**
*
* @author aiyuan
*create at 2017/7/9 0:58
*/
public class WaterMarkSettingActivity extends AppCompatActivity {

    private TextView durationTextView;
    private TextView fontSizeTextView;

    private BoomMenuButton bmb1;
    private BoomMenuButton bmb2;
    private BoomMenuButton bmb3;
    private String direction = "";
    private String fonts = "";


    private int fonts_size = 16;

    private int background_color_code = R.color.color_black;
    private int font_color_code = R.color.color_white;
    private int direction_code = 4;
    private OperationUtils operationUtils;
    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final String TAG = "WaterMarkSettingActivit";

    private Bitmap srcBitmap;

    private Bitmap waterBitmap;

    private String watermarkInformation;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_set_mark);
        operationUtils = new OperationUtils();

        BoomMenuButtoninit();
        SeekBarinit();
        Intent intent = getIntent();
        if (intent != null) {
            watermarkInformation = intent.getStringExtra("water_info");
            srcBitmap = intent.getParcelableExtra("src_bitmap");
            location = intent.getStringExtra("location");

        }
        getSupportActionBar().setTitle("水印设置");

        FontSizeinit();
    }

    private void FontSizeinit() {

        SeekBar fontSizeSeekbar = (SeekBar) findViewById(fonts_seek);
        fontSizeSeekbar.setMax(20);
        fontSizeSeekbar.setProgress(16);
        fontSizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSizeTextView.setText("当前字体大小为：" + seekBar.getProgress() );
                fonts_size = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        fontSizeTextView = (TextView) findViewById(R.id.fonts_text);
        fontSizeTextView.setText("当前字体大小为：" + fontSizeSeekbar.getProgress() );


    }


    private void SeekBarinit() {
        Switch threeDAnimationSwitch = (Switch) findViewById(R.id.three_d_animation_switch);
        threeDAnimationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bmb1.setUse3DTransformAnimation(isChecked);
                bmb2.setUse3DTransformAnimation(isChecked);
                bmb3.setUse3DTransformAnimation(isChecked);
            }
        });
        threeDAnimationSwitch.setChecked(true);

        SeekBar durationSeekBar = (SeekBar) findViewById(R.id.duration_seek);
        durationSeekBar.setMax(3000);
        durationSeekBar.setProgress(300);
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                durationTextView.setText("Show/Hide duration = " + seekBar.getProgress() + " ms");
                bmb1.setDuration(progress);
                bmb2.setDuration(progress);
                bmb3.setDuration(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        durationTextView = (TextView) findViewById(R.id.fonts_text);
        durationTextView.setText("duration = " + durationSeekBar.getProgress() + " ms");
    }





    private void BoomMenuButtoninit() {
        bmb1 = (BoomMenuButton) findViewById(R.id.bmb1);
        assert bmb1 != null;
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //// TODO: 2017/7/9
                            fonts = operationUtils.getfonts(index);
                            background_color_code = operationUtils.getFontsCodes(index, 0);
                            font_color_code = operationUtils.getFontsCodes(index, 1);
                            Toast.makeText(WaterMarkSettingActivity.this, "您选择的背景和字体颜色是"
                                    + fonts, Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.normalImageRes(BuilderManager.getImageResource());
            bmb1.addBuilder(builder);
        }


        bmb2 = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb2.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //// TODO: 2017/7/9
                            direction = operationUtils.getDirections(index);
                            direction_code = index;
                            Toast.makeText(WaterMarkSettingActivity.this, "您选择将水印放在" + direction, Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.normalImageRes(BuilderManager.getdirections());
            bmb2.addBuilder(builder);
        }

        bmb3 = (BoomMenuButton) findViewById(R.id.bmb3);
        for (int i = 0; i < bmb3.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if (index == 0) {
                                //// TODO: 2017/7/9
                                if (!"".equals(fonts)) {
                                    int[] temp = returnValueCode();
                                    for (int i = 0; i < temp.length; i++) {
                                        Log.d(TAG, "" + temp[i]);
                                    }
                                    saveBtn();
//                                    Toast.makeText(WaterMarkSettingActivity.this, "您保存的当前操作是:" +
//                                            "选择将水印放在" + direction + "  背景和字体颜色是" + fonts, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(WaterMarkSettingActivity.this, "您当前并未进行任何有效操作，请从新选择", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                direction = "";
                                fonts = "";
                                Toast.makeText(WaterMarkSettingActivity.this, "您取消了当前操作，请从新选择", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            if (i == 0) {
                builder.normalImageRes(R.drawable.save);
            } else
                builder.normalImageRes(R.drawable.cancle);

            bmb3.addBuilder(builder);
        }
    }

    private void saveBtn() {
        switch (direction_code){
            case 0:
                waterBitmap = createWatermarkLeftTop(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;
            case 1:
                waterBitmap = createWatermarkRightTop(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;
            case 2:
                waterBitmap = createWatermarkCenter(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;
            case 3:
                waterBitmap = createWatermarkLeftBottom(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;
            case 4:
                waterBitmap = createWatermarkRightBottom(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;
            default:
                waterBitmap = createWatermarkLeftBottom(srcBitmap,watermarkInformation,fonts_size,font_color_code,background_color_code);
                break;

        }

        Intent intent = new Intent();
        intent.putExtra("r_pic_bitmap", waterBitmap);
        setResult(RESULT_CANCELED,intent);
        finish();
    }


    /**
     * 在左上角绘制水印
     * @param bitmap
     * @param markText
     * @param size
     * @param font_color_code
     * @param background_color_code
     * @return
     */
    private Bitmap createWatermarkLeftTop(Bitmap bitmap, String markText,int size,int font_color_code,int background_color_code) {

        // 获取图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 创建一个和图片一样大的背景图
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        // 画背景图
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(background_color_code);
        RectF rect = new RectF(0, 0, 160, 60);
        canvas.drawRect(rect, paint);


        //-------------开始绘制文字-------------------------------

        if (!TextUtils.isEmpty(markText)) {
            int screenWidth = getScreenWidth();
            float textSize = dp2px(this, size) * bitmapWidth / screenWidth;
            // 创建画笔
            TextPaint mPaint = new TextPaint();
            // 文字矩阵区域
            Rect textBounds = new Rect();

            // 水印的字体大小
            mPaint.setTextSize(textSize);
            // 文字阴影
            //mPaint.setShadowLayer(0.5f, 0f, 1f, Color.YELLOW);
            // 抗锯齿
            mPaint.setAntiAlias(true);
            // 水印的区域
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);

            // 水印的颜色
            mPaint.setColor(font_color_code);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            // 文字开始的坐标
            float textX = dp2px(this, 8) * bitmapWidth / screenWidth;
            float textY = bitmapHeight / 2;//图片的中间
            // 画文字
            //canvas.translate(textX, textY);
            canvas.translate(0,0);
            layout.draw(canvas);

        }
        //保存所有元素
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }
//    private void saveToServer(){
//        final WaterInfoEntity waterInfoEntity = new WaterInfoEntity();
//        waterInfoEntity.setProjectName(projectName);
//        waterInfoEntity.setCompanyName(companyName);
//        waterInfoEntity.setCurrentTime(currentTime);
//        waterInfoEntity.setLocation(location);
//        ImageUtil imageUtil = new ImageUtil(PHOTO_IMAGE_FILE_NAME);
//        final BmobFile bmobFile = new BmobFile(imageUtil.bitmapToFile(waterBitmap));
//        //waterInfoEntity.setPicture(bmobFile);
//        bmobFile.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e == null){
//                    waterInfoEntity.setPicture(bmobFile);
//                    waterInfoEntity.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String s, BmobException e) {
//                            if(e==null){
//                                ToastUtils.showShort(WaterMarkSettingActivity.this,"添加数据成功，返回objectId为："+s);
//                            }else{
//                                ToastUtils.showShort(WaterMarkSettingActivity.this,"创建数据失败：" + e.getMessage());
//                                Log.d(TAG, "---->>>" + e.getMessage());
//                            }
//                        }
//                    });
//                }
//            }
//        });
//
//    }

    /**
     * 在左上角绘制水印
     * @param bitmap
     * @param markText
     * @param size
     * @param font_color_code
     * @param background_color_code
     * @return
     */
    private Bitmap createWatermarkCenter(Bitmap bitmap, String markText,int size,int font_color_code,int background_color_code) {

        // 获取图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 创建一个和图片一样大的背景图
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        // 画背景图
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bitmap, 0, 0, null);
        int screenWidth = getScreenWidth();

        float textX = bitmapWidth / 2;
        float textY = bitmapHeight / 2;//图片的中间

        Paint paint = new Paint();
        paint.setColor(background_color_code);
        RectF rect = new RectF(textX-80, textY-30, textX+80, textY+30);
        canvas.drawRect(rect, paint);


        //-------------开始绘制文字-------------------------------

        if (!TextUtils.isEmpty(markText)) {

            float textSize = dp2px(this, size) * bitmapWidth / screenWidth;
            // 创建画笔
            TextPaint mPaint = new TextPaint();
            // 文字矩阵区域
            Rect textBounds = new Rect();

            // 水印的字体大小
            mPaint.setTextSize(textSize);
            // 文字阴影
            //mPaint.setShadowLayer(0.5f, 0f, 1f, Color.YELLOW);
            // 抗锯齿
            mPaint.setAntiAlias(true);
            // 水印的区域
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);

            // 水印的颜色
            mPaint.setColor(font_color_code);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            // 文字开始的坐标

            // 画文字
            //canvas.translate(textX, textY);
            canvas.translate(textX-80,textY-30);
            layout.draw(canvas);

        }
        //保存所有元素
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }



    /**
     * 在右上角绘制水印
     * @param bitmap
     * @param markText
     * @param size
     * @param font_color_code
     * @param background_color_code
     * @return
     */
    private Bitmap createWatermarkRightTop(Bitmap bitmap, String markText,int size,int font_color_code,int background_color_code) {

        // 获取图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 创建一个和图片一样大的背景图
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        // 画背景图
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(background_color_code);
        RectF rect = new RectF(bitmapWidth-160, 0, bitmapWidth, 60);
        canvas.drawRect(rect, paint);


        //-------------开始绘制文字-------------------------------

        if (!TextUtils.isEmpty(markText)) {
            int screenWidth = getScreenWidth();
            float textSize = dp2px(this, size) * bitmapWidth / screenWidth;
            // 创建画笔
            TextPaint mPaint = new TextPaint();
            // 文字矩阵区域
            Rect textBounds = new Rect();

            // 水印的字体大小
            mPaint.setTextSize(textSize);
            // 文字阴影
            //mPaint.setShadowLayer(0.5f, 0f, 1f, Color.YELLOW);
            // 抗锯齿
            mPaint.setAntiAlias(true);
            // 水印的区域
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);

            // 水印的颜色
            mPaint.setColor(font_color_code);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            // 文字开始的坐标
            float textX = dp2px(this, 8) * bitmapWidth / screenWidth;
            float textY = bitmapHeight / 2;//图片的中间
            // 画文字
            //canvas.translate(textX, textY);
            canvas.translate(bitmapWidth-160,0);
            layout.draw(canvas);

        }
        //保存所有元素
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }

    /**
     * 在左下角绘制水印
     * @param bitmap
     * @param markText
     * @param size
     * @param font_color_code
     * @param background_color_code
     * @return
     */
    private Bitmap createWatermarkLeftBottom(Bitmap bitmap, String markText,int size,int font_color_code,int background_color_code) {

        // 获取图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 创建一个和图片一样大的背景图
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        // 画背景图
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(background_color_code);
        RectF rect = new RectF(0, bitmapHeight-60, 160, bitmapHeight);
        canvas.drawRect(rect, paint);


        //-------------开始绘制文字-------------------------------

        if (!TextUtils.isEmpty(markText)) {
            int screenWidth = getScreenWidth();
            float textSize = dp2px(this, size) * bitmapWidth / screenWidth;
            // 创建画笔
            TextPaint mPaint = new TextPaint();
            // 文字矩阵区域
            Rect textBounds = new Rect();

            // 水印的字体大小
            mPaint.setTextSize(textSize);
            // 文字阴影
            //mPaint.setShadowLayer(0.5f, 0f, 1f, Color.YELLOW);
            // 抗锯齿
            mPaint.setAntiAlias(true);
            // 水印的区域
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);

            // 水印的颜色
            mPaint.setColor(font_color_code);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            // 文字开始的坐标
            float textX = dp2px(this, 8) * bitmapWidth / screenWidth;
            float textY = bitmapHeight / 2;//图片的中间
            // 画文字
            //canvas.translate(textX, textY);
            canvas.translate(0,bitmapHeight-60);
            layout.draw(canvas);

        }
        //保存所有元素
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }
    /**
     * 在右下角绘制水印
     * @param bitmap
     * @param markText
     * @param size
     * @param font_color_code
     * @param background_color_code
     * @return
     */
    private Bitmap createWatermarkRightBottom(Bitmap bitmap, String markText,int size,int font_color_code,int background_color_code) {

        // 获取图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // 创建一个和图片一样大的背景图
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        // 画背景图
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(background_color_code);
        RectF rect = new RectF(bitmapWidth-160, bitmapHeight-60, bitmapWidth-160, bitmapHeight-60);
        canvas.drawRect(rect, paint);


        //-------------开始绘制文字-------------------------------

        if (!TextUtils.isEmpty(markText)) {
            int screenWidth = getScreenWidth();
            float textSize = dp2px(this, size) * bitmapWidth / screenWidth;
            // 创建画笔
            TextPaint mPaint = new TextPaint();
            // 文字矩阵区域
            Rect textBounds = new Rect();

            // 水印的字体大小
            mPaint.setTextSize(textSize);
            // 文字阴影
            //mPaint.setShadowLayer(0.5f, 0f, 1f, Color.YELLOW);
            // 抗锯齿
            mPaint.setAntiAlias(true);
            // 水印的区域
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);

            // 水印的颜色
            mPaint.setColor(font_color_code);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            // 文字开始的坐标
            float textX = dp2px(this, 8) * bitmapWidth / screenWidth;
            float textY = bitmapHeight / 2;//图片的中间
            // 画文字
            //canvas.translate(textX, textY);
            canvas.translate(bitmapWidth-160,bitmapHeight-60);
            layout.draw(canvas);

        }
        //保存所有元素
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bmp;
    }




    private int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }



    public int[] returnValueCode(){
        int[] valueCode = new int[3];
        valueCode[0] = background_color_code;
        valueCode[1] = font_color_code;
        valueCode[2] = direction_code;
        return valueCode;
    }
}
