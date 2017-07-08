package tobeone.waterpic.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
/**
*
* @author aiyuan
*create at 2017/7/9 0:58
*/
public class WaterMarkSettingActivity extends AppCompatActivity {

    private TextView durationTextView;

    private BoomMenuButton bmb1;
    private BoomMenuButton bmb2;
    private BoomMenuButton bmb3;
    private String direction = "";
    private String fonts = "";
    private int background_color_code = R.color.color_black;
    private int font_color_code = R.color.color_white;
    private int direction_code = R.color.color_white;
    private OperationUtils operationUtils;
    private static final String TAG = "WaterMarkSettingActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_set_mark);
        operationUtils = new OperationUtils();

        BoomMenuButtoninit();
        SeekBarinit();
    }


    private  void SeekBarinit(){
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

        durationTextView = (TextView) findViewById(R.id.duration_text);
        durationTextView.setText("Show/Hide duration = " + durationSeekBar.getProgress() + " ms");

    }
    private void BoomMenuButtoninit(){
        bmb1 = (BoomMenuButton) findViewById(R.id.bmb1);
        assert bmb1 != null;
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //// TODO: 2017/7/9
                            fonts = operationUtils.getfonts(index);
                            background_color_code = operationUtils.getFontsCodes(index,0);
                            font_color_code = operationUtils.getFontsCodes(index,1);
                            Toast.makeText(WaterMarkSettingActivity.this, "您选择的背景和字体颜色是"
                                    + fonts, Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.normalImageRes(BuilderManager.getImageResource());
            bmb1.addBuilder(builder);
        }


        bmb2 = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb2.getPiecePlaceEnum().pieceNumber(); i++){
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
        for (int i = 0; i < bmb3.getPiecePlaceEnum().pieceNumber(); i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index == 0){
                                //// TODO: 2017/7/9
                                if(!"".equals(direction) && !"".equals(fonts)){
                                    int[] temp = returnValueCode();
                                    for(int i = 0;i < temp.length;i++){
                                        Log.d(TAG, "" + temp[i]);
                                    }
                                    Toast.makeText(WaterMarkSettingActivity.this,"您保存的当前操作是:" +
                                            "选择将水印放在" + direction + "  背景和字体颜色是" + fonts,Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(WaterMarkSettingActivity.this,"您当前并未进行任何有效操作，请从新选择",Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                direction = "";
                                fonts = "";
                                Toast.makeText(WaterMarkSettingActivity.this,"您取消了当前操作，请从新选择",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            if(i == 0){
                builder.normalImageRes(R.drawable.save);
            }else
                builder.normalImageRes(R.drawable.cancle);

            bmb3.addBuilder(builder);
        }
    }

    public int[] returnValueCode(){
        int[] valueCode = new int[3];
        valueCode[0] = background_color_code;
        valueCode[1] = font_color_code;
        valueCode[2] = direction_code;
        return valueCode;
    }
}
