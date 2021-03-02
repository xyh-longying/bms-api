package com.longying.bms.usermis.service;

/**
 * Create by chenglong on 2021/2/23
 */
public interface LoginService {

    String doLogin(String username, String password, String terminal, String ip);
}
