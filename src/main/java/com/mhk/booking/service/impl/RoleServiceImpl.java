package com.mhk.booking.service.impl;

import com.mhk.booking.service.RoleService;
import org.springframework.stereotype.Component;



@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public String getUserRole(Integer userId) {
        return null;
    }



/*    @Override
    public String getUserRole(Integer userId) {
        return roleDao.getRoleUser(userId);
    }*/
}
