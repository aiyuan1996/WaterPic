package tobeone.waterpic.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tobeone.waterpic.R;
import tobeone.waterpic.activity.AddProjectName;
import tobeone.waterpic.activity.FindPasswordActivity_ViewBinding;

/**
 * Created by 王特 on 2017/7/7.
 */
public class AddWaterPicFragment extends Fragment {

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
                Intent intent = new Intent(getActivity(),AddProjectName.class);
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
