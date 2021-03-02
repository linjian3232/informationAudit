package com.javaclimb.music.dao;

import com.javaclimb.music.domain.PublicFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface PublicFileMapper {
    /**
     * 增加
     */
    public int insert(PublicFile publicFile);

    /**
     * 修改
     */
    public int update(PublicFile publicFile);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据逐渐查询整个对象
     */
    public PublicFile selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<PublicFile> allFile();


    /**
     * 根据上传者id以及status查询列表
     */
    public List<PublicFile> publicFileOfStatus(Integer status);

    /**
     * 根据文件名模糊查询列表
     */
    public List<PublicFile> publicFileOfName(String name);

    /**
     * 根据上传者id查询
     * @param uploaderId
     * @returnId
     */
    public List<PublicFile> publicFileOfUploaderId(Integer uploaderId);
}
