package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员Dao
 */
@Repository
public interface AdminMapper {
    /**
     * 验证密码是否正确
     */
    public int verifyPassword(String username,String password);

    public int ifExist(String username,String password);

    public List<Admin> getAllReviewer(Integer level);
    public List<Admin> getAllAdmin();
}
