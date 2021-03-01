package com.javaclimb.music.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传者
 */
//Serializable实现前后端的传输
public class Uploader implements Serializable {
    //    主键
    private Integer id;
    //    账号
    private String name;
    //    学号
    private String studyNumber;
    //    性别
    private Byte gender;
    //    学院
    private String academy;
    //    头像
    private String pic;
    //专业
    private String major;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(String studyNumber) {
        this.studyNumber = studyNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }


}
