package com.longying.bms.systemmis.controller;

import com.longying.bms.systemmis.service.SystemSettingService;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import com.longying.bmsdata.modules.yms.model.YmsSystemSetting;
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
@Api(tags = "[0202]系统参数设置")
@Slf4j
@RestController
@RequestMapping("/bms/sys/systemsetting")
public class SystemSettingController {

    @Autowired
    private SystemSettingService systemSettingService;

    @ApiOperation(value = "[020201]新增系统参数")
    @RequestMapping(value = "saveSystemSetting",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveSystemSetting(@RequestParam(value = "name") @NonNull @ApiParam("名称") String name,
                                   @RequestParam(value = "code") @NonNull @ApiParam("编码") String code,
                                   @RequestParam(value = "value") @NonNull @ApiParam("值") String value,
                                   @RequestParam(value = "remark", required = false) @ApiParam("备注说明") String remark){
        try {
            YmsSystemSetting model = YmsSystemSetting.builder()
                    .name(name)
                    .code(code)
                    .value(value)
                    .remark(remark)
                    .build();
            return CommonResult.success(systemSettingService.saveSystemSetting(BaseModel.createModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[020203]修改系统参数")
    @RequestMapping(value = "modifySystemSetting",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult modifySystemSetting(@RequestParam(value = "uuid") @NonNull @ApiParam("uuid") String uuid,
                                          @RequestParam(value = "name", required = false) @ApiParam("名称") String name,
                                          @RequestParam(value = "value", required = false) @ApiParam("值") String value,
                                          @RequestParam(value = "remark", required = false) @ApiParam("备注说明") String remark){
        try {
            YmsSystemSetting model = YmsSystemSetting.builder()
                    .uuid(uuid)
                    .name(name)
                    .value(value)
                    .remark(remark)
                    .build();
            return CommonResult.success(systemSettingService.modifySystemSetting(BaseModel.updateModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[020205]缓存系统参数")
    @RequestMapping(value = "cacheSystemSetting",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cacheSystemSetting(){
        try {
            systemSettingService.cacheSystemSetting();
            return CommonResult.success("系统参数缓存完成");
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

}
