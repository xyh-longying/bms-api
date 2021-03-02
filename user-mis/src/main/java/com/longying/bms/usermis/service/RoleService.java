package com.longying.bms.usermis.service;

import com.longying.bmsdata.modules.ums.model.UmsRole;

import java.util.List;

/**
 * Create by chenglong on 2021/3/1
 */
public interface RoleService {

    UmsRole createModel(UmsRole model);

    List<String> relationRoles(String userId, List<String> roleIdList);

    List<UmsRole> getUserRelRoles(String userId);

    UmsRole modifyRole(UmsRole model);
}
