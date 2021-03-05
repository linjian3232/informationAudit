package com.javaclimb.music.service;

/**
 * 管理员service接口
 */
public interface AdminService  {
    public int verifyPasswords(String username,String password);
    public int ifExist(String username,String password);
}
