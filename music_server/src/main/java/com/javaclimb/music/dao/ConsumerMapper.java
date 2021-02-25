package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Dao
 */
@Repository
public interface ConsumerMapper {
/**
 * 增加
 */
    public int insert(Consumer consumer);

    /**
     * 修改
     */
    public int update(Consumer consumer);

    /**
     * 删除
     */
    public int delete(Integer id);

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
    public int verifyPassword(String username, String password);

    /**
     * 根据账号模糊查询
     * @param username
     * @return
     */
    public Consumer getByUsername(String username);
}
