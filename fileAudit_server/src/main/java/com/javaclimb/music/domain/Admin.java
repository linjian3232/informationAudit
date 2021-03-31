package com.javaclimb.music.domain;

import java.io.Serializable;

//Serializable实现前后端的传输
public class Admin implements Serializable {
//    主键
    private Integer id;
//    账号
    private String name;
//    密码
    private String password;
    //    级别
    private Integer privilegeLevel;

    public Integer getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(Integer privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
