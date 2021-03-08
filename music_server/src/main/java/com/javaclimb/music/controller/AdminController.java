package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Admin;
import com.javaclimb.music.domain.Uploader;
import com.javaclimb.music.service.AdminService;
import com.javaclimb.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 判断是否登录成功
     */
    @RequestMapping(value = "/admin/login/status",method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest request, HttpSession session){
        JSONObject jsonObject=new JSONObject();
        String name= request.getParameter("name");
        String password=request.getParameter("password");
        int exist=adminService.ifExist(name,password);
        if(exist==0){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名或密码错误");
            return jsonObject;
        }else {
            int flag = adminService.verifyPasswords(name, password);
            if (flag > 0) {
                jsonObject.put(Consts.CODE, flag);
                jsonObject.put(Consts.MSG, "登录成功:" + flag);
                session.setAttribute(Consts.NAME, name);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,-1);
            jsonObject.put(Consts.MSG,"出现未知错误");
            return jsonObject;
        }

    }
    /**
     * 根据权限等级查询
     */
    @RequestMapping(value="/admin/level",method = RequestMethod.GET)
    public Object adminOfLevel(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String level=request.getParameter("privilegeLevel").trim();
        List<Admin> list=adminService.getAllReviewer(Integer.parseInt(level));
        return list;
    }

    /**
     * 根据权限等级查询
     */
    @RequestMapping(value="/admin/all",method = RequestMethod.GET)
    public Object allAdmin(HttpServletRequest request){

        List<Admin> list=adminService.getAllAdmin();
        return list;
    }
}
