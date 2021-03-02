package com.longying.bms.systemmis.service;

import com.longying.bmsdata.modules.yms.model.YmsDataDict;
import com.longying.bmsserver.data.DataDict;

/**
 * Create by chenglong on 2021/2/24
 */
public interface DataDictService {
    YmsDataDict saveDataDict(YmsDataDict model);

    void cacheDataDict();

    DataDict getDataDictByCode(String code);
}
