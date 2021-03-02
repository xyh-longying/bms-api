package com.longying.bms.usermis.controller;

import com.longying.bms.usermis.service.LoginService;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by chenglong on 2021/2/23
 */
@Api(tags = "[0101]用户登录")
@Slf4j
@RestController
@RequestMapping("/bms/user/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "C01020301",description = "用户名不存在"),
            @ApiResponse(responseCode = "C01020302",description = "登录密码错误"),
            @ApiResponse(responseCode = "500",description = "接口内部异常")
    })
    @ApiOperation(value = "[010101]用户登录")
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult doLogin(@RequestParam(value = "username") @NonNull @ApiParam("用户名") String username,
                                 @RequestParam(value = "password") @NonNull @ApiParam("密码") String password,
                                 @RequestParam(value = "terminal") @NonNull @ApiParam("终端") String terminal,
                                 @RequestParam(value = "ip") @NonNull @ApiParam("IP") String ip
    ){
        try {
            return CommonResult.success(loginService.doLogin(username,password,terminal,ip));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }
}
