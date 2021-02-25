package com.javaclimb.music.dao;

import com.javaclimb.music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单里歌曲Dao
 */
@Repository
public interface ListSongMapper {
    /**
     * 增加
     */
    public int insert(ListSong listSong);

    /**
     * 修改
     */
    public int update(ListSong listSong);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据歌曲id和歌单id删除歌单中歌曲
     * @param songId
     * @param songListId
     * @return
     */
    public int deleteBySongIdAndSongListId(Integer songId,Integer songListId);

    /**
     * 根据逐渐查询整个对象
     */
    public ListSong selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单
     */
    public List<ListSong> allListSong();


    /**
     * 查询歌单中所有歌曲
     * @param songListId
     * @return
     */
    public List<ListSong> listSongOfSongListId (Integer songListId);
}
