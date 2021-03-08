package com.javaclimb.music.service.Impl;

import com.javaclimb.music.dao.AdminMapper;
import com.javaclimb.music.domain.Admin;
import com.javaclimb.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员sevice实现类 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public int verifyPasswords(String username, String password) {
        return adminMapper.verifyPassword(username,password);
    }

    @Override
    public int ifExist(String username, String password) {
        return adminMapper.ifExist(username,password);
    }

    @Override
    public List<Admin> getAllReviewer(Integer level) {
        return adminMapper.getAllReviewer(level);
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.getAllAdmin();
    }
}
