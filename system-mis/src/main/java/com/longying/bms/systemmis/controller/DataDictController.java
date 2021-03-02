package com.longying.bms.systemmis.controller;

import com.longying.bms.systemmis.service.DataDictService;
import com.longying.bmsbase.base.BaseModel;
import com.longying.bmsbase.common.api.ApiException;
import com.longying.bmsbase.common.api.CommonResult;
import com.longying.bmsdata.modules.yms.model.YmsDataDict;
import com.longying.bmsserver.data.DataDict;
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
@Api(tags = "[0201]数据字典管理")
@Slf4j
@RestController
@RequestMapping("/bms/sys/datadict")
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @ApiOperation(value = "[020101]新增数据字典")
    @RequestMapping(value = "saveDataDict",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveApiExc(@RequestParam(value = "name") @NonNull @ApiParam("字典名称") String name,
                                   @RequestParam(value = "code") @NonNull @ApiParam("字典编码") String code,
                                   @RequestParam(value = "value") @NonNull @ApiParam("字典值") String value,
                                   @RequestParam(value = "status") @NonNull @ApiParam("状态") String status,
                                   @RequestParam(value = "remark", required = false) @ApiParam("备注说明") String remark){
        try {
            YmsDataDict model = YmsDataDict.builder()
                    .name(name)
                    .code(code)
                    .value(value)
                    .status(status)
                    .remark(remark)
                    .build();
            return CommonResult.success(dataDictService.saveDataDict(BaseModel.createModel(model)));
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[020105]缓存数据字典")
    @RequestMapping(value = "cacheDataDict",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cacheDataDict(){
        try {
            dataDictService.cacheDataDict();
            return CommonResult.success("数据字典缓存完成");
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }

    @ApiOperation(value = "[020106]根据编码获取数据字典")
    @RequestMapping(value = "getDataDictByCode",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getDataDictByCode(@RequestParam(value = "code") @NonNull @ApiParam("字典编码") String code){
        try {
            DataDict dataDict = dataDictService.getDataDictByCode(code);
            return CommonResult.success(dataDict);
        } catch (ApiException e){
            return CommonResult.failed(e);
        }
    }
}
