package com.longying.bms.usermis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longying.bms.usermis.service.LoginService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsdata.modules.ums.model.UmsUser;
import com.longying.bmsdata.modules.ums.service.UmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by chenglong on 2021/2/23
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UmsUserService umsUserService;

    @Override
    public String doLogin(String username, String password, String terminal, String ip) {
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(UmsUser::getIsDelete, BaseConstants.DEL_NO)
                .eq(UmsUser::getUsername, username);
        UmsUser umsUser = umsUserService.getOne(wrapper);
        return null;
    }
}
