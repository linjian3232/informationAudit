package com.javaclimb.music.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.service.SingerService;
import com.javaclimb.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 歌手控制类
 */
@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    /**
     * 添加歌手
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name").trim();
        String gender=request.getParameter("gender").trim();
        String pic=request.getParameter("pic").trim();
        String birth=request.getParameter("birth").trim();
        String location=request.getParameter("location").trim();
        String introduction=request.getParameter("introduction").trim();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate= new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        //保存到歌手对象中
        Singer singer=new Singer();
        singer.setName(name);
        singer.setGender(new Byte(gender));
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag=singerService.insert(singer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加歌手成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加歌手失败");
        return jsonObject;
    }
    /**
     * 修改歌手信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String name=request.getParameter("name").trim();
        String gender=request.getParameter("gender").trim();
        String pic=request.getParameter("pic").trim();
        String birth=request.getParameter("birth").trim();
        String location=request.getParameter("location").trim();
        String introduction=request.getParameter("introduction").trim();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate= new Date();
        try {
            birthDate = dateFormat.parse(birth);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        //保存到歌手对象中
        Singer singer=new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setGender(new Byte(gender));
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag=singerService.insert(singer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改歌手成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改歌手失败");
        return jsonObject;
    }
    /**
     * 删除歌手信息
     */
    @RequestMapping(value= "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        String id=request.getParameter("id").trim();

        boolean flag=singerService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询某个对象
     */
    @RequestMapping(value="/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return singerService.selectByPrimaryKey(Integer.parseInt(id));

    }

    /**
     * 查询所有歌手
     */
    @RequestMapping(value="/allSinger",method = RequestMethod.GET)
    public Object allSinger(HttpServletRequest request){
        return singerService.allSinger();
    }

    /**
     * 根据歌手名字查询
     */
    @RequestMapping(value="/singerOfName",method = RequestMethod.GET)
    public Object singerOfNmae(HttpServletRequest request){
        String name=request.getParameter("name").trim();
        return singerService.singerOfName("%"+name+"%");
    }

    /**
     * 根据性别查询
     */
    @RequestMapping(value="/singerOfGender",method = RequestMethod.GET)
    public Object singerOfGender(HttpServletRequest request){
        String gender=request.getParameter("gender").trim();
        return singerService.selectByPrimaryKey(Integer.parseInt(gender));

    }
//    更新歌手图片
    @RequestMapping(value= "/updateSingerPic",method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject= new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
//        currentTimeMillis是一个当前时间精确到毫秒的长字符串，这样做的目的是避免同时上传两个文件的时候其中一个被覆盖掉

        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath =System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")+"singerPic";
        File file1 = new File(filePath);
//        如果文件路劲不存在，创建新的路劲
        if(!file1.exists()){
            file1.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath= "/img/singerPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag= singerService.update(singer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
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
