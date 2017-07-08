package tobeone.waterpic.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import tobeone.waterpic.R;

public class WaterMarkSettingActivity extends AppCompatActivity {

    private TextView durationTextView;

    private BoomMenuButton bmb1;
    private BoomMenuButton bmb2;
    private BoomMenuButton bmb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_set_mark);

        bmb1 = (BoomMenuButton) findViewById(R.id.bmb1);
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(WaterMarkSettingActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            bmb1.addBuilder(builder);
        }


        bmb2 = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb2.getPiecePlaceEnum().pieceNumber(); i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(WaterMarkSettingActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            bmb2.addBuilder(builder);
        }

       bmb3 = (BoomMenuButton) findViewById(R.id.bmb3);
        for (int i = 0; i < bmb3.getPiecePlaceEnum().pieceNumber(); i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(WaterMarkSettingActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            bmb3.addBuilder(builder);
        }


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
}
