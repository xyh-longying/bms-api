package com.longying.bms.systemmis.service;

import com.longying.bmsdata.modules.yms.model.YmsSystemSetting;

/**
 * Create by chenglong on 2021/2/24
 */
public interface SystemSettingService {
    YmsSystemSetting saveSystemSetting(YmsSystemSetting model);

    YmsSystemSetting modifySystemSetting(YmsSystemSetting model);

    void cacheSystemSetting();
}
