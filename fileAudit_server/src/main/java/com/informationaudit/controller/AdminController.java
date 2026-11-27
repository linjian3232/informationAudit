package com.informationaudit.controller;

import com.informationaudit.domain.Admin;
import com.informationaudit.service.AdminService;
import com.informationaudit.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 判断是否登录成功
     */
    @PostMapping("/admin/login/status")
    public Object loginStatus(@RequestParam(name = "name", defaultValue = "") String name,
                              @RequestParam(name = "password", defaultValue = "") String password,
                              HttpSession session) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (name.isEmpty() || password.isEmpty() || adminService.ifExist(name, password) == 0) {
            result.put(Consts.CODE, 0);
            result.put(Consts.MSG, "账号或密码错误");
            return result;
        }

        int role = adminService.verifyPasswords(name, password);
        if (role < 1 || role > 4) {
            result.put(Consts.CODE, -1);
            result.put(Consts.MSG, "账号角色无效");
            return result;
        }

        session.setAttribute(Consts.NAME, name);
        result.put(Consts.CODE, role);
        result.put(Consts.MSG, "登录成功");
        return result;
    }
    /**
     * 根据权限等级查询
     */
    @RequestMapping(value="/admin/level",method = RequestMethod.GET)
    public Object adminOfLevel(HttpServletRequest request){
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
    public Object deleteAdmin(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        boolean flag=adminService.delete(Integer.parseInt(id));
        return flag;
    }

    @RequestMapping(value="/admin/verifyExist",method = RequestMethod.GET)
    public Object verifyAdminExists(HttpServletRequest request){
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
