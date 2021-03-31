package com.javaclimb.music.service;

import com.javaclimb.music.domain.PublicFile;

import java.util.List;

public interface PublicFileService {
    /**
     * 增加
     */
    public boolean insert(PublicFile publicFile);

    /**
     * 修改
     */
    public boolean update(PublicFile publicFile);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据逐渐查询整个对象
     */
    public PublicFile selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<PublicFile> allFile();
    
    /**
     * 根据introduction精确查询列表
     */
    public List<PublicFile> publicFileOfStatus(Integer status,Integer uploaderId);

    /**
     * 根据name精模糊查询列表
     */
    public List<PublicFile> publicFileOfName(String name);

    /**
     * 根据歌手id查询
     * @param uploaderId
     * @returnId
     */
    public List<PublicFile> publicFileOfUploaderId(Integer uploaderId);
}
