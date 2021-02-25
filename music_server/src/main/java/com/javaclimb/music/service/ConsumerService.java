package com.javaclimb.music.service;

import com.javaclimb.music.domain.Consumer;

import java.util.List;

/**
 * 前端用户service接口
 */
public interface ConsumerService {
    /**
     * 增加
     */
    public boolean insert(Consumer consumer);

    /**
     * 修改
     */
    public boolean update(Consumer consumer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据逐渐查询整个对象
     */
    public Consumer selectByPrimaryKey(Integer id);

    /**
     * 查询所有用户
     */
    public List<Consumer> allConsumer();

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    public boolean verifyPassword(String username, String password);

    /**
     * 根据账号模糊查询
     * @param username
     * @return
     */
    public Consumer getByUsername(String username);
}
