package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface SingerMapper {
/**
 * 增加
 */
    public int insert(String singer);

    /**
     * 修改
     */
    public int update(String singer);

    /**
     * 删除
     */
    public int delete(String singer);

    /**
     * 根据逐渐查询整个对象
     */
    public Singer selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();


    /**
     * 根据歌手名字模糊查询列表
     */
    public List<Singer> singerOfNmae(String name);

    public List<Singer> singerOfGender (Integer gender);
}
