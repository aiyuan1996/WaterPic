package tobeone.waterpic.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by aiyuan on 2017/7/9.
 */

public class WaterInfoEntity extends BmobObject{

    private String projectName;
    private String companyName;
    private String currentTime;
    private String location;
    private String username;
    private BmobFile picture;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }



    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public BmobFile getPicture() {

        return picture;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProjectName() {

        return projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getLocation() {
        return location;
    }
}
