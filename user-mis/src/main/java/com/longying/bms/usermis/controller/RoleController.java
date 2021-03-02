package com.longying.bms.usermis.controller;

import cn.hutool.core.collection.CollUtil;
import com.longying.bms.usermis.service.RoleService;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import com.longying.bmsdata.modules.ums.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by chenglong on 2021/3/1
 */
@Api(tags = "[0104]角色管理")
@Slf4j
@RestController
@RequestMapping("/bms/user/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "[010401]新增角色")
    @RequestMapping(value = "createRole",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createRole(@RequestParam(value = "name") @NonNull @ApiParam("角色名称") String name,
                                    @RequestParam(value = "descript", required = false) @ApiParam("角色描述") String descript,
                                    @RequestParam(value = "status") @NonNull @ApiParam("角色状态") String status,
                                    @RequestParam(value = "sort") @NonNull @ApiParam("排序") Integer sort
    ){
        try {
            UmsRole model = UmsRole.builder()
                    .name(name)
                    .descript(descript)
                    .status(status)
                    .sort(sort)
                    .build();
            return CommonResult.success(roleService.createModel(BaseModel.createModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[010402]关联用户角色")
    @RequestMapping(value = "relationRoles",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult relationRoles(@RequestParam(value = "userId") @NonNull @ApiParam("用户id") String userId,
                                   @RequestParam(value = "roleIds") @NonNull @ApiParam("角色id列表") String roleIds

    ){
        try {
            return CommonResult.success(roleService.relationRoles(userId, CollUtil.newArrayList(roleIds.split(","))));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[010403]查看用户所属角色")
    @RequestMapping(value = "getUserRelRoles",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getUserRelRoles(@RequestParam(value = "userId") @NonNull @ApiParam("用户id") String userId
    ){
        try {
            return CommonResult.success(roleService.getUserRelRoles(userId));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[010404]修改角色")
    @RequestMapping(value = "modifyRole",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult modifyRole(@RequestParam(value = "uuid") @NonNull @ApiParam("角色id") String uuid,
                                   @RequestParam(value = "name", required = false) @ApiParam("角色名称") String name,
                                   @RequestParam(value = "descript", required = false) @ApiParam("角色描述") String descript,
                                   @RequestParam(value = "status", required = false) @ApiParam("角色状态") String status,
                                   @RequestParam(value = "sort", required = false) @ApiParam("排序") Integer sort
    ){
        try {
            UmsRole model = UmsRole.builder()
                    .uuid(uuid)
                    .name(name)
                    .descript(descript)
                    .status(status)
                    .sort(sort)
                    .build();
            return CommonResult.success(roleService.modifyRole(BaseModel.updateModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }
}
