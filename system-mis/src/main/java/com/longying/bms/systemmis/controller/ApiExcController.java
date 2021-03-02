package com.longying.bms.systemmis.controller;

import com.longying.bms.systemmis.service.ApiExcService;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import com.longying.bmsdata.modules.yms.model.YmsApiExc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by chenglong on 2021/2/24
 */
@Api(tags = "[0203]接口异常管理")
@Slf4j
@RestController
@RequestMapping("/bms/sys/apiexc")
public class ApiExcController {

    @Autowired
    private ApiExcService apiExcService;

    @ApiOperation(value = "[020301]新增接口异常信息")
    @RequestMapping(value = "saveApiExc",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveApiExc(@RequestParam(value = "code") @NonNull @ApiParam("异常编码") String code,
                                @RequestParam(value = "descript") @NonNull @ApiParam("异常描述") String descript,
                                @RequestParam(value = "reason", required = false) @ApiParam("异常原因") String reason,
                                @RequestParam(value = "solution", required = false) @ApiParam("处理方法") String solution,
                                @RequestParam(value = "print", required = false) @ApiParam("前端显示") String print,
                                @RequestParam(value = "remark", required = false) @ApiParam("备注") String remark){
        try {
            YmsApiExc model = YmsApiExc.builder()
                    .code(code)
                    .descript(descript)
                    .reason(reason)
                    .solution(solution)
                    .print(print)
                    .remark(remark)
                    .build();
            return CommonResult.success(apiExcService.saveApiExc(BaseModel.createModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[020302]缓存接口异常信息")
    @RequestMapping(value = "cacheApiExc",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cacheApiExc(){
        try {
            apiExcService.cacheApiExc();
            return CommonResult.success("接口异常信息缓存完成");
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }
}
