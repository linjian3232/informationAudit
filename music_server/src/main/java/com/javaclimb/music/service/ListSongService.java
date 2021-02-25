package com.javaclimb.music.service;

import com.javaclimb.music.domain.ListSong;

import java.util.List;

public interface ListSongService {
    /**
     * 增加
     */
    public boolean insert(ListSong listSong);

    /**
     * 修改
     */
    public boolean update(ListSong listSong);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据歌曲id和歌单id删除歌单中歌曲
     * @param songId
     * @param songListId
     * @return
     */
    public boolean deleteBySongIdAndSongListId(Integer songId,Integer songListId);

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
