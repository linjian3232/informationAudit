package com.informationaudit.service;

import com.informationaudit.domain.Uploader;


import java.util.List;

/**
 * 管理员service接口
 */

public interface UploaderService {
    /**
     * 增加
     */
    public boolean insert(Uploader uploader);

    /**
     * 修改
     */
    public boolean update(Uploader uploader);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据逐渐查询整个对象
     */
    public Uploader selectByPrimaryKey(Integer id);

    /**
     * 查询所有上传者
     */
    public List<Uploader> allUploader();


    /**
     * 根据上传者名字模糊查询列表
     */
    public List<Uploader> uploaderOfName(String name);

    /**
     * 根据性别查询
     * @param gender
     * @return
     */
    public List<Uploader> uploaderOfGender (Integer gender);

    /**
     * 根据学号查询是否有此用户
     * @param studyNumber
     * @return
     */
    public List<Uploader> uploaderOfStudyNumber (String studyNumber);


}
