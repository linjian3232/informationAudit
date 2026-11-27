package com.informationaudit.controller;

import com.informationaudit.domain.Uploader;
import com.informationaudit.service.UploaderService;
import com.informationaudit.storage.AuditStorage;
import com.informationaudit.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传者控制类
 */
@RestController
@RequestMapping("/uploader")
public class UploaderController {
    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private AuditStorage auditStorage;

    /**
     * 添加上传者
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addUploader(HttpServletRequest request){
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
        Map<String, Object> jsonObject=new LinkedHashMap<>();
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
     * 根据性别查询
     */
    @RequestMapping(value="/uploaderOfStudyNumber",method = RequestMethod.GET)
    public Object uploaderOfStudyNumber(HttpServletRequest request){
        Map<String, Object> jsonObject=new LinkedHashMap<>();
        String studyNumber=request.getParameter("studyNumber").trim();
        List<Uploader>list=uploaderService.uploaderOfStudyNumber(studyNumber);
        if(list.size()==0){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"无此上传者");
        }
        else {
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"上传者已存在");
        }
            return jsonObject;
    }


    /**
     * 更新上传者图片
     * @param avatorFile
     * @param id
     * @return
     */
    @RequestMapping(value= "/updateUploaderPic",method = RequestMethod.POST)
    public Object updateUploaderPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        Map<String, Object> jsonObject= new LinkedHashMap<>();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        try {
            String storedUrl = auditStorage.storeUploaderPicture(avatorFile);
            Uploader uploader = new Uploader();
            uploader.setId(id);
            uploader.setPic(storedUrl);
            boolean flag= uploaderService.update(uploader);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic", storedUrl);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;

        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存头像失败");
            return jsonObject;
        }
    }


}
