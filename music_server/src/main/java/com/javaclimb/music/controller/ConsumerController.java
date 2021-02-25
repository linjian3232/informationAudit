package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.service.ConsumerService;
import com.javaclimb.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 前端用户控制类
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /**
     * 添加前端用户
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String username=request.getParameter("username").trim();
        String password=request.getParameter("password").trim();
        String gender=request.getParameter("gender").trim();
        String phoneNumber=request.getParameter("phoneNumber").trim();
        String email=request.getParameter("email").trim();
        String birth=request.getParameter("birth").trim();
        String introduction=request.getParameter("introduction").trim();
        String location=request.getParameter("location").trim();
        String avator=request.getParameter("avator").trim();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }
        Date birthDate= new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        //保存到前端用户对象中
        Consumer consumer=new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setGender(new Byte(gender));
        consumer.setPhoneNumber(phoneNumber);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setLocation(location);
        consumer.setIntroduction(introduction);
        consumer.setAvator(avator);
        boolean flag=consumerService.insert(consumer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加前端用户成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加前端用户失败");
        return jsonObject;
    }
    /**
     * 修改前端用户信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String username=request.getParameter("username").trim();
        String password=request.getParameter("password").trim();
        String gender=request.getParameter("gender").trim();
        String phoneNumber=request.getParameter("phoneNumber").trim();
        String email=request.getParameter("email").trim();
        String birth=request.getParameter("birth").trim();
        String introduction=request.getParameter("introduction").trim();
        String location=request.getParameter("location").trim();
        String avator=request.getParameter("avator").trim();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }
        Date birthDate= new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        //保存到前端用户对象中
        Consumer consumer=new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setGender(new Byte(gender));
        consumer.setPhoneNumber(phoneNumber);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setLocation(location);
        consumer.setIntroduction(introduction);
        boolean flag=consumerService.update(consumer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改前端用户成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改前端用户失败");
        return jsonObject;
    }
    /**
     * 删除前端用户信息
     */
    @RequestMapping(value= "/delete",method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request){
        String id=request.getParameter("id").trim();

        boolean flag=consumerService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询某个对象
     */
    @RequestMapping(value="/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));

    }

    /**
     * 查询所有前端用户
     */
    @RequestMapping(value="/allConsumer",method = RequestMethod.GET)
    public Object allConsumer(HttpServletRequest request){
        return consumerService.allConsumer();
    }



    /**
     * 更新前端用户图片
     * @param avatorFile
     * @param id
     * @return
     */
    @RequestMapping(value= "/updateConsumerPic",method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject= new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
//        currentTimeMillis是一个当前时间精确到毫秒的长字符串，这样做的目的是避免同时上传两个文件的时候其中一个被覆盖掉

        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath =System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImg"+System.getProperty("file.separator");
        File file1 = new File(filePath);
//        如果文件路劲不存在，创建新的路劲
        if(!file1.exists()){
            file1.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath= "/avatorImg/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag= consumerService.update(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;

        }catch (IOException e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }


}
