package tobeone.waterpic.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import tobeone.waterpic.R;
import tobeone.waterpic.activity.AddCompanyName;
import tobeone.waterpic.activity.AddProjectName;
import tobeone.waterpic.utils.TakePhotoView;

/**
 * Created by 王特 on 2017/7/7.
 */
public class AddWaterPicFragment extends Fragment  {

    public static final int ADD_PROJECT_NAME = 1;
    public static final int ADD_COMPANY_NAME = 2;


    private ImageView imageView;
    private LinearLayout projectNameLayout;
    private LinearLayout CompanyNameLayout;
    private LinearLayout TimeLayout;
    private TextView addProjectNameText;
    private TextView addCompanyNameText;
    private TextView addTimeText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_water_pic, container, false);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        imageView = ((ImageView) view.findViewById(R.id.imageView));
        projectNameLayout = (LinearLayout) view.findViewById(R.id.project_name_layout);
        addProjectNameText = (TextView) view.findViewById(R.id.text_project_name);
        CompanyNameLayout = (LinearLayout) view.findViewById(R.id.compay_name_layout);
        addCompanyNameText = (TextView) view.findViewById(R.id.text_bulid_compay);
        TimeLayout = (LinearLayout) view.findViewById(R.id.time_now_layout);
        addTimeText = (TextView) view.findViewById(R.id.text_now_time);


        imageView.setImageResource(R.drawable.default_pic);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击 图片放大
                Toast.makeText(getActivity(),"点击事件",Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按 图片进入选择菜单


                return true;
            }
        });
        projectNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddProjectName.class);
                startActivityForResult(intent ,ADD_PROJECT_NAME);
            }
        });
        CompanyNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddCompanyName.class);
                startActivityForResult(intent ,ADD_COMPANY_NAME);
            }
        });
        TimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(date);
                addTimeText.setText(s);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1){
            switch (requestCode)
            {
                case ADD_PROJECT_NAME:
                    String backDataProject = data.getStringExtra("data_project");
                    addProjectNameText.setText(backDataProject);
                    break;
                case ADD_COMPANY_NAME:
                    String backDataCompany = data.getStringExtra("data_company");
                    addCompanyNameText.setText(backDataCompany);
                    break;

            }

        }




    }


}
