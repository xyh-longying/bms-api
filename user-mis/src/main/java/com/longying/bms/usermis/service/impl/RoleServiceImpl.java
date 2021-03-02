package com.longying.bms.usermis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.longying.bms.usermis.service.RoleService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsdata.modules.ums.model.UmsAdminRoleRel;
import com.longying.bmsdata.modules.ums.model.UmsRole;
import com.longying.bmsdata.modules.ums.service.UmsAdminRoleRelService;
import com.longying.bmsdata.modules.ums.service.UmsRoleService;
import com.longying.bmsserver.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by chenglong on 2021/3/1
 */
@Service
@Slf4j
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private UmsRoleService umsRoleService;
    @Autowired
    private UmsAdminRoleRelService umsAdminRoleRelService;

    @Override
    public UmsRole createModel(UmsRole model) {
        umsRoleService.save(model);
        return model;
    }

    @Override
    public List<String> relationRoles(String userId, List<String> roleIdList) {
        List<String> relationIdList = new ArrayList<>();
        removeRoleRel(userId);
        List<UmsAdminRoleRel> relList = new ArrayList<>();
        for(String roleId : roleIdList){
            UmsAdminRoleRel rel = UmsAdminRoleRel.builder().userId(userId).roleId(roleId).build();
            rel = BaseModel.createModel(rel);
            relList.add(rel);
            relationIdList.add(rel.getUuid());
        }
        umsAdminRoleRelService.saveBatch(relList);
        return relationIdList;
    }

    @Override
    public List<UmsRole> getUserRelRoles(String userId) {
        List<UmsRole> roles = new ArrayList<>();
        QueryWrapper<UmsAdminRoleRel> wrapper = new QueryWrapper();
        wrapper.lambda().eq(UmsAdminRoleRel::getUserId, userId);
        List<UmsAdminRoleRel> list = umsAdminRoleRelService.list(wrapper);
        for(UmsAdminRoleRel rel:list){
            roles.add(umsRoleService.getOne(new QueryWrapper<UmsRole>()
                    .lambda()
                    .eq(UmsRole::getIsDelete, BaseConstants.DEL_NO).eq(UmsRole::getUuid,rel.getRoleId())));
        }
        return roles;
    }

    @Override
    public UmsRole modifyRole(UmsRole model) {
        LambdaUpdateWrapper<UmsRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UmsRole::getIsDelete, BaseConstants.DEL_NO)
                .eq(UmsRole::getUuid, model.getUuid());
        umsRoleService.update(model,wrapper);
        model = umsRoleService.getOne(wrapper);
        return model;
    }

    /**
     * 清空用户所有角色
     * @param userId
     * @return
     */
    private boolean removeRoleRel(String userId){
        QueryWrapper<UmsAdminRoleRel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdminRoleRel::getUserId, userId);
        return umsAdminRoleRelService.remove(wrapper);
    }
}
