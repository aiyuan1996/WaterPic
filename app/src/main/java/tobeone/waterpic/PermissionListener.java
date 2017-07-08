package tobeone.waterpic;

import java.util.List;

/**
 * Created by 王特 on 2017/7/8.
 */

public interface PermissionListener  {
    void onGranted();
    void onDenied(List<String> DeniedPermissionList);
}
