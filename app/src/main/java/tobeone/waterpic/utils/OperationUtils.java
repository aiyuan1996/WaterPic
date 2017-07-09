package tobeone.waterpic.utils;

import tobeone.waterpic.R;

/**
 * Created by aiyuan on 2017/7/9.
 */

public class OperationUtils {
    private static String[] directions = new String[]{"左上方","右上方","居中方向",
            "左下方","右下方"};
    private static String[] fonts = new String[]{"白底黑字","蓝底黑字","白底蓝字","黑底白字",
            "黑底黄字","黄底白字","黄底蓝字","红底蓝字","红底黑字"};
    private static int[][] fonts_codes =
            {{R.color.color_white,R.color.color_black},
            {R.color.colorPrimaryDark,R.color.color_black},
            {R.color.color_white,R.color.colorPrimaryDark},
            {R.color.color_black,R.color.color_white},
            {R.color.color_black,R.color.color_yellow},
            {R.color.color_yellow,R.color.color_white},
            {R.color.color_yellow,R.color.colorPrimaryDark},
            {R.color.color_red,R.color.colorPrimaryDark},
            {R.color.color_red,R.color.color_black}};
    public String getDirections(int i){
        return directions[i];
    }
    public String getfonts(int i){
        return fonts[i];
    }
    public int getFontsCodes(int i,int j){
        return fonts_codes[i][j];
    }

}
