package com.longying.bms.systemmis.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.longying.bms.systemmis.service.SystemSettingService;
import com.longying.bmsbase.base.BaseConstants;
import com.longying.bmsdata.modules.yms.model.YmsSystemSetting;
import com.longying.bmsdata.modules.yms.service.YmsSystemSettingService;
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
public class SystemSettingServiceImpl extends BaseService implements SystemSettingService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private YmsSystemSettingService ymsSystemSettingService;

    @Override
    public YmsSystemSetting saveSystemSetting(YmsSystemSetting model) {
        QueryWrapper<YmsSystemSetting> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(YmsSystemSetting::getIsDelete, BaseConstants.DEL_NO)
                .eq(YmsSystemSetting::getCode, model.getCode());
        if(ymsSystemSettingService.count(wrapper)>0){
            throwException("B020201001",model.getCode());
        }
        ymsSystemSettingService.save(model);
        return model;
    }

    @Override
    public YmsSystemSetting modifySystemSetting(YmsSystemSetting model) {
        LambdaUpdateWrapper<YmsSystemSetting> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(YmsSystemSetting::getIsDelete, BaseConstants.DEL_NO)
                .eq(YmsSystemSetting::getUuid, model.getUuid());
        ymsSystemSettingService.update(model,wrapper);
        model = ymsSystemSettingService.getOne(wrapper);
        return model;
    }

    @Override
    public void cacheSystemSetting() {
        QueryWrapper<YmsSystemSetting> wrapper = new QueryWrapper();
        wrapper.lambda().eq(YmsSystemSetting::getIsDelete, BaseConstants.DEL_NO);
        List<YmsSystemSetting> list = ymsSystemSettingService.list(wrapper);
        JSONObject cacheData = new JSONObject();
        list.forEach(item -> cacheData.put(item.getCode(),
                new JSONObject()
                        .putOpt("name", item.getName())
                        .putOpt("value", item.getValue())
        ));
        redisService.set(BaseConstants.SYSTEM_SETTING_CACHE_KEY, JSONUtil.toJsonStr(cacheData));
    }
}
