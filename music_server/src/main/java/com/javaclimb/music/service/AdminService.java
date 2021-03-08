package com.javaclimb.music.service;

import com.javaclimb.music.domain.Admin;

import java.util.List;

/**
 * 管理员service接口
 */
public interface AdminService  {
    public int verifyPasswords(String username,String password);
    public int ifExist(String username,String password);
    public List<Admin> getAllReviewer(Integer level);
    public List<Admin> getAllAdmin();
}
