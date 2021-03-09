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

    @RequestMapping(value = "/admin/add",method = RequestMethod.POST)
    public Object addAdmin(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name").trim();
        String password=request.getParameter("password").trim();
        String privilegeLevel=request.getParameter("privilegeLevel").trim();

        //保存到上传者对象中
       Admin admin=new Admin();
        admin.setName(name);
        admin.setPassword(password);
        admin.setPrivilegeLevel(Integer.parseInt(privilegeLevel));
        boolean flag=adminService.insert(admin);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加用户成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加用户失败");
        return jsonObject;
    }

    @RequestMapping(value = "/admin/update",method = RequestMethod.POST)
    public Object updateAdmin(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String name=request.getParameter("name").trim();
        String password=request.getParameter("password").trim();
        String privilegeLevel=request.getParameter("privilegeLevel").trim();
        //保存到上传者对象中
        Admin admin=new Admin();
        admin.setId(Integer.parseInt(id));
        admin.setName(name);
        admin.setPassword(password);
        admin.setPrivilegeLevel(Integer.parseInt(privilegeLevel));
        boolean flag=adminService.update(admin);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改用户成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改用户失败");
        return jsonObject;
    }

    /**
     * 删除一个管理员
     * @param request
     * @return
     */
    @RequestMapping(value= "/admin/delete",method = RequestMethod.GET)
    public Object deleteUploader(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        boolean flag=adminService.delete(Integer.parseInt(id));
        return flag;
    }

    @RequestMapping(value="/admin/verifyExist",method = RequestMethod.GET)
    public Object uploaderOfStudyNumber(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name").trim();
        List<Admin>list=adminService.adminOfName(name);
        if(list.size()==0){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"无此用户");
        }
        else {
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"用户已存在");
        }
        return jsonObject;
    }
}
