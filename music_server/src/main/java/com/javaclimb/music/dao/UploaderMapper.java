package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Uploader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface UploaderMapper {
/**
 * 增加
 */
    public int insert(Uploader uploader);

    /**
     * 修改
     */
    public int update(Uploader uploader);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据逐渐查询整个对象
     */
    public Uploader selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Uploader> allUploader();


    /**
     * 根据歌手名字模糊查询列表
     */
    public List<Uploader> uploaderOfName(String name);

    public List<Uploader> uploaderOfGender (Integer gender);
}
