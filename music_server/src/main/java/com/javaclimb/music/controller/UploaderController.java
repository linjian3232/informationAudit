package com.javaclimb.music.controller;
import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Uploader;
import com.javaclimb.music.service.UploaderService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传者控制类
 */
@RestController
@RequestMapping("/uploader")
public class UploaderController {
    @Autowired
    private UploaderService uploaderService;

    /**
     * 添加上传者
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addUploader(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name").trim();
        String gender=request.getParameter("gender").trim();
        String pic=request.getParameter("pic").trim();
        String studyNumber=request.getParameter("studyNumber").trim();
        String academy=request.getParameter("academy").trim();
        String major=request.getParameter("major").trim();

        //保存到上传者对象中
        Uploader uploader=new Uploader();
        uploader.setName(name);
        uploader.setGender(new Byte(gender));
        uploader.setPic(pic);
        uploader.setStudyNumber(studyNumber);
        uploader.setAcademy(academy);
        uploader.setMajor(major);
        boolean flag=uploaderService.insert(uploader);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加上传者成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加上传者失败");
        return jsonObject;
    }
    /**
     * 修改上传者信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateUploader(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String name=request.getParameter("name").trim();
        String gender=request.getParameter("gender").trim();
        String studyNumber=request.getParameter("studyNumber").trim();
        String academy=request.getParameter("academy").trim();
        String major=request.getParameter("major").trim();

        //保存到上传者对象中
        Uploader uploader=new Uploader();
        uploader.setId(Integer.parseInt(id));
        uploader.setName(name);
        uploader.setGender(new Byte(gender));
        uploader.setStudyNumber(studyNumber);
        uploader.setAcademy(academy);
        uploader.setMajor(major);
        boolean flag=uploaderService.update(uploader);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改上传者成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改上传者失败");
        return jsonObject;
    }
    /**
     * 删除上传者信息
     */
    @RequestMapping(value= "/delete",method = RequestMethod.GET)
    public Object deleteUploader(HttpServletRequest request){
        String id=request.getParameter("id").trim();

        boolean flag=uploaderService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询某个对象
     */
    @RequestMapping(value="/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return uploaderService.selectByPrimaryKey(Integer.parseInt(id));

    }

    /**
     * 查询所有上传者
     */
    @RequestMapping(value="/allUploader",method = RequestMethod.GET)
    public Object allUploader(HttpServletRequest request){
        return uploaderService.allUploader();
    }

    /**
     * 根据上传者名字查询
     */
    @RequestMapping(value="/uploaderOfName",method = RequestMethod.GET)
    public Object uploaderOfName(HttpServletRequest request){
        String name=request.getParameter("name").trim();
        return uploaderService.uploaderOfName("%"+name+"%");
    }

    /**
     * 根据性别查询
     */
    @RequestMapping(value="/uploaderOfGender",method = RequestMethod.GET)
    public Object uploaderOfGender(HttpServletRequest request){
        String gender=request.getParameter("gender").trim();
        return uploaderService.uploaderOfGender(Integer.parseInt(gender));

    }


    /**
     * 更新上传者图片
     * @param avatorFile
     * @param id
     * @return
     */
    @RequestMapping(value= "/updateUploaderPic",method = RequestMethod.POST)
    public Object updateUploaderPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject= new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
//        currentTimeMillis是一个当前时间精确到毫秒的长字符串，这样做的目的是避免同时上传两个文件的时候其中一个被覆盖掉

        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath =System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
		+System.getProperty("file.separator")+"uploaderPic";
        File file1 = new File(filePath);
//        如果文件路劲不存在，创建新的路劲
        if(!file1.exists()){
            file1.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath= "/img/uploaderPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Uploader uploader = new Uploader();
            uploader.setId(id);
            uploader.setPic(storeAvatorPath);
            boolean flag= uploaderService.update(uploader);
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
