package com.longying.bms.systemmis.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longying.bms.systemmis.service.ApiExcService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsdata.modules.yms.model.YmsApiExc;
import com.longying.bmsdata.modules.yms.service.YmsApiExcService;
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
public class ApiExcServiceImpl extends BaseService implements ApiExcService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private YmsApiExcService ymsApiExcService;

    @Override
    public YmsApiExc saveApiExc(YmsApiExc model) {
        QueryWrapper<YmsApiExc> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(YmsApiExc::getIsDelete, BaseConstants.DEL_NO)
                .eq(YmsApiExc::getCode, model.getCode());
        if(ymsApiExcService.count(wrapper)>0){
            throwException("B020301001",model.getCode());
        }
        ymsApiExcService.save(model);
        return model;
    }

    @Override
    public void cacheApiExc() {
        QueryWrapper<YmsApiExc> wrapper = new QueryWrapper();
        wrapper.lambda().eq(YmsApiExc::getIsDelete, BaseConstants.DEL_NO).orderByAsc(YmsApiExc::getCode);
        List<YmsApiExc> list = ymsApiExcService.list(wrapper);
        JSONObject cacheData = new JSONObject();
        list.forEach(item -> cacheData.put(item.getCode(),
                new JSONObject()
                .putOpt("descript", item.getDescript())
                .putOpt("reason", item.getReason())
                .putOpt("solution", item.getSolution())
                .putOpt("print", item.getPrint())
        ));
        redisService.set(BaseConstants.API_EXC_CACHE_KEY, JSONUtil.toJsonStr(cacheData));
    }
}
