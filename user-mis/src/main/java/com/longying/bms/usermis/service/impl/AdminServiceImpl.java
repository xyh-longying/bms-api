package com.longying.bms.usermis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longying.bms.usermis.service.AdminService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsbase.base.BaseUtil;
import com.longying.bmsbase.base.SystemSettingCode;
import com.longying.bmsdata.modules.ums.model.UmsUser;
import com.longying.bmsdata.modules.ums.service.UmsUserService;
import com.longying.bmsserver.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by chenglong on 2021/2/28
 */
@Service
@Slf4j
public class AdminServiceImpl extends BaseService implements AdminService {

    @Autowired
    private UmsUserService umsUserService;

    @Override
    public UmsUser createModel(UmsUser model) {
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getIsDelete, BaseConstants.DEL_NO)
                .eq(UmsUser::getUsername, model.getUsername());
        if(umsUserService.count(wrapper)>0){
            throwException("B010201001", model.getUsername());
        }
        String pwdRegex = getSystemSettingByCode(SystemSettingCode.ADMIN_PWD_REGEX).getValue();
        if(!model.getPassword().matches(pwdRegex)){
            throwException("B010201002",pwdRegex);
        }
        model.setPassword(BaseUtil.bcryptPwd(model.getPassword()));
        umsUserService.save(model);
        return model;
    }
}
