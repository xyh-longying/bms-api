package com.longying.bms.systemmis.service;

import com.longying.bmsdata.modules.yms.model.YmsApiExc;

/**
 * Create by chenglong on 2021/2/24
 */
public interface ApiExcService {
    YmsApiExc saveApiExc(YmsApiExc model);

    void cacheApiExc();
}
