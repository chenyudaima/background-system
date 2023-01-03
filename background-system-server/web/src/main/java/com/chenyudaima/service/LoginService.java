package com.chenyudaima.service;

import com.chenyudaima.model.Result;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
public interface LoginService {
    Result<?> login(String account,String password);

    Result<?> updateToken(String token);

}
