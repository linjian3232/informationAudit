package com.javaclimb.music.service.Impl;

import com.javaclimb.music.dao.UploaderMapper;
import com.javaclimb.music.domain.Uploader;
import com.javaclimb.music.service.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 上传者uploader实现类
 */
@Service
public class UploaderServiceImpl implements UploaderService {
    @Autowired
    private UploaderMapper uploaderMapper;
    /**
     * 增加
     *
     * @param uploader
     */
    @Override
    public boolean insert(Uploader uploader) {
        return uploaderMapper.insert(uploader)>0;
    }

    /**
     * 修改
     *
     * @param uploader
     */
    @Override
    public boolean update(Uploader uploader) {
        return uploaderMapper.update(uploader)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return uploaderMapper.delete(id)>0;
    }


    /**
     * 根据逐渐查询整个对象
     *
     * @param id
     */
    @Override
    public Uploader selectByPrimaryKey(Integer id) {
        return uploaderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌手
     */
    @Override
    public List<Uploader> allUploader() {
        return uploaderMapper.allUploader();
    }

    /**
     * 根据歌手名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Uploader> uploaderOfName(String name) {
        return uploaderMapper.uploaderOfName(name);
    }

    @Override
    public List<Uploader> uploaderOfGender(Integer gender) {
        return uploaderMapper.uploaderOfGender(gender);
    }

    /**
     * 根据学号查询是否有此用户
     *
     * @param studyNumber
     * @return
     */
    @Override
    public List<Uploader> uploaderOfStudyNumber(String studyNumber) {
        return uploaderMapper.uploaderOfStudyNumber(studyNumber);
    }
}
