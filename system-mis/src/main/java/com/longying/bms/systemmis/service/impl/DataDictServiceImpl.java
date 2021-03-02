package com.longying.bms.systemmis.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longying.bms.systemmis.service.DataDictService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsdata.modules.yms.model.YmsDataDict;
import com.longying.bmsdata.modules.yms.service.YmsDataDictService;
import com.longying.bmsserver.data.DataDict;
import com.longying.bmsserver.service.BaseService;
import com.longying.bmsserver.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by chenglong on 2021/2/24
 */
@Service
@Slf4j
public class DataDictServiceImpl extends BaseService implements DataDictService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private YmsDataDictService ymsDataDictService;

    @Override
    public YmsDataDict saveDataDict(YmsDataDict model) {
        QueryWrapper<YmsDataDict> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(YmsDataDict::getIsDelete, BaseConstants.DEL_NO)
                .eq(YmsDataDict::getCode, model.getCode());
        if(ymsDataDictService.count(wrapper)>0){
            throwException("B020101001",model.getCode());
        }
        ymsDataDictService.save(model);
        return model;
    }

    @Override
    public void cacheDataDict() {
        QueryWrapper<YmsDataDict> wrapper = new QueryWrapper();
        wrapper.lambda().eq(YmsDataDict::getIsDelete, BaseConstants.DEL_NO);
        List<YmsDataDict> list = ymsDataDictService.list(wrapper);
        JSONObject cacheData = new JSONObject();
        list.forEach(item -> cacheData.put(item.getCode(),
                new JSONObject()
                .putOpt("name", item.getName())
                .putOpt("status", item.getStatus())
                .putOpt("items", JSONUtil.parseArray(item.getValue()))
        ));
        redisService.set(BaseConstants.DATA_DICT_CACHE_KEY, JSONUtil.toJsonStr(cacheData));
    }

    @Override
    public DataDict getDataDictByCode(String code) {
        DataDict dataDict = listDataDictByCode(code);
        if(dataDict==null){
            throwException("B020101002", code);
        }
        return dataDict;
    }
}
