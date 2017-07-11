package tobeone.waterpic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import tobeone.waterpic.R;

public class NewActivity extends AppCompatActivity {


    private  ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bitmap bitmap= intent.getParcelableExtra("water_bitmap");
        imageView.setImageBitmap(bitmap);

    }

}
