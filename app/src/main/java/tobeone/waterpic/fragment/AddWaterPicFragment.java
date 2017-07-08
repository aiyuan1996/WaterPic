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
import android.widget.TextView;
import android.widget.Toast;

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
    private Button addProjectNameBtn,addCompanyNameBtn;
    private TextView addProjectNameText,addCompanyNameText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_water_pic, container, false);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        imageView = ((ImageView) view.findViewById(R.id.imageView));
        addProjectNameBtn = (Button) view.findViewById(R.id.btn_project_name);
        addProjectNameText = (TextView) view.findViewById(R.id.text_project_name);

        addCompanyNameBtn = (Button) view.findViewById(R.id.btn_bulid_compay);
        addCompanyNameText = (TextView) view.findViewById(R.id.text_bulid_compay);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(getResources().getStringArray(R.array.selectArray), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //照片来源---》拍照
                                break;
                            case 1:
                                //照片来源---》图库
                                break;
                        }
                    }
                }).show();

                return true;
            }
        });
        addProjectNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddProjectName.class);
                startActivityForResult(intent ,ADD_PROJECT_NAME);
            }
        });
        addCompanyNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddCompanyName.class);
                startActivityForResult(intent ,ADD_COMPANY_NAME);
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
