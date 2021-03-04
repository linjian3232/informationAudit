package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.PublicFile;
import com.javaclimb.music.service.PublicFileService;
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
import java.util.List;

/**
 * 歌曲管理controller
 */
@RestController
@RequestMapping("/publicFile")
public class PublicFileController {

    @Autowired
    private PublicFileService publicFileService;

    /**
     * 添加歌曲
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addPublicFile(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
//        获取前端传来的参数
        String uploaderId=request.getParameter("uploaderId").trim();
        String uploaderName=request.getParameter("uploaderName").trim();
        String name=uploaderName+'-'+mpFile.getOriginalFilename();
        String beginDate=request.getParameter("beginDate").trim();
        String endDate=request.getParameter("endDate").trim();
        String beginTime= request.getParameter("beginTime");
        String endTime=request.getParameter("endTime");
        String introduction=request.getParameter("introduction").trim();
        Integer status=0;//0代表未审核
        String firstReason="暂无";
        String secondReason="暂无";
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date startDate= new Date();Date finalDate=new Date();
        System.out.println(beginDate);
        try {
            startDate = dateFormat.parse(beginDate);
            finalDate = dateFormat.parse(endDate);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        //上传文件
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"publicFile";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "/publicFile/"+fileName;
        try {
            mpFile.transferTo(dest);
            PublicFile publicFile = new PublicFile();
            publicFile.setUploaderId(Integer.parseInt(uploaderId));
            publicFile.setName(name);
            publicFile.setIntroduction(introduction);
            publicFile.setStatus(status);
            publicFile.setFirstReason(firstReason);
            publicFile.setSecondReason(secondReason);
            publicFile.setBeginDate(startDate);
            publicFile.setEndDate(finalDate);
            publicFile.setBeginTime(beginTime);
            publicFile.setEndTime(endTime);
            publicFile.setUrl(storeUrlPath);
            boolean flag= publicFileService.insert(publicFile);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;

        }catch (IOException e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }
    /**
     * 根据歌手id查询
     */
    @RequestMapping(value="/uploader/detail" ,method = RequestMethod.GET)
    public Object publicFileOfUploaderId(HttpServletRequest request){
        String uploaderId= request.getParameter("uploaderId");
        List<PublicFile> list=publicFileService.publicFileOfUploaderId(Integer.parseInt(uploaderId));
        return publicFileService.publicFileOfUploaderId(Integer.parseInt(uploaderId));
    }

    @RequestMapping(value="/allPublicFile",method = RequestMethod.GET)
    public Object allPublicFileList(HttpServletRequest request){
        return publicFileService.allFile();
    }

    /**
     * 修改歌曲
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updatePublicFile(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String introduction=request.getParameter("introduction").trim();
        String beginDate=request.getParameter("beginDate").trim();
        String endDate=request.getParameter("endDate").trim();
        String beginTime=request.getParameter("beginTime").trim();
        String endTime=request.getParameter("endTime").trim();
        String name=request.getParameter("name").trim();

        System.out.println("update:"+beginDate);
        //保存到歌曲对象中
        System.out.println(endDate);
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date startDate= new Date();Date finalDate=new Date();
        try {
            startDate = dateFormat.parse(beginDate);
            finalDate = dateFormat.parse(endDate);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        PublicFile publicFile=new PublicFile();
        publicFile.setId(Integer.parseInt(id));
        publicFile.setName(name);
        publicFile.setIntroduction(introduction);
        publicFile.setBeginDate(startDate);
        publicFile.setEndDate(finalDate);
        publicFile.setBeginTime(beginTime);
        publicFile.setEndTime(endTime);
        boolean flag=publicFileService.update(publicFile);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改文件信息成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改文件信息失败");
        return jsonObject;
    }

    /**
     * 修改歌曲状态
     */
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public Object updatePublicFileStatus(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String status=request.getParameter("status").trim();
        System.out.println("change Status to:"+status);
        //保存到歌曲对象中
        PublicFile publicFile=new PublicFile();
        System.out.println("id:"+id+"  status:"+status);
        publicFile.setId(Integer.parseInt(id));
        publicFile.setStatus(Integer.parseInt(status));
        boolean flag=publicFileService.update(publicFile);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"审核成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"审核失败");
        return jsonObject;
    }

    /**
     * 保存一级反馈原因
     */
    @RequestMapping(value = "/updateFirstReason",method = RequestMethod.POST)
    public Object updateFirstReason(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String firstReason=request.getParameter("firstReason").trim();
        System.out.println("change firstReason to:"+firstReason);
        //保存到歌曲对象中
        PublicFile publicFile=new PublicFile();
        publicFile.setId(Integer.parseInt(id));
        publicFile.setFirstReason(firstReason);
        boolean flag=publicFileService.update(publicFile);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"反馈成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"反馈失败");
        return jsonObject;
    }



    /**
     * 删除歌曲信息
     */
    @RequestMapping(value= "/delete",method = RequestMethod.GET)
    public Object deletePublicFile(HttpServletRequest request){
        String id=request.getParameter("id").trim();

        boolean flag=publicFileService.delete(Integer.parseInt(id));
        return flag;
    }





    /**
     * 更新文件
     */
    @RequestMapping(value = "/updatePublicFileUrl",method = RequestMethod.POST)
    public Object updatePublicFileUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id,@RequestParam("uploaderName")String name){
        JSONObject jsonObject = new JSONObject();
        String newName=name+"-"+avatorFile.getOriginalFilename();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"publicFile";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/publicFile/"+fileName;
        try {
            avatorFile.transferTo(dest);
            PublicFile publicFile = new PublicFile();
            publicFile.setId(id);
            publicFile.setUrl(storeAvatorPath);
            publicFile.setName(newName);
            boolean flag = publicFileService.update(publicFile);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 根据文件id查询
     */
    @RequestMapping(value="/detail" ,method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String publicFileId= request.getParameter("publicFileId");
        return publicFileService.selectByPrimaryKey(Integer.parseInt(publicFileId));
    }

    /**
     * 根据上传者id查询
     */
    @RequestMapping(value="/publicFileOfUploader" ,method = RequestMethod.GET)
    public Object publicFileOfUploader(HttpServletRequest request){
        String uploaderId= request.getParameter("uploaderId");
        return publicFileService.publicFileOfUploaderId(Integer.parseInt(uploaderId));
    }

    /**
         * 根据某个上传者中文件状态查询
         */
        @RequestMapping(value="/publicFileOfStatus" ,method = RequestMethod.GET)
        public Object publicFileOfStatus(HttpServletRequest request){
            String status= request.getParameter("status");
            String uploaderId= request.getParameter("uploaderId");
            return publicFileService.publicFileOfStatus(Integer.parseInt(status),Integer.parseInt(uploaderId));
    }
}
