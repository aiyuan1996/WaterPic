package tobeone.waterpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tobeone.waterpic.R;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
import tobeone.waterpic.utils.GlideUtils;

/**
 * Created by 王特 on 2017/7/11.
 */

public class SpecialGridAdapter extends BaseAdapter{



    public Context mContext;
    private LayoutInflater inflater;
    private List<LocalWaterInfo> mList;
    private LocalWaterInfo model;

    public SpecialGridAdapter(Context mContext, List<LocalWaterInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_special_grid_item, null);
            viewHolder.iv_main_grid_icon = (ImageView) view.findViewById(R.id.iv_main_grid_icon);
            viewHolder.tv_main_grid_project = (TextView) view.findViewById(R.id.tv_main_grid_project);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        model = mList.get(i);

        Glide.with(mContext).load(model.getPictureUri()).override(200,200).centerCrop().into(viewHolder.iv_main_grid_icon);
       // GlideUtils.loadImageCrop(mContext, model.getPictureUri(), viewHolder.iv_main_grid_icon);
        viewHolder.tv_main_grid_project.setText( model.toString());
        return view;
    }
    class ViewHolder {
        private ImageView iv_main_grid_icon;
        private TextView tv_main_grid_project;
    }
}
