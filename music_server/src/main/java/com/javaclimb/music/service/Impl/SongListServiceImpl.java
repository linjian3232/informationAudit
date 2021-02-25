package com.javaclimb.music.service.Impl;

import com.javaclimb.music.dao.SongListMapper;
import com.javaclimb.music.domain.SongList;
import com.javaclimb.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;
    /**
     * 增加
     *
     * @param songList
     */
    @Override
    public boolean insert(SongList songList) {
       return songListMapper.insert(songList)>0;
    }

    /**
     * 修改
     *
     * @param songList
     */
    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    /**
     * 根据逐渐查询整个对象
     *
     * @param id
     */
    @Override
    public SongList selectByPrimaryKey(Integer id) {
        return songListMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<SongList> allSongList() {
        return songListMapper.allSongList();
    }

    /**
     * 根据标题精确查询歌单列表
     *
     * @param title
     */
    @Override
    public List<SongList> songListOfTitle(String title) {
        return songListMapper.songListOfTitle(title);
    }

    /**
     * 根据标题模糊查询
     *
     * @param title
     * @return
     */
    @Override
    public List<SongList> likeTitle(String title) {
        return songListMapper.likeTitle(title);
    }

    /**
     * 根据风格模糊查询
     *
     * @param style
     * @return
     */
    @Override
    public List<SongList> likeStyle(String style) {
        return songListMapper.likeStyle(style);
    }
}
