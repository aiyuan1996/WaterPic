package tobeone.waterpic.activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;



import tobeone.waterpic.R;

/**
 * Created by 王特 on 2017/7/8.
 */

public class BigPictureActivity extends BaseActivity implements View.OnClickListener{


    private ImageView imageView;
    private ImageView iv_back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture);
        initView();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        Intent intent = getIntent();
        if (intent != null){
            Bitmap bitmap =intent.getParcelableExtra("pic_bitmap");
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
