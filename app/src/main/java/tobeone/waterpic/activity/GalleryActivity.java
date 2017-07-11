package tobeone.waterpic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tobeone.waterpic.R;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
import tobeone.waterpic.utils.GlideUtils;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private ImageView iv_back;
    private ImageView iv_preview_share;

    private TextView projectNameText;

    private LocalWaterInfo localWaterInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initView();


    }
    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_preview_share = (ImageView) findViewById(R.id.iv_preview_share);
        iv_preview_share.setOnClickListener(this);
        projectNameText = (TextView) findViewById(R.id.projectNameText);

        //imageView.setImageResource(R.drawable.default_pic);

        Intent intent = getIntent();
        if (intent != null){
            localWaterInfo = (LocalWaterInfo) intent.getSerializableExtra("water_info");
        }
        Glide.with(this).load(localWaterInfo.getPictureUri()).into(imageView);
        projectNameText.setText(localWaterInfo.getProjectName());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_preview_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
