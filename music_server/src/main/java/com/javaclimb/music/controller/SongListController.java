package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.SongList;
import com.javaclimb.music.service.SongListService;
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


/**
 * 歌单控制类
 */
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    /**
     * 添加歌单
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String title=request.getParameter("title").trim();
        String pic=request.getParameter("pic").trim();
        String introduction=request.getParameter("introduction").trim();
        String style= request.getParameter("style").trim();
        //保存到歌手对象中
        SongList songList=new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag=songListService.insert(songList);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加歌单成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加歌单失败");
        return jsonObject;
    }
    /**
     * 修改歌手信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String title=request.getParameter("title").trim();
        String introduction=request.getParameter("introduction").trim();
        String style =request.getParameter("style").trim();
        //保存到歌手对象中
        SongList songList=new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag=songListService.update(songList);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改歌单成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改歌单失败");
        return jsonObject;
    }
    /**
     * 删除歌手信息
     */
    @RequestMapping(value= "/delete",method = RequestMethod.GET)
    public Object deleteSongList(HttpServletRequest request){
        String id=request.getParameter("id").trim();

        boolean flag=songListService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询某个对象
     */
    @RequestMapping(value="/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return songListService.selectByPrimaryKey(Integer.parseInt(id));

    }

    /**
     * 查询所有歌手
     */
    @RequestMapping(value="/allSongList",method = RequestMethod.GET)
    public Object allSongList(HttpServletRequest request){
        return songListService.allSongList();
    }

    /**
     * 根据主题精确查找歌单
     */
    @RequestMapping(value="/songListOfTitle",method = RequestMethod.GET)
    public Object songListOfNmae(HttpServletRequest request){
        String title=request.getParameter("title").trim();
        return songListService.songListOfTitle("%"+title+"%");
    }


    /**
     * 根据主题模糊查询歌单
     */
    @RequestMapping(value="/likeTitle",method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request){
        String title=request.getParameter("title").trim();
        return songListService.likeTitle("%"+title+"%");

    }
    /**
     * 根据主题模糊查询歌单
     */
    @RequestMapping(value="/likeStyle",method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        String style=request.getParameter("style").trim();
        return songListService.likeTitle("%"+style+"%");

    }



    /**
     * 更新歌单图片
     * @param avatorFile
     * @param id
     * @return
     */
    @RequestMapping(value= "/updateSongListPic",method = RequestMethod.POST)
    public Object updateSongListPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject= new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
//        currentTimeMillis是一个当前时间精确到毫秒的长字符串，这样做的目的是避免同时上传两个文件的时候其中一个被覆盖掉

        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath =System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
		+System.getProperty("file.separator")+"songListPic";
        File file1 = new File(filePath);
//        如果文件路劲不存在，创建新的路劲
        if(!file1.exists()){
            file1.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath= "/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            boolean flag= songListService.update(songList);
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
