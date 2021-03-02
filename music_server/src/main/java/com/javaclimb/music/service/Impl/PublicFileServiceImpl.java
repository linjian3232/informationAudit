package com.javaclimb.music.service.Impl;

import com.javaclimb.music.dao.PublicFileMapper;
import com.javaclimb.music.domain.PublicFile;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicFileServiceImpl implements PublicFileService {
    @Autowired
    private PublicFileMapper publicFileMapper;
    /**
     * 增加
     *
     * @param publicFile
     */
    @Override
    public boolean insert(PublicFile publicFile) {
        return publicFileMapper.insert(publicFile)>0;
    }

    /**
     * 修改
     *
     * @param publicFile
     */
    @Override
    public boolean update(PublicFile publicFile) {
        return publicFileMapper.update(publicFile)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return publicFileMapper.delete(id)>0;
    }

    /**
     * 根据逐渐查询整个对象
     *
     * @param id
     */
    @Override
    public PublicFile selectByPrimaryKey(Integer id) {
        return publicFileMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<PublicFile> allFile() {
        return publicFileMapper.allFile();
    }

    /**
     * 根据introduction精确查询列表
     *
     * @param status
     */
    @Override
    public List<PublicFile> publicFileOfStatus(Integer status,Integer uploaderId) {
        List<PublicFile> fileList=publicFileMapper.publicFileOfStatus(status);
        List<PublicFile> list=new ArrayList<>();
        for(PublicFile publicFile:fileList)
        {
            if(publicFile.getUploaderId().equals(uploaderId)){
                list.add(publicFile);
            }
        }
        return list;
    }

    /**
     * 根据name精模糊查询列表
     *
     * @param name
     */
    @Override
    public List<PublicFile> publicFileOfName(String name) {
        return publicFileMapper.publicFileOfName(name);
    }

    /**
     * 根据歌手id查询
     *
     * @param uploaderId
     * @returnId
     */
    @Override
    public List<PublicFile> publicFileOfUploaderId(Integer uploaderId) {
        return publicFileMapper.publicFileOfUploaderId(uploaderId);
    }
}
