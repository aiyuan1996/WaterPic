package tobeone.waterpic.entity;

import java.io.Serializable;

/**
 * Created by 王特 on 2017/7/9.
 */

public class WatermarkInformationEntity implements Serializable {

    private String ProjectName;
    private String ConpanyName;
    private String NowTime;
    private String location;

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public void setConpanyName(String conpanyName) {
        ConpanyName = conpanyName;
    }

    public void setNowTime(String nowTime) {
        NowTime = nowTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProjectName() {
        return "工程名称：" + ProjectName;
    }

    public String getConpanyName() {
        return "施工单位：" + ConpanyName;
    }

    public String getNowTime() {
        return "当前日期：" + NowTime;
    }
    public String getLocation() {
        return "当前位置："+location;
    }
}
