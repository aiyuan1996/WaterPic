package tobeone.waterpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import tobeone.waterpic.R;

public class AddWaterPicture extends AppCompatActivity {


    @BindView(R.id.imageView)
    AppCompatImageView imageView;
    @BindView(R.id.btn_project_name)
    Button btnProjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_picture);
        ButterKnife.bind(this);
        btnProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWaterPicture.this,AddProjectName.class);
                startActivity(intent);
            }
        });
    }


}
