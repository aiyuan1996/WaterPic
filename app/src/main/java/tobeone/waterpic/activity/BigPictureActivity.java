package tobeone.waterpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import tobeone.waterpic.R;

/**
 * Created by 王特 on 2017/7/8.
 */

public class BigPictureActivity extends BaseActivity implements View.OnClickListener{


    private ImageView imageView;
    private ImageView iv_back;
    private ImageView iv_preview_share;

    private ImageView iv_preview_down;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_bigPicture);
        initView();
    }


    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_preview_share = (ImageView) findViewById(R.id.iv_preview_share);
        iv_preview_share.setOnClickListener(this);
        iv_preview_down = (ImageView) findViewById(R.id.iv_preview_down);
        iv_preview_down.setOnClickListener(this);


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
            case R.id.iv_preview_down:
                //保存水印图片

                break;
        }
    }
}