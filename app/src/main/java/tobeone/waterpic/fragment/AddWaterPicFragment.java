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

import tobeone.waterpic.R;
import tobeone.waterpic.activity.AddProjectName;


public class AddWaterPicFragment extends Fragment {


    private ImageView imageView;
    private Button addProjectNameBtn;


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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addProjectNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = Intent(getActivity(),AddProjectName.class);
                //startActivity(intent);
            }
        });

    }
}
