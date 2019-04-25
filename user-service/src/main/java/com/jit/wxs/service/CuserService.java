package com.jit.wxs.service;

import com.jit.wxs.entity.CUser;
import com.jit.wxs.mapper.CUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuserService {
    @Autowired
    CUserMapper cUserMapper;

    public List<CUser> getUserList(CUser user){
        List<CUser> userList = cUserMapper.getUserList(user);
        return userList;
    }

    public CUser getUserInfo(CUser user){
        CUser userInfo = cUserMapper.getUserInfo(user);
        return userInfo;
    }
}
