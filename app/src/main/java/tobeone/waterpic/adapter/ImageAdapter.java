package tobeone.waterpic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tobeone.waterpic.R;
import tobeone.waterpic.entity.WaterInfoEntity;
import tobeone.waterpic.utils.GlideUtils;


public class ImageAdapter extends BaseAdapter{
    //上下文对象，提供给外界实例化ImageAdapter
    private Context mContext;

    private List<WaterInfoEntity>  mlist = new ArrayList<WaterInfoEntity>();

    public ImageAdapter(Context mContext, List<WaterInfoEntity> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    //获得图片的个数
    @Override
    public int getCount() {
        return mlist.size();
    }
    //获取对应索引上的图片
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mlist.get(position);
    }
    //获取对应图片的索引id
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //获取适配器中指定位置的视图对象
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ImageView iv = new ImageView(mContext);
        GlideUtils.loadImageView(mContext,mlist.get(position).getPicture().getFileUrl(),iv);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setLayoutParams(new Gallery.LayoutParams(300, 300));
        return iv;
    }

}
