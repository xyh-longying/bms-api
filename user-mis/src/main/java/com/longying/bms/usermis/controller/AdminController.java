package com.longying.bms.usermis.controller;

import com.longying.bms.usermis.service.AdminService;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import com.longying.bmsdata.modules.ums.model.UmsUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by chenglong on 2021/2/28
 */
@Api(tags = "[0102]后台用户管理")
@Slf4j
@RestController
@RequestMapping("/bms/user/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "[010201]新增管理员")
    @RequestMapping(value = "createAdmin",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdmin(@RequestParam(value = "username") @NonNull @ApiParam("用户名") String username,
                                @RequestParam(value = "password") @NonNull @ApiParam("密码") String password,
                                @RequestParam(value = "userType") @NonNull @ApiParam("用户类型") String userType

    ){
        try {
            UmsUser model = UmsUser.builder()
                    .username(username)
                    .password(password)
                    .userType(userType)
                    .build();
            return CommonResult.success(adminService.createModel(BaseModel.createModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }
}
